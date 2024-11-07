package com.musubi.teammusubi.domain.member.service;

import com.musubi.teammusubi.common.security.MemberDetailsImpl;
import com.musubi.teammusubi.common.entity.Member;
import com.musubi.teammusubi.domain.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 회원입니다." + email));

        if (member.isDeActive()) {
            throw new UsernameNotFoundException("탈퇴한 회원입니다." + email);
        }

        return new MemberDetailsImpl(member);
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 회원입니다." + id));

        if (member.isDeActive()) {
            throw new UsernameNotFoundException("탈퇴한 회원입니다." + id);
        }

        return  new MemberDetailsImpl(member);
    }
}
