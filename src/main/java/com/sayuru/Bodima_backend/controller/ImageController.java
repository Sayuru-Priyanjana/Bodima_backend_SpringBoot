package com.sayuru.Bodima_backend.controller;

import com.sayuru.Bodima_backend.models.DTOs.ImageDTO;
import com.sayuru.Bodima_backend.models.Images;
import com.sayuru.Bodima_backend.services.images.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/bulk")
    public void addImages(@RequestBody List<Images> images){
        imageService.AddImages(images);
    }


}
