package com.musubi.teammusubi.common.config;

import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.findByEmail("test@example.com").ifPresent(memberRepository::delete);
    }

    @Test
    void securityFilterChain() throws Exception {
        SecurityFilterChain securityFilterChain = securityConfig.securityFilterChain(null);
        assertNotNull(securityFilterChain, "SecurityFilterChain 이 생성되지 않았습니다.");
    }

    @Test
    void jwtAuthorizationFilter() {
        assertNotNull(securityConfig.jwtAuthorizationFilter(), "JwtAuthorizationFilter 이 생성되지 않았습니다.");
    }

    @Test
    void authenticationManager(@Autowired AuthenticationManager authenticationManager) {
        assertNotNull(authenticationManager, "AuthenticationManager 가 생성되지 않았습니다.");
    }

    @Test
    void shouldRestrictAccessToProtectedEndpointWithoutAuth() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/seller/secure-endpoint")).andReturn();
        assertEquals(403, result.getResponse().getStatus(), "보호된 엔드포인트에 대한 응답 상태 코드가 403이 아닙니다.");
    }
}
