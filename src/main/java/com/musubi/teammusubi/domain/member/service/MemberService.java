package com.musubi.teammusubi.domain.member.service;

import com.musubi.teammusubi.common.config.PasswordEncoder;
import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.util.JwtUtil;
import com.musubi.teammusubi.domain.member.dto.LoginRequest;
import com.musubi.teammusubi.domain.member.dto.LoginResponse;
import com.musubi.teammusubi.domain.member.dto.MemberRequest;
import com.musubi.teammusubi.domain.member.dto.MemberResponse;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        String token = authenticate(request);
        if (token != null) {

            Member member = findByEmail(request.getEmail());

            Cookie jwtcookie = new Cookie("jwt", token);
            jwtcookie.setHttpOnly(true);
            jwtcookie.setPath("/");
            response.addCookie(jwtcookie);

            Cookie idCookie = new Cookie("id", member.getId().toString());
            idCookie.setHttpOnly(true);
            idCookie.setPath("/");
            response.addCookie(idCookie);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setEmail(member.getEmail());
            loginResponse.setUsername(member.getUsername());
            loginResponse.setNickname(member.getNickname());
            loginResponse.setToken(token);
            loginResponse.setId(member.getId().toString());
            return loginResponse;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.");
    }

    public String authenticate(LoginRequest request) {
        Member member = findByEmail(request.getEmail());
        if (member != null && passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            return jwtUtil.generateToken(member.getId(), member.getRole().name());
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
    public void deActivateMember(String email) {
        Member member = Member.findByEmailOrThrow(email, memberRepository);

        member.deActiveAccount();
        memberRepository.save(member);
    }
}
