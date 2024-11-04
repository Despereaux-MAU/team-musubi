package com.musubi.teammusubi.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String email;
    private String username;
    private String nickname;
    private String token;
    private String errorMessage;
}
