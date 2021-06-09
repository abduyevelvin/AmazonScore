package com.amazon.score.enums;

import lombok.Getter;

@Getter
public enum InvalidKeywordEnum {
    INVALID_KEYWORD(222, "invalid_keyword");

    private int code;
    private String message;

    InvalidKeywordEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
