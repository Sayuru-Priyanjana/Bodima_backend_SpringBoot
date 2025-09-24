package com.sayuru.Bodima_backend.services.places;


import com.sayuru.Bodima_backend.models.DTOs.PlaceDTO;
import com.sayuru.Bodima_backend.models.DTOs.PlaceDistanceDTO;
import com.sayuru.Bodima_backend.models.Images;
import com.sayuru.Bodima_backend.models.Places;
import com.sayuru.Bodima_backend.models.Users;
import com.sayuru.Bodima_backend.models.mapper.PlaceMapper;
import com.sayuru.Bodima_backend.repository.AuthRepo;
import com.sayuru.Bodima_backend.repository.ImageRepo;
import com.sayuru.Bodima_backend.repository.PlacesRepo;
import com.sayuru.Bodima_backend.services.Error.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    private final PlacesRepo placesRepo;
    private final AuthRepo authRepo;
    private PlaceMapper placeMapper;
    private  ImageRepo imageRepo;


    public PlaceService(PlacesRepo placesRepo, AuthRepo authRepo, PlaceMapper placeMapper, ImageRepo imageRepo) {
        this.placesRepo = placesRepo;
        this.authRepo = authRepo;
        this.placeMapper = placeMapper;
        this.imageRepo = imageRepo;
    }

    public List<PlaceDTO> getAllPlaces() {
        List<Places> places = placesRepo.findAll();
        return places.stream()
                .map(placeMapper::toPlaceDTO)
                .collect(Collectors.toList());
    }

    public Places addPlace(Places place){
        if (place.getPlace_name() == null || place.getPlace_name().isBlank()) {
            throw new RuntimeException("Place name cannot be empty");
        }
        if (place.getRent() <= 0) {
            throw new RuntimeException("Rent must be greater than 0");
        }
        return placesRepo.save(place);
    }


    public PlaceDTO getSinglePlace(int place_id) {
        return placesRepo.findById(place_id)
                .map(placeMapper::toPlaceDTO)
                .orElseThrow(() -> new RuntimeException("Place not found with id: " + place_id));

    }

    public void deletePlace(int place_id){
        if (!placesRepo.existsById(place_id)){
            throw new ResourceNotFoundException("Place not found with id: " + place_id);
        }
        placesRepo.deleteById(place_id);
    }

    public void updatePlace(PlaceDTO placeDTO){
        System.out.println(placeDTO.toString());
        Places place = placeMapper.toPlaceEntity(placeDTO);
        placesRepo.save(place);
    }

    public List<PlaceDTO> searchPlacesWithinRadius(double centerLat, double centerLng, double radiusKm) {
        List<Object[]> results = placesRepo.findWithinRadiusWithDistance(centerLat, centerLng, radiusKm);

        return results.stream().map(result -> {

            // Get the calculated distance
            Double distance = (Double) result[3];

            PlaceDTO placeDTO = getSinglePlace((Integer) result[0]);
            placeDTO.setDistanceFromCenter(distance);

            return placeDTO;
        }).collect(Collectors.toList());
    }

    public List<PlaceDTO> getPlacesByUserId(int userId) {
        List<Places> places = placesRepo.findByOwnerId(userId);
        return places.stream()
                .map(placeMapper::toPlaceDTO)
                .collect(Collectors.toList());
    }

}
