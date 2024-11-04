package com.musubi.teammusubi.domain.member.service;

import com.musubi.teammusubi.common.config.PasswordEncoder;
import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.exception.GlobalException;
import com.musubi.teammusubi.common.util.JwtUtil;
import com.musubi.teammusubi.domain.member.dto.MemberRequestDto;
import com.musubi.teammusubi.domain.member.dto.MemberResponseDto;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
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

    public MemberResponseDto registerMember(MemberRequestDto requestDto) {
        if (memberRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new GlobalException("U0001", HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");
        }
        if (!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        Member member = new Member();
        member.setEmail(requestDto.getEmail());
        member.setUsername(requestDto.getUsername());
        member.setNickname(requestDto.getNickname());
        member.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        member.setRole(requestDto.getRole());
        member.setPhone(requestDto.getPhone());
        member.setAddress(requestDto.getAddress());

        memberRepository.save(member);

        return convertToResponseDto(member);
    }

    public String login(MemberRequestDto requestDto, HttpServletResponse response) {
        String token = authenticate(requestDto);
        if (token != null) {
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            Member member = findByEmail(requestDto.getEmail());
            return member.getNickname() + "님 환영합니다.";
        }
        return null;
    }

    public String authenticate(MemberRequestDto requestDto) {
        Member member = findByEmail(requestDto.getEmail());
        if (member != null && passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            return jwtUtil.generateToken(member.getUsername(), member.getRole().name());
        }
        return null;
    }

    public MemberResponseDto getProfile(String email) {
        Member member = findByEmail(email);
        if (member != null) {
            return convertToResponseDto(member);
        }
        return null;
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    public MemberResponseDto convertToResponseDto(Member member) {
        MemberResponseDto responseDto = new MemberResponseDto();
        responseDto.setId(member.getId());
        responseDto.setEmail(member.getEmail());
        responseDto.setUsername(member.getUsername());
        responseDto.setNickname(member.getNickname());
        responseDto.setRole(member.getRole());
        responseDto.setPhone(member.getPhone());
        responseDto.setAddress(member.getAddress());
        return responseDto;
    }

    @Transactional
    public MemberResponseDto updateMember(String email, MemberRequestDto requestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException("U0002", HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."));

        member.setUsername(requestDto.getUsername());
        member.setNickname(requestDto.getNickname());
        member.setEmail(requestDto.getEmail());
        member.setPhone(requestDto.getPhone());
        member.setAddress(requestDto.getAddress());

        return convertToResponseDto(member);
    }

    @Transactional
    public void changePassword(String email, String newPassword, String passwordCheck) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException("U0002", HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."));

        if (!newPassword.equals(passwordCheck)) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException("U0002", HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."));

        member.deactiveAccount();
        member.setUsername("탈퇴회원");
        member.setNickname("");
        member.setPassword("");
        member.setPhone("");
        member.setAddress("");
        memberRepository.save(member);
    }
}
