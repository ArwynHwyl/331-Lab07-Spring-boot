package com.example.demo.util;

import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupabaseStorageService {

    @Value("${supabase.storage.bucket}")
    String bucketName;

    @Value("${supabase.storage.endpoint_output}")
    String outputUrl;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddmmssSSS");

    private final S3Client s3Client;

    public String uploadFile(MultipartFile file) throws IOException {
        Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

        String saltFileName = LocalDateTime.now().format(formatter) + "-" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(saltFileName)
                .build();

        s3Client.putObject(putObjectRequest, tempFile);

        Files.deleteIfExists(tempFile);

        return saltFileName;
    }

    public StorageFileDto uploadImage(MultipartFile file) throws ServletException, IOException {
        String fileName = file.getOriginalFilename();
        if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase(Locale.ROOT);
            String[] allowedExt = {"jpg", "jpeg", "png", "gif"};
            for (String allowed : allowedExt) {
                if (allowed.equals(extension)) {
                    String storedName = this.uploadFile(file);
                    return StorageFileDto.builder()
                            .name(storedName)
                            .build();
                }
            }
        }
        throw new ServletException("file must be an image");
    }

    public String buildPublicUrl(String objectName) {
        return String.format("%s/object/public/%s/%s", outputUrl, bucketName, objectName);
    }

    public List<String> toPublicUrls(List<String> keysOrUrls) {
        if (keysOrUrls == null) return null;
        return keysOrUrls.stream()
                .map(v -> v == null ? null : (v.startsWith("http://") || v.startsWith("https://")) ? v : buildPublicUrl(v))
                .collect(Collectors.toList());
    }
}
