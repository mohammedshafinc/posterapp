package com.wydex.posterapp.controller;

import com.wydex.posterapp.service.ImageService;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PosterController {

    private static final Logger logger = LoggerFactory.getLogger(PosterController.class);

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<byte[]> handleImageUpload(@RequestParam("photo") MultipartFile photo) {
        logger.info("Received image upload request: original filename = {}", photo.getOriginalFilename());
        try {
            byte[] finalImage = imageService.generatePoster(photo);
            logger.info("Poster generated successfully for file: {}", photo.getOriginalFilename());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(finalImage);
        } catch (Exception e) {
            logger.error("Failed to process image upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
