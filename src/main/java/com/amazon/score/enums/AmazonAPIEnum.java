package com.amazon.score.enums;

import lombok.Getter;

@Getter
public enum AmazonAPIEnum {
    AMAZON__API_ISSUE(111, "amazon_api_issue");

    private int code;
    private String message;

    AmazonAPIEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
