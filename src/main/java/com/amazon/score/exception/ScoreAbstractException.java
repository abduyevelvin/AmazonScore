package com.amazon.score.exception;

import lombok.Data;

@Data
public class ScoreAbstractException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected final Integer errorCode;
    protected final String message;

    public ScoreAbstractException(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
