package com.amazon.score;

import com.amazon.score.config.AmazonAutocompleteConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
@EnableConfigurationProperties(AmazonAutocompleteConfig.class)
public class AmazonScoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonScoreApplication.class, args);
	}

}
