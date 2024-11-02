package com.musubi.teammusubi.domain.member.dto;

import com.musubi.teammusubi.domain.member.entity.MemberRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDto {
    private String email;
    private String username;
    private String nickname;
    private String password;
    private MemberRoleEnum role;
    private String address;
    private String phone;
}
