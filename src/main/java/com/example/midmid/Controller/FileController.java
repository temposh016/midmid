package com.example.midmid.Controller;

import com.example.midmid.Service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/files")
@CrossOrigin(origins = "*")
public class FileController {

    private final MinioService minioService;
    
    @Autowired
    public FileController(MinioService minioService) {
        this.minioService = minioService;
    }
    
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("png", "txt", "json");

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Security жасап тур");
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("кате жол");
            }

            String extension = "";
            int lastDotIndex = originalFilename.lastIndexOf('.');
            if (lastDotIndex > 0 && lastDotIndex < originalFilename.length() - 1) {
                extension = originalFilename.substring(lastDotIndex + 1).toLowerCase();
            }

            if (!ALLOWED_EXTENSIONS.contains(extension)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("кате файл типы");
            }
            
            minioService.upload(file);
            return ResponseEntity.ok("файл жуктелды");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("файл жуктегендеуде кате: " + e.getMessage());
        }
    }

}
