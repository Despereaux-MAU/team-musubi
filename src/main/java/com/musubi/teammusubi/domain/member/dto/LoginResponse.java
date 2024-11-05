package com.musubi.teammusubi.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String email;
    private String username;
    private String nickname;
    @JsonIgnore
    private String token;
    @JsonIgnore
    private String id;
    @JsonIgnore
    private String errorMessage;
}
