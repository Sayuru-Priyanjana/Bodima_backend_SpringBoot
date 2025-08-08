package com.sayuru.Bodima_backend.services.images;

import com.sayuru.Bodima_backend.models.DTOs.ImageDTO;
import com.sayuru.Bodima_backend.models.Images;
import com.sayuru.Bodima_backend.repository.ImageRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private ImageRepo imageRepo;

    public ImageService(ImageRepo imageRepo) {
        this.imageRepo = imageRepo;
    }

    public void AddImages(List<Images> images){
        imageRepo.saveAll(images);

    }




}
