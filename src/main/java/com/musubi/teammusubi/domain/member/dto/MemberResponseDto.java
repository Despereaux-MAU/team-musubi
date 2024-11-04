package com.musubi.teammusubi.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.musubi.teammusubi.domain.member.entity.MemberRoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberResponseDto {
    @JsonIgnore
    private Long id;
    private String email;
    private String username;
    private String nickname;
    private MemberRoleEnum role;
    private String phone;
    private String address;
}
