package com.sayuru.Bodima_backend;

import com.sayuru.Bodima_backend.models.DTOs.PlaceDTO;
import com.sayuru.Bodima_backend.models.Places;
import com.sayuru.Bodima_backend.models.Users;
import com.sayuru.Bodima_backend.models.mapper.PlaceMapper;
import com.sayuru.Bodima_backend.repository.AuthRepo;
import com.sayuru.Bodima_backend.repository.ImageRepo;
import com.sayuru.Bodima_backend.repository.PlacesRepo;
import com.sayuru.Bodima_backend.services.places.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class PlaceServiceTest {

    private PlacesRepo placesRepo;
    private AuthRepo authRepo;
    private PlaceMapper placeMapper;
    private ImageRepo imageRepo;
    private PlaceService placeService;

    @BeforeEach
    void setUp() {
        placesRepo = mock(PlacesRepo.class);
        authRepo = mock(AuthRepo.class);
        placeMapper = mock(PlaceMapper.class);
        imageRepo = mock(ImageRepo.class);

        placeService = new PlaceService(placesRepo, authRepo, placeMapper, imageRepo);
    }

    @Test
    void shouldAddPlaceSuccessfully() {
        // Arrange
        Users owner = new Users();
        owner.setId(1);

        Places place = new Places();
        place.setPlace_id(1);
        place.setOwner(owner);
        place.setPlace_name("Test Place");

        when(placesRepo.save(place)).thenReturn(place);

        // Act
        Places saved = placeService.addPlace(place);

        // Assert
        assertEquals("Test Place", saved.getPlace_name());
        verify(placesRepo, times(1)).save(place);
    }


    @Test
    void shouldReturnSinglePlace_WhenExists() {
        Places place = new Places();
        place.setPlace_id(1);
        place.setPlace_name("Test Place");

        PlaceDTO dto = new PlaceDTO();
        dto.setPlace_id(1);
        dto.setPlace_name("Test Place");

        when(placesRepo.findById(1)).thenReturn(Optional.of(place));
        when(placeMapper.toPlaceDTO(place)).thenReturn(dto);

        PlaceDTO result = placeService.getSinglePlace(1);

        assertEquals("Test Place", result.getPlace_name());
    }



}
