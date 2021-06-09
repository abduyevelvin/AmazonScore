package com.amazon.score.controller;

import com.amazon.score.dto.ErrorDTO;
import com.amazon.score.exception.AmazonAPIException;
import com.amazon.score.exception.InvalidKeywordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RestController
@CrossOrigin
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidKeywordException.class})
    public Object handleException(InvalidKeywordException exception) {
        return ErrorDTO
                .builder()
                .code(exception.getErrorCode())
                .message(exception.getMessage())
                .build();
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({AmazonAPIException.class})
    public Object handleException(AmazonAPIException exception) {
        return ErrorDTO
                .builder()
                .code(exception.getErrorCode())
                .message(exception.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handleException(MissingServletRequestParameterException exception) {
        return ErrorDTO
                .builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
    }
}
