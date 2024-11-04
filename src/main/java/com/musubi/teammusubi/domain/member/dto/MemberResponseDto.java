package com.musubi.teammusubi.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.musubi.teammusubi.common.enums.MemberRoleEnum;
import lombok.Getter;
import lombok.Setter;

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
