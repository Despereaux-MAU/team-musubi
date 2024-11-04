package com.musubi.teammusubi.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalException extends RuntimeException{
    private final String errCode;
    private final HttpStatus errStatus;
    private final String errmessage;

    public GlobalException(String errCode, HttpStatus errStatus, String errmessage) {
        super(errmessage);
        this.errCode = errCode;
        this.errStatus = errStatus;
        this.errmessage = errmessage;
    }
}
