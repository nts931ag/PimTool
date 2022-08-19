package com.elca.internship.client.config;

import com.elca.internship.client.exception.ErrorResponse;
import com.elca.internship.client.exception.ProjectException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class ProjectConfiguration {

    @Bean
    public WebClient webClient(){

        Function<ClientResponse, Mono<ClientResponse>> webClientResponseProcessor=
              clientResponse -> {
                  Mono<ErrorResponse> errorMessage = clientResponse.bodyToMono(ErrorResponse.class);
                  HttpStatus responseStatus = clientResponse.statusCode();
                  if (responseStatus.is4xxClientError()) {
                      return errorMessage.flatMap(
                              message -> Mono.error(new ProjectException(message.getStatusMsg(), message.getI18nKey(), message.getI18nValue()))
                      );
                  } else if (responseStatus.is5xxServerError()) {
                      return errorMessage.flatMap(
                              message -> Mono.error(new WebClientResponseException(clientResponse.rawStatusCode(),clientResponse.statusCode().toString(), null, null, null))
                      );
                  }
                  return Mono.just(clientResponse);
              };

        return WebClient.builder()
                .filter(ExchangeFilterFunction.ofResponseProcessor(webClientResponseProcessor))
                .build();
    }

}
