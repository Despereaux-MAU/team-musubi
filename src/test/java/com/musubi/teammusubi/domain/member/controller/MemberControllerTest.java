package com.musubi.teammusubi.domain.member.controller;

import com.musubi.teammusubi.common.exception.ExceptionType;
import com.musubi.teammusubi.common.exception.ResponseException;
import com.musubi.teammusubi.domain.member.dto.LoginRequest;
import com.musubi.teammusubi.domain.member.dto.LoginResponse;
import com.musubi.teammusubi.domain.member.dto.MemberRequest;
import com.musubi.teammusubi.domain.member.dto.MemberResponse;
import com.musubi.teammusubi.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입 성공")
    void registerMember() throws ResponseException {
        MemberRequest request = new MemberRequest();
        MemberResponse expectedResponse = new MemberResponse();

        when(memberService.registerMember(request)).thenReturn(expectedResponse);

        MemberResponse actualResponse = memberController.registerMember(request);

        assertEquals(expectedResponse, actualResponse);
        verify(memberService, times(1)).registerMember(request);
    }

    @Test
    @DisplayName("회원가입 실패 - 같은 이메일 존재")
    void registerMember_EmailExists() throws ResponseException {
        MemberRequest request = new MemberRequest();
        when(memberService.registerMember(request)).thenThrow(new ResponseException(ExceptionType.EMAIL_IN_USE));

        ResponseException exception = assertThrows(ResponseException.class, () ->
                memberController.registerMember(request));

        assertEquals(ExceptionType.EMAIL_IN_USE.getErrorCode(), exception.getErrorCode());
        assertEquals(ExceptionType.EMAIL_IN_USE.getMessage(), exception.getMessage());
        assertEquals(ExceptionType.EMAIL_IN_USE.getStatus(), exception.getStatus());
        verify(memberService, times(1)).registerMember(request);
    }

    @Test
    @DisplayName("로그인 성공")
    void login() {
        LoginRequest request = new LoginRequest();
        LoginResponse expectedResponse = new LoginResponse();

        when(memberService.login(request, response)).thenReturn(expectedResponse);

        ResponseEntity<LoginResponse> actualResponse = memberController.login(request, response);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
        verify(memberService, times(1)).login(request, response);
    }

    @Test
    @DisplayName("회원정보 조회 성공")
    void getProfile() {
        String username = "testUser";
        MemberResponse expectedResponse = new MemberResponse();

        when(userDetails.getUsername()).thenReturn(username);
        when(memberService.getProfile(username)).thenReturn(expectedResponse);

        ResponseEntity<MemberResponse> actualResponse = memberController.getProfile(userDetails);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
        verify(memberService, times(1)).getProfile(username);
    }

    @Test
    @DisplayName("비밀번호 변경 성공")
    void changePassword() {
        String email = "test@example.com";
        Map<String, String> passwordRequest = Map.of(
                "email", email,
                "newPassword", "new_password",
                "passwordCheck", "new_password"
        );

        when(authentication.getName()).thenReturn(email);

        ResponseEntity<Void> actualResponse = memberController.changePassword(passwordRequest, authentication);

        assertEquals(HttpStatus.NO_CONTENT, actualResponse.getStatusCode());
        verify(memberService, times(1)).changePassword(email, "new_password", "new_password");
    }

    @Test
    @DisplayName("비밀번호 변경 실패 - 이메일 불일치")
    void changePassword_EmailMismatch() {
        String email = "test@example.com";
        Map<String, String> passwordRequest = Map.of(
                "email", "wrong@example.com",
                "newPassword", "new_password",
                "passwordCheck", "new_password"
        );

        when(authentication.getName()).thenReturn(email);

        ResponseException exception = assertThrows(ResponseException.class, () ->
                memberController.changePassword(passwordRequest, authentication));

        assertEquals(ExceptionType.EMAIL_MISMATCH.getErrorCode(), exception.getErrorCode());
        assertEquals(ExceptionType.EMAIL_MISMATCH.getMessage(), exception.getMessage());
        assertEquals(ExceptionType.EMAIL_MISMATCH.getStatus(), exception.getStatus());
        verify(memberService, never()).changePassword(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("비밀번호 변경 실패 - 비밀번호 형식 불일치")
    void changePassword_InvalidPasswordFormat() {
        String email = "test@example.com";
        Map<String, String> passwordRequest = Map.of(
                "email", email,
                "newPassword", "short",
                "passwordCheck", "short"
        );

        when(authentication.getName()).thenReturn(email);
        doThrow(new ResponseException(ExceptionType.PASSWORD_DENIED)).when(memberService).changePassword(email, "short", "short");

        ResponseException exception = assertThrows(ResponseException.class, () ->
                memberController.changePassword(passwordRequest, authentication));

        assertEquals(ExceptionType.PASSWORD_DENIED.getErrorCode(), exception.getErrorCode());
        assertEquals(ExceptionType.PASSWORD_DENIED.getMessage(), exception.getMessage());
        assertEquals(ExceptionType.PASSWORD_DENIED.getStatus(), exception.getStatus());
        verify(memberService, times(1)).changePassword(email, "short", "short");
    }

    @Test
    @DisplayName("비밀번호 변경 실패 - 비밀번호 확인 불일치")
    void changePassword_PasswordCheckMismatch() {
        String email = "test@example.com";
        Map<String, String> passwordRequest = Map.of(
                "email", email,
                "newPassword", "new_password",
                "passwordCheck", "different_password"
        );

        when(authentication.getName()).thenReturn(email);
        doThrow(new ResponseException(ExceptionType.PASSWORD_MISMATCH)).when(memberService).changePassword(email, "new_password", "different_password");

        ResponseException exception = assertThrows(ResponseException.class, () ->
                memberController.changePassword(passwordRequest, authentication));

        assertEquals(ExceptionType.PASSWORD_MISMATCH.getErrorCode(), exception.getErrorCode());
        assertEquals(ExceptionType.PASSWORD_MISMATCH.getMessage(), exception.getMessage());
        assertEquals(ExceptionType.PASSWORD_MISMATCH.getStatus(), exception.getStatus());
        verify(memberService, times(1)).changePassword(email, "new_password", "different_password");
    }

    @Test
    @DisplayName("회원정보 수정 성공")
    void updateMember() {
        String email = "test@example.com";
        MemberRequest request = new MemberRequest();
        MemberResponse expectedResponse = new MemberResponse();

        when(authentication.getName()).thenReturn(email);
        when(memberService.updateMember(email, request)).thenReturn(expectedResponse);

        ResponseEntity<MemberResponse> actualResponse = memberController.updateMember(request, authentication);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
        verify(memberService, times(1)).updateMember(email, request);
    }

    @Test
    @DisplayName("회원탈퇴 성공")
    void deActivateMember() {
        String email = "test@example.com";

        when(authentication.getName()).thenReturn(email);

        ResponseEntity<Void> actualResponse = memberController.deActivateMember(authentication, response);

        assertEquals(HttpStatus.NO_CONTENT, actualResponse.getStatusCode());
        verify(memberService, times(1)).deActivateMember(email);
        verify(response, times(1)).addCookie(any());
    }
}
