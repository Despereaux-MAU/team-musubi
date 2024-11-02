package com.musubi.teammusubi.domain.member.service;

import com.musubi.teammusubi.common.config.PasswordEncoder;
import com.musubi.teammusubi.domain.member.dto.MemberRequestDto;
import com.musubi.teammusubi.domain.member.dto.MemberResponseDto;
import com.musubi.teammusubi.domain.member.entity.Member;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member registerMember(MemberRequestDto memberRequestDto) {
        Member member = new Member();
        member.setEmail(memberRequestDto.getEmail());
        member.setUsername(memberRequestDto.getUsername());
        member.setNickname(memberRequestDto.getNickname());
        member.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        member.setRole(memberRequestDto.getRole());
        member.setAddress(memberRequestDto.getAddress());
        member.setPhone(memberRequestDto.getPhone());
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
        responseDto.setAddress(member.getAddress());
        responseDto.setPhone(member.getPhone());
        responseDto.setCreatedAt(member.getCreatedAt());
        responseDto.setUpdatedAt(member.getUpdatedAt());
        return responseDto;
    }
}
