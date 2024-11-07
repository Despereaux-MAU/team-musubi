package com.musubi.teammusubi.domain.member.service;

import com.musubi.teammusubi.common.config.PasswordEncoder;
import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.common.enums.MemberRoleEnum;
import com.musubi.teammusubi.common.util.JwtUtil;
import com.musubi.teammusubi.domain.member.dto.LoginRequest;
import com.musubi.teammusubi.domain.member.dto.MemberRequest;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private MemberService memberService;

    private MemberRequest memberRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        memberRequest = new MemberRequest();
        memberRequest.setEmail("test@example.com");
        memberRequest.setUsername("testUser");
        memberRequest.setNickname("tester");
        memberRequest.setPassword("Password123!");
        memberRequest.setPasswordCheck("Password123!");
        memberRequest.setRole(MemberRoleEnum.USER);
        memberRequest.setPhone("010-1234-5678");
        memberRequest.setAddress("서울시 강남구");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("Password123!");
    }

    @Test
    void registerMember() {

        Member member = new Member();

        when(memberRepository.save(any(Member.class))).thenReturn(member);

        assertDoesNotThrow(() -> memberService.registerMember(memberRequest));
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void login() {

        Member member = new Member();
        member.setId(1L);
        member.setEmail(loginRequest.getEmail());
        member.setPassword("encodedPassword");
        member.setRole(MemberRoleEnum.USER);

        when(memberRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())).thenReturn(true);
        doReturn("mockToken").when(jwtUtil).generateToken(eq(1L), eq("USER"));

        try {
            memberService.login(loginRequest, response);
        } catch (Exception e) {
            fail("예외가 발생했습니다: " + e.getMessage());
        }
    }

    @Test
    void authenticate() {

        Member member = new Member();
        member.setPassword("encodedPassword");

        when(memberRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(anyLong(), anyString())).thenReturn("mockToken");

        String token = memberService.authenticate(loginRequest);
        assertNotNull(token);
        assertEquals("mockToken", token);
    }

    @Test
    void getProfile() {

        String email = memberRequest.getEmail();
        Member member = new Member();
        member.setEmail(email);

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

        assertDoesNotThrow(() -> memberService.getProfile(email));
    }

    @Test
    void findByEmail() {

        String email = memberRequest.getEmail();
        Member member = new Member();
        member.setEmail(email);

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

        Member foundMember = memberService.findByEmail(email);
        assertNotNull(foundMember);
        assertEquals(email, foundMember.getEmail());
    }

    @Test
    void updateMember() {

        String email = memberRequest.getEmail();
        Member member = new Member();
        member.setEmail(email);

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

        assertDoesNotThrow(() -> memberService.updateMember(email, memberRequest));
        assertEquals(memberRequest.getUsername(), member.getUsername());
        assertEquals(memberRequest.getNickname(), member.getNickname());
        assertEquals(memberRequest.getAddress(), member.getAddress());
    }

    @Test
    void changePassword() {

        String email = memberRequest.getEmail();
        String newPassword = "newPassword123!";
        Member member = new Member();

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

        try {
            memberService.changePassword(email, newPassword, newPassword);
            verify(memberRepository, times(1)).save(any(Member.class));
        } catch (Exception e) {
            fail("예외가 발생했습니다: " + e.getMessage());
        }
    }

    @Test
    void deActivateMember() {

        String email = memberRequest.getEmail();
        Member member = new Member();

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

        assertDoesNotThrow(() -> memberService.deActivateMember(email));
        verify(memberRepository, times(1)).save(any(Member.class));
    }
}
