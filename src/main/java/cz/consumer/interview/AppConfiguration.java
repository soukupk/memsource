package cz.consumer.interview;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import cz.consumer.interview.memsource.MemsourceApi;
import cz.consumer.interview.memsource.MemsourceConfig;
import cz.consumer.interview.memsource.MemsourceRestApi;

@Configuration
public class AppConfiguration {
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public MemsourceApi memsourceRestApi(RestTemplate restTemplate, MemsourceConfig memsourceConfig) {
		return new MemsourceRestApi(restTemplate, memsourceConfig.url);
	}
	
}
