package com.sayuru.Bodima_backend.models.mapper;

import com.sayuru.Bodima_backend.models.DTOs.ImageDTO;
import com.sayuru.Bodima_backend.models.DTOs.PlaceDTO;
import com.sayuru.Bodima_backend.models.DTOs.UserDTO;
import com.sayuru.Bodima_backend.models.Images;
import com.sayuru.Bodima_backend.models.Places;
import com.sayuru.Bodima_backend.models.Users;
import com.sayuru.Bodima_backend.repository.AuthRepo;
import com.sayuru.Bodima_backend.repository.PlacesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlaceMapper {

    @Autowired
    private AuthRepo authRepo;

    @Autowired
    private PlacesRepo placesRepo;


    public PlaceDTO toPlaceDTO(Places place) {
        if (place == null) return null;

        PlaceDTO dto = new PlaceDTO();
        // Direct field assignment works because names match
        dto.setPlace_id(place.getPlace_id());
        dto.setPlace_name(place.getPlace_name());
        dto.setLatitude(place.getLatitude());
        dto.setLongitude(place.getLongitude());
        dto.setAddress(place.getAddress());
        dto.setRent(place.getRent());
        dto.setDescription(place.getDescription());
        dto.setType(place.getType());
        dto.setRooms(place.getRooms());
        dto.setFurniture_availability(place.isFurniture_availability());
        dto.setMobile(place.getMobile());
        dto.setViews(place.getViews());
        dto.setCreated_at(place.getCreated_at());
        dto.setUpdated_at(place.getUpdated_at());

        // Map owner
        dto.setOwner(toUserDTO(place.getOwner()));

        // Map images
        if (place.getPlaceImages() != null) {
            dto.setPlaceImages(place.getPlaceImages().stream()
                    .map(this::toImageDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private UserDTO toUserDTO(Users user) {
        if (user == null) return null;

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getMobile(),
                user.isUser()

        );
    }

    private ImageDTO toImageDTO(Images image) {
        if (image == null) return null;

        return new ImageDTO(
                image.getPlace().getPlace_id(),
                image.getImage_id(),
                image.getImage_url(),
                image.is_primary()
        );
    }

    public Images toImageEntity(ImageDTO dto){
        if (dto == null) return  null;

        Images image = new Images();

        image.setImage_id(dto.getImage_id());
        image.setImage_url(dto.getImage_url());
        image.set_primary(dto.is_primary());
        image.setPlace(placesRepo.findById(dto.getPlace_id()).orElse(null));

        return image;
    }


    public List<Images> toImageEntityList(List<ImageDTO> imageDTOS){
        if (imageDTOS ==null) return null;
        return imageDTOS.stream().map(this::toImageEntity).collect(Collectors.toList());
    }



    public Places toPlaceEntity(PlaceDTO dto) {
            if (dto == null) return null;

            Places place = new Places();
            place.setOwner(authRepo.findByUsername(dto.getOwner().getUsername()));
            place.setPlace_id(dto.getPlace_id());
            place.setPlace_name(dto.getPlace_name());
            place.setLatitude(dto.getLatitude());
            place.setLongitude(dto.getLongitude());
            place.setAddress(dto.getAddress());
            place.setRent(dto.getRent());
            place.setDescription(dto.getDescription());
            place.setType(dto.getType());
            place.setRooms(dto.getRooms());
            place.setFurniture_availability(dto.isFurniture_availability());
            place.setMobile(dto.getMobile());
            place.setViews(dto.getViews());
            place.setCreated_at(dto.getCreated_at());
            place.setUpdated_at(dto.getUpdated_at());
            place.setPlaceImages(toImageEntityList(dto.getPlaceImages()));

            return place;
        }




}