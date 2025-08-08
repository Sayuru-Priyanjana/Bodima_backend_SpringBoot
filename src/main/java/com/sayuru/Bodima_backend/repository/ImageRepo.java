package com.sayuru.Bodima_backend.repository;

import com.sayuru.Bodima_backend.models.Images;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepo extends JpaRepository<Images, Integer> {
    public interface ImagesRepository extends JpaRepository<Images, Integer> {

        List<Images> findByPlacePlaceID(List<Integer> placeIds);
    }
}
