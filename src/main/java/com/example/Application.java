package com.example;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class Application {

    @Bean
    public RestTemplate restTemplate() {
        // 添加拦截器，使用 gzip 编码提交
        ClientHttpRequestInterceptor interceptor = (httpRequest, bytes, execution) -> {
            httpRequest.getHeaders().set("user-agent",   "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
            httpRequest.getHeaders().set(HttpHeaders.ACCEPT, MediaType.TEXT_HTML_VALUE);
            httpRequest.getHeaders().set(HttpHeaders.ACCEPT_ENCODING, "gzip");   // 使用 gzip 编码提交
            return execution.execute(httpRequest, bytes);
        };

        HttpComponentsClientHttpRequestFactory
            clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.getInterceptors().add(interceptor);
        restTemplate.getMessageConverters().add(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
