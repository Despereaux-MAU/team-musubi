package com.musubi.teammusubi.common.Security;

import com.musubi.teammusubi.common.exception.GlobalException;
import com.musubi.teammusubi.common.util.JwtUtil;
import com.musubi.teammusubi.domain.member.service.MemberDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberDetailsServiceImpl memberDetailsServiceImpl;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, MemberDetailsServiceImpl memberDetailsServiceImpl) {
        this.jwtUtil = jwtUtil;
        this.memberDetailsServiceImpl = memberDetailsServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String token = getJwtFromRequest(request);
        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.getUsernameFromToken(token);
            String role = jwtUtil.getRoleFromToken(token);

            UserDetails userDetails = memberDetailsServiceImpl.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, Collections.singletonList(new SimpleGrantedAuthority(role)));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            if (request.getRequestURI().startsWith("/api/seller") && !"OWNER".equals(role)) {
                throw new GlobalException("U0003", HttpStatus.FORBIDDEN, "접근이 거부되었습니다.");
            }

        }
        chain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
