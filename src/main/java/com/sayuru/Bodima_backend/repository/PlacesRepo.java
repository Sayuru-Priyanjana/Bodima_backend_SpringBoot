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
    p.place_id, p.latitude, p.longitude,
    (6371 * ACOS(
        COS(RADIANS(:latitude)) *
        COS(RADIANS(p.latitude)) *
        COS(RADIANS(p.longitude) - RADIANS(:longitude)) +
        SIN(RADIANS(:latitude)) *
        SIN(RADIANS(p.latitude))
    )) AS distance_km
FROM places p
WHERE (6371 * ACOS(
        COS(RADIANS(:latitude)) *
        COS(RADIANS(p.latitude)) *
        COS(RADIANS(p.longitude) - RADIANS(:longitude)) +
        SIN(RADIANS(:latitude)) *
        SIN(RADIANS(p.latitude))
    )) <= :radius
ORDER BY distance_km
""", nativeQuery = true)
    List<Object[]> findWithinRadiusWithDistance(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("radius") double radiusInKm
    );


    List<Places> findByOwnerId(int ownerId);


}
