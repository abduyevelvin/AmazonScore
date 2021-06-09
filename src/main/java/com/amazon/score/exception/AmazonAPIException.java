package com.amazon.score.exception;

import com.amazon.score.enums.AmazonAPIEnum;
import lombok.Data;

@Data
public class AmazonAPIException extends ScoreAbstractException {
    private static final long serialVersionUID = 1L;

    public AmazonAPIException(AmazonAPIEnum amazonAPIEnum) {
        super(amazonAPIEnum.getCode(), amazonAPIEnum.getMessage());
    }
}
