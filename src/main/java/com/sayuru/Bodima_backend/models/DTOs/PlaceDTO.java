package com.sayuru.Bodima_backend.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {
    private int place_id;          // matches entity field
    private String place_name;     // matches entity field
    private Double latitude;
    private Double longitude;
    private String address;
    private double rent;
    private String description;
    private String type;
    private int rooms;
    private boolean furniture_availability;  // matches entity field
    private String mobile;
    private int views;
    private Date created_at;       // matches entity field
    private Date updated_at;       // matches entity field
    private UserDTO owner;
    private List<ImageDTO> placeImages;

    private double distanceFromCenter;



}
