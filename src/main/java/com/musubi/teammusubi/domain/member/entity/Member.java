package com.musubi.teammusubi.domain.member.entity;

import com.musubi.teammusubi.common.entity.Timestamped;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRoleEnum role;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;
}
