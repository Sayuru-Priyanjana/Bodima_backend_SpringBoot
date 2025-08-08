package com.sayuru.Bodima_backend.repository;

import com.sayuru.Bodima_backend.models.DTOs.PlaceDTO;
import com.sayuru.Bodima_backend.models.Places;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlacesRepo extends JpaRepository<Places,Integer> {


    @Query(value = """
    SELECT 
        p.place_id,p.latitude, p.longitude,
        (ST_Distance_Sphere(
            POINT(:longitude, :latitude),
            POINT(p.longitude, p.latitude)
        ) / 1000) AS distance_km
    FROM Places p
    WHERE ST_Distance_Sphere(
        POINT(:longitude, :latitude),
        POINT(p.longitude, p.latitude)
    ) <= :radius * 1000
    ORDER BY distance_km
    """, nativeQuery = true)
    List<Object[]> findWithinRadiusWithDistance(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("radius") double radiusInKm
    );

}
