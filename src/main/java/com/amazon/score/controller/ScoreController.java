package com.amazon.score.controller;

import com.amazon.score.dto.ResponseDTO;
import com.amazon.score.service.IScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
@Api("Email Event Controller")
public class ScoreController {

    private IScoreService scoreService;

    @GetMapping("/estimate")
    @ApiOperation(value = "return the score of provided keyword using Amazon Auto Complete API",
            notes = "need to provide the keyword")
    public ResponseDTO getKeywordEstimate(@RequestParam String keyword) {
        return scoreService.estimateScore(keyword);
    }
}
