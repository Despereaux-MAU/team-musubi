package com.musubi.teammusubi.domain.member.service;

import com.musubi.teammusubi.common.config.PasswordEncoder;
import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.util.JwtUtil;
import com.musubi.teammusubi.domain.member.dto.MemberRequest;
import com.musubi.teammusubi.domain.member.dto.MemberResponse;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public MemberResponse registerMember(MemberRequest request) {

        Member member = new Member();
        member.checkEmailDuplicate(request.getEmail(), memberRepository);
        member.setEmail(request.getEmail());
        member.setUsername(request.getUsername());
        member.setNickname(request.getNickname());
        member.checkPasswordMatch(request.getPassword(), request.getPasswordCheck());
        member.setPassword(request.getPassword(), passwordEncoder);
        member.setRole(request.getRole());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());

        memberRepository.save(member);

        return convertToResponseDto(member);
    }

    public String login(MemberRequest request, HttpServletResponse response) {
        String token = authenticate(request);
        if (token != null) {
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            Member member = findByEmail(request.getEmail());
            return member.getNickname() + "님 환영합니다.";
        }
        return null;
    }

    public String authenticate(MemberRequest request) {
        Member member = findByEmail(request.getEmail());
        if (member != null && passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            return jwtUtil.generateToken(member.getUsername(), member.getRole().name());
        }
        return null;
    }

    public MemberResponse getProfile(String email) {
        Member member = findByEmail(email);
        if (member != null) {
            return convertToResponseDto(member);
        }
        return null;
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    public MemberResponse convertToResponseDto(Member member) {
        MemberResponse response = new MemberResponse();
        response.setId(member.getId());
        response.setEmail(member.getEmail());
        response.setUsername(member.getUsername());
        response.setNickname(member.getNickname());
        response.setRole(member.getRole());
        response.setPhone(member.getPhone());
        response.setAddress(member.getAddress());
        return response;
    }

    @Transactional
    public MemberResponse updateMember(String email, MemberRequest request) {
        Member member = Member.findByEmailOrThrow(email, memberRepository);

        member.setUsername(request.getUsername());
        member.setNickname(request.getNickname());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());

        return convertToResponseDto(member);
    }

    @Transactional
    public void changePassword(String email, String newPassword, String passwordCheck) {
        Member member = Member.findByEmailOrThrow(email, memberRepository);

        member.checkPasswordMatch(newPassword, passwordCheck);
        member.setPassword(newPassword, passwordEncoder);
        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(String email) {
        Member member = Member.findByEmailOrThrow(email, memberRepository);

        member.deactiveAccount();
        member.setUsername("탈퇴회원");
        member.setNickname("");
        member.setPassword("");
        member.setPhone("");
        member.setAddress("");
        memberRepository.save(member);
    }
}
