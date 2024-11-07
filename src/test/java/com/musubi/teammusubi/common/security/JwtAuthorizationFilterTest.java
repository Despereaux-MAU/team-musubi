package com.musubi.teammusubi.common.security;

import com.musubi.teammusubi.common.util.JwtUtil;
import com.musubi.teammusubi.domain.member.service.MemberDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class JwtAuthorizationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private MemberDetailsServiceImpl memberDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_shouldNotAuthenticateOnSignupAndLoginPaths() throws ServletException, IOException {

        when(request.getRequestURI()).thenReturn("/api/members/signup");

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "SecurityContext에 인증 정보가 없어야 합니다.");
    }

    @Test
    void doFilterInternal_shouldAuthenticateWithValidToken() throws ServletException, IOException {

        String token = "validToken";
        Long memberId = 1L;
        String username = "testUser";

        when(request.getRequestURI()).thenReturn("/api/secure-endpoint");
        when(jwtUtil.resolveToken(request)).thenReturn(token);
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getMemberIdFromToken(token)).thenReturn(memberId);

        UserDetails userDetails = new User(username, "password", Collections.emptyList());
        when(memberDetailsService.loadUserById(memberId)).thenReturn(userDetails);

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication(), "SecurityContext에 인증 정보가 설정되어야 합니다.");
        assertEquals(username, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    void doFilterInternal_shouldNotAuthenticateWithInvalidToken() throws ServletException, IOException {

        String token = "invalidToken";

        when(request.getRequestURI()).thenReturn("/api/secure-endpoint");
        when(jwtUtil.resolveToken(request)).thenReturn(token);
        when(jwtUtil.validateToken(token)).thenReturn(false);

        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "유효하지 않은 토큰으로 인증이 설정되지 않아야 합니다.");
    }
}

