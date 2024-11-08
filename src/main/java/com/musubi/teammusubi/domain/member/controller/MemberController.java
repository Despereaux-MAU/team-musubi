package com.musubi.teammusubi.domain.member.controller;

import com.musubi.teammusubi.common.exception.ExceptionType;
import com.musubi.teammusubi.common.exception.ResponseException;
import com.musubi.teammusubi.domain.member.dto.LoginRequest;
import com.musubi.teammusubi.domain.member.dto.LoginResponse;
import com.musubi.teammusubi.domain.member.dto.MemberRequest;
import com.musubi.teammusubi.domain.member.dto.MemberResponse;
import com.musubi.teammusubi.domain.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse registerMember(@RequestBody MemberRequest request) throws ResponseException {
        return memberService.registerMember(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        LoginResponse loginResponse = memberService.login(request, response);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<MemberResponse> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        MemberResponse response = memberService.getProfile(userDetails.getUsername());
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        throw new ResponseException(ExceptionType.USER_NOT_FOUND);
    }

    @PutMapping("/profile")
    public ResponseEntity<MemberResponse> updateMember(@RequestBody MemberRequest request, Authentication authentication) {
        String email = authentication.getName();
        MemberResponse response = memberService.updateMember(email, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody Map<String, String> passwordRequest, Authentication authentication) {
        String email = authentication.getName();
        String requestEmail = passwordRequest.get("email");
        String newPassword = passwordRequest.get("newPassword");
        String passwordCheck = passwordRequest.get("passwordCheck");

        if (!email.equals(requestEmail)) {
            throw new ResponseException(ExceptionType.EMAIL_MISMATCH);
        }

        memberService.changePassword(email, newPassword, passwordCheck);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> deActivateMember(Authentication authentication, HttpServletResponse response) {
        String email = authentication.getName();
        memberService.deActivateMember(email);

        Cookie deleteCookie = new Cookie("jwt", null);
        deleteCookie.setPath("/");
        deleteCookie.setMaxAge(0);
        response.addCookie(deleteCookie);

        return ResponseEntity.noContent().build();
    }
}
