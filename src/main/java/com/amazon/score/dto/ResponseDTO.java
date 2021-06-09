package com.amazon.score.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String keyword;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long score;
}
