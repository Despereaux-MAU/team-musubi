package com.musubi.teammusubi.domain.member.service;

import com.musubi.teammusubi.domain.member.entity.Member;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 EMAIL 입니다: " + email));
    }
}
