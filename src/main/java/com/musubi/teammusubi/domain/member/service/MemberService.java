package com.musubi.teammusubi.domain.member.service;

import com.musubi.teammusubi.common.config.PasswordEncoder;
import com.musubi.teammusubi.common.exception.GlobalException;
import com.musubi.teammusubi.domain.member.dto.MemberRequestDto;
import com.musubi.teammusubi.domain.member.dto.MemberResponseDto;
import com.musubi.teammusubi.domain.member.entity.Member;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member registerMember(MemberRequestDto requestDto) {
        if (memberRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new GlobalException("U0001", HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다.");
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
        return memberRepository.save(member);
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
}
