package com.sayuru.Bodima_backend.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDistanceDTO {
    private PlaceDTO place;
    private double distanceKm;
}
