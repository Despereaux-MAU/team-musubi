package com.musubi.teammusubi.domain.member.controller;

import com.musubi.teammusubi.common.config.PasswordEncoder;
import com.musubi.teammusubi.domain.member.dto.MemberRequestDto;
import com.musubi.teammusubi.domain.member.dto.MemberResponseDto;
import com.musubi.teammusubi.domain.member.entity.Member;
import com.musubi.teammusubi.domain.member.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public MemberResponseDto register(@RequestBody MemberRequestDto requestDto) {
        Member member = memberService.registerMember(requestDto);
        return memberService.convertToResponseDto(member);
    }

    @PostMapping("/login")
    public MemberResponseDto login(@RequestBody MemberRequestDto requestDto) {
        Member member = memberService.findByEmail(requestDto.getEmail());
        if (member != null && passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            return memberService.convertToResponseDto(member);
        }
        return null;
    }
}
