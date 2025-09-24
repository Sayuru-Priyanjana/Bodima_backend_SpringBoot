package com.sayuru.Bodima_backend.unit;

import com.sayuru.Bodima_backend.models.Places;
import com.sayuru.Bodima_backend.models.DTOs.PlaceDTO;
import com.sayuru.Bodima_backend.models.mapper.PlaceMapper;
import com.sayuru.Bodima_backend.repository.PlacesRepo;
import com.sayuru.Bodima_backend.repository.AuthRepo;
import com.sayuru.Bodima_backend.repository.ImageRepo;
import com.sayuru.Bodima_backend.services.places.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaceServiceUnitTest {

    @Mock
    private PlacesRepo placesRepo;

    @Mock
    private AuthRepo authRepo;

    @Mock
    private ImageRepo imageRepo;

    @Mock
    private PlaceMapper placeMapper;

    @InjectMocks
    private PlaceService placeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPlaces() {
        // Prepare mock data
        Places place1 = new Places();
        place1.setPlace_name("Place One");
        Places place2 = new Places();
        place2.setPlace_name("Place Two");

        when(placesRepo.findAll()).thenReturn(Arrays.asList(place1, place2));
        when(placeMapper.toPlaceDTO(any())).thenAnswer(invocation -> {
            Places p = invocation.getArgument(0);
            PlaceDTO dto = new PlaceDTO();
            dto.setPlace_name(p.getPlace_name());
            return dto;
        });

        // Call service
        List<PlaceDTO> result = placeService.getAllPlaces();

        // Assertions
        assertEquals(2, result.size());
        assertEquals("Place One", result.get(0).getPlace_name());
        assertEquals("Place Two", result.get(1).getPlace_name());

        verify(placesRepo, times(1)).findAll();
    }

    @Test
    @Tag("unit")
    public void testAddPlace_success() {
        Places place = new Places();
        place.setPlace_name("Test Place");
        place.setRent(5000.0);

        when(placesRepo.save(place)).thenReturn(place);

        // Call service
        Places saved = placeService.addPlace(place);

        // Assertions
        assertNotNull(saved);
        assertEquals("Test Place", saved.getPlace_name());
        assertEquals(5000.0, saved.getRent());

        verify(placesRepo, times(1)).save(place);
    }

    @Test
    @Tag("unit")
    public void testAddPlace_fail_missingName() {
        Places place = new Places();
        place.setRent(3000.0);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            placeService.addPlace(place);
        });

        assertEquals("Place name cannot be empty", exception.getMessage());
        verify(placesRepo, never()).save(any());
    }
}
