package com.amazon.score.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "amazon.autocomplete.api.client")
public class AmazonAutocompleteConfig {
    private String searchAlias;
    private String searchUI;
    private Integer mkt;
}
