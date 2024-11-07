package com.musubi.teammusubi.domain.member.dto;

import com.musubi.teammusubi.common.enums.MemberRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {
    private String email;
    private String username;
    private String nickname;
    private String password;
    private String passwordCheck;
    private MemberRoleEnum role;
    private String phone;
    private String address;
}
