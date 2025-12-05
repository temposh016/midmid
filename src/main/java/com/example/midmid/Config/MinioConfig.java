package com.example.midmid.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class MinioConfig {

    @Configuration
    public class MinioConfig {
        @Value("${minio.url}")
        private String url;

        @Value("${minio.access}")
        private String access;

        @Value("${minio.secret}")
        private String secret;

        @Bean
        public MinioClient minioClient(){
            return MinioClient.builder()
                    .endpoint(url)
                    .credentials(access, secret)
                    .build();

        }

    }
}
