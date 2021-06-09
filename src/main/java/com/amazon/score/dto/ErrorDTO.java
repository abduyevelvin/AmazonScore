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
public class ErrorDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer code;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String message;
}
