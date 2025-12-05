package com.example.midmid.Service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;
    private static final String BUCKET_NAME = "files";

    public void upload(MultipartFile file) throws Exception {

        try {
            if (!minioClient.bucketExists(io.minio.BucketExistsArgs.builder()
                    .bucket(BUCKET_NAME)
                    .build())) {
                minioClient.makeBucket(io.minio.MakeBucketArgs.builder()
                        .bucket(BUCKET_NAME)
                        .build());
            }
        } catch (Exception e) {

        }

        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(file.getOriginalFilename())
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }
    }
}

