package com.sayuru.Bodima_backend.controller;

import com.sayuru.Bodima_backend.models.DTOs.PlaceDTO;
import com.sayuru.Bodima_backend.models.DTOs.PlaceDistanceDTO;
import com.sayuru.Bodima_backend.models.Places;
import com.sayuru.Bodima_backend.services.places.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }


    @PostMapping("")
    public Places addPlace(@RequestBody Places place){
        return placeService.addPlace(place);
    }

    @GetMapping("")
    public ResponseEntity<List<PlaceDTO>> getAllPlaces() {
        return ResponseEntity.ok(placeService.getAllPlaces());
    }

    @GetMapping("/{place_id}")
    public ResponseEntity<PlaceDTO> getSinglePlace(@PathVariable int place_id) {
        return ResponseEntity.ok(placeService.getSinglePlace(place_id));
    }

    @DeleteMapping("/{place_id}")
    public void deletePlace(@PathVariable int place_id){
        placeService.deletePlace(place_id);
    }

    @PutMapping("")
    public void updatePlace(@RequestBody PlaceDTO placeDTO){
        System.out.println(placeDTO.getPlace_id());
        placeService.updatePlace(placeDTO);

    }


    @GetMapping("/search/radius")
    public List<PlaceDTO> searchByRadius(@RequestParam double lat, @RequestParam double lng, @RequestParam double radius) {
        return placeService.searchPlacesWithinRadius(lat, lng, radius);
    }



}
