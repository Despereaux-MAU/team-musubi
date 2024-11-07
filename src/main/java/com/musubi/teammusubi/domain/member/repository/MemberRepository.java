package com.musubi.teammusubi.domain.member.repository;

import com.musubi.teammusubi.common.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByUsername(String username);
}
