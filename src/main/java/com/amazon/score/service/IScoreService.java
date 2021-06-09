package com.amazon.score.service;

import com.amazon.score.dto.ResponseDTO;

public interface IScoreService {
    ResponseDTO estimateScore(String keyword);
}
