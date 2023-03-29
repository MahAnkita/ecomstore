package com.ankitacodes.Ecom.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BadApiRequestException extends RuntimeException{
    String msg;
    public BadApiRequestException(String msg) {
        super(String.format("%s",msg));
        this.msg = msg;
    }
}
