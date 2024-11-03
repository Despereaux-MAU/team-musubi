package com.musubi.teammusubi.domain.member.controller;

import com.musubi.teammusubi.common.config.PasswordEncoder;
import com.musubi.teammusubi.domain.member.dto.MemberRequestDto;
import com.musubi.teammusubi.domain.member.dto.MemberResponseDto;
import com.musubi.teammusubi.domain.member.entity.Member;
import com.musubi.teammusubi.domain.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponseDto registerMember(@RequestBody MemberRequestDto requestDto) {
        Member member = memberService.registerMember(requestDto);
        return memberService.convertToResponseDto(member);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberRequestDto requestDto) {
        Member member = memberService.findByEmail(requestDto.getEmail());
        if (member != null && passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            return ResponseEntity.ok(member.getNickname() + " 님이 로그인 되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 일치하지 않습니다.");
    }
}
