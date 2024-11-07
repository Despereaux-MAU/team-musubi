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
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Import 추가
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private MemberDetailsServiceImpl memberDetailsServiceImpl;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_validToken_ownerRole() throws IOException, ServletException {

        String token = "validToken";
        Long memberId = 1L;
        String role = "OWNER";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getMemberIdFromToken(token)).thenReturn(memberId);
        when(jwtUtil.getRoleFromToken(token)).thenReturn(role);

        when(request.getRequestURI()).thenReturn("/api/seller");

        UserDetails userDetails = new User("testUser", "password", Collections.singletonList(new SimpleGrantedAuthority(role)));
        when(memberDetailsServiceImpl.loadUserById(memberId)).thenReturn(userDetails);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("testUser", SecurityContextHolder.getContext().getAuthentication().getName(), "인증된 사용자 이름이 일치하지 않습니다.");
        assertTrue(SecurityContextHolder.getContext().getAuthentication().isAuthenticated(), "인증 상태가 false입니다.");
    }



    @Test
    void doFilterInternal_validToken_nonOwnerRole_accessDenied() throws IOException, ServletException {

        String token = "validToken";
        Long memberId = 1L;
        String role = "USER";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getMemberIdFromToken(token)).thenReturn(memberId);
        when(jwtUtil.getRoleFromToken(token)).thenReturn(role);
        when(request.getRequestURI()).thenReturn("/api/seller");

        UserDetails userDetails = new User("testUser", "password", Collections.singletonList(new SimpleGrantedAuthority(role)));
        when(memberDetailsServiceImpl.loadUserById(memberId)).thenReturn(userDetails);

        SecurityContextHolder.clearContext();

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(response, times(1)).sendError(HttpServletResponse.SC_FORBIDDEN, "접근이 거부되었습니다.");
        verify(filterChain, never()).doFilter(request, response); // filterChain 호출되지 않음을 확인

        assertNull(SecurityContextHolder.getContext().getAuthentication(), "예외가 발생했습니다: SecurityContext에 인증 정보가 존재합니다.");

        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_invalidToken() throws IOException, ServletException {

        String token = "invalidToken";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.validateToken(token)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(response, never()).sendError(anyInt(), anyString());
        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}

