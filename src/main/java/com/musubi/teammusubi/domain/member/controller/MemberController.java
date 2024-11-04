package com.musubi.teammusubi.domain.member.controller;

import com.musubi.teammusubi.common.config.PasswordEncoder;
import com.musubi.teammusubi.common.exception.GlobalException;
import com.musubi.teammusubi.common.util.JwtUtil;
import com.musubi.teammusubi.domain.member.dto.MemberRequestDto;
import com.musubi.teammusubi.domain.member.dto.MemberResponseDto;
import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponseDto registerMember(@RequestBody MemberRequestDto requestDto) {
        Member member = memberService.registerMember(requestDto);
        return memberService.convertToResponseDto(member);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberRequestDto requestDto, HttpServletResponse response) {
        try {
            Member member = memberService.findByEmail(requestDto.getEmail());
            if (member != null && passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
                String token = jwtUtil.generateToken(member.getUsername(), member.getRole().name());
                Cookie cookie = new Cookie("jwt", token);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                response.addCookie(cookie);
                return ResponseEntity.ok(member.getNickname() + "님 환영합니다.");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 일치하지 않습니다.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<MemberResponseDto> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberService.findByEmail(userDetails.getUsername());
        if (member != null) {
            MemberResponseDto responseDto = memberService.convertToResponseDto(member);
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PutMapping("/profile")
    public ResponseEntity<MemberResponseDto> updateMember(@RequestBody MemberRequestDto requestDto, Authentication authentication) {
        String email = authentication.getName();
        Member member = memberService.findByEmail(email);
        if (member == null) {
            throw new GlobalException("U0002", HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다.");
        }
        Member updatedMember = memberService.updateMember(member.getId(), requestDto);
        MemberResponseDto responseDto = memberService.convertToResponseDto(updatedMember);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody Map<String, String> passwordRequest, Authentication authentication) {
        String email = authentication.getName();
        String requestEmail = passwordRequest.get("email");
        String newPassword = passwordRequest.get("newPassword");
        String passwordCheck = passwordRequest.get("passwordCheck");

        if (!email.equals(requestEmail)) {
            throw new GlobalException("U0001", HttpStatus.BAD_REQUEST, "이메일이 일치하지 않습니다.");
        }

        memberService.changePassword(email, newPassword, passwordCheck);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMember(HttpServletRequest request, @RequestBody Map<String, String> passwordRequest) {
        String password = passwordRequest.get("password");
        memberService.deleteMember(request, password);
        return ResponseEntity.noContent().build();
    }
}
