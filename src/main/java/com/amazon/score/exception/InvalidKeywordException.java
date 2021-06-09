package com.amazon.score.exception;

import com.amazon.score.enums.InvalidKeywordEnum;
import lombok.Data;

@Data
public class InvalidKeywordException extends ScoreAbstractException {
    private static final long serialVersionUID = 1L;

    public InvalidKeywordException(InvalidKeywordEnum invalidKeywordEnum) {
        super(invalidKeywordEnum.getCode(), invalidKeywordEnum.getMessage());
    }
}
