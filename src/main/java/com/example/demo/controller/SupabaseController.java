package com.example.demo.controller;

import com.example.demo.util.StorageFileDto;
import com.example.demo.util.SupabaseStorageService;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SupabaseController {

    private final SupabaseStorageService supabaseStorageService;

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        MultipartFile payload = file != null ? file : image;
        if (payload == null || payload.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "No file content received"));
        }
        try {
            String storedName = supabaseStorageService.uploadFile(payload);
            return ResponseEntity.ok(StorageFileDto.builder().name(storedName).build());
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Error uploading file", "error", e.getMessage()));
        }
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        MultipartFile payload = image != null ? image : file;
        if (payload == null || payload.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "No file content received"));
        }
        try {
            StorageFileDto fileUrl = supabaseStorageService.uploadImage(payload);
            return ResponseEntity.ok(fileUrl);
        } catch (ServletException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Error uploading file", "error", e.getMessage()));
        }
    }
}
