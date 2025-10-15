package com.example.demo.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class SupabaseStorageService {

    @Value("${supabase.storage.bucket}")
    String bucketName;

    @Value("${supabase.storage.endpoint_output}")
    String outputUrl;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddmmssSSS");

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    public String uploadFile(MultipartFile file) throws IOException {
        // Create temp file and copy uploaded content
        Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

        // Salt filename with timestamp to avoid collision
        String saltFileName = LocalDateTime.now().format(formatter) + "-" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(saltFileName)
                .build();

        s3Client.putObject(putObjectRequest, tempFile);

        Files.deleteIfExists(tempFile);

        // Build a public URL based on provided output endpoint
        String url = String.format("%s/%s/%s", outputUrl, bucketName, saltFileName);
        return url;
    }
}
