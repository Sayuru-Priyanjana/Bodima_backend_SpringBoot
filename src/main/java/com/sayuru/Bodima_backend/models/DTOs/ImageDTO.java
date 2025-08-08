package com.sayuru.Bodima_backend.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private int place_id;
    private int image_id;          // matches entity field
    private String image_url;      // matches entity field
    private boolean is_primary;    // matches entity field

}
