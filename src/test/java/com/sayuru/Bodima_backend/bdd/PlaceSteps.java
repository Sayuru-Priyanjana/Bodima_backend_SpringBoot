package com.sayuru.Bodima_backend.bdd;

import com.sayuru.Bodima_backend.models.Places;
import com.sayuru.Bodima_backend.models.Users;
import com.sayuru.Bodima_backend.repository.PlacesRepo;
import com.sayuru.Bodima_backend.services.places.PlaceService;
import com.sayuru.Bodima_backend.services.Error.ResourceNotFoundException;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaceSteps {

    private PlacesRepo placesRepo;
    private PlaceService placeService;
    private Places place;
    private Users user;
    private Exception exception;

    @Given("a user with id {int} exists")
    public void a_user_with_id_exists(Integer userId) {
        placesRepo = mock(PlacesRepo.class);
        placeService = new PlaceService(placesRepo, null, null, null);

        user = new Users();
        user.setId(userId);
    }

    @When("the user adds a place with name {string} and rent {double}")
    public void the_user_adds_a_place_with_name_and_rent(String name, Double rent) {
        place = new Places();
        place.setOwner(user);
        place.setPlace_name(name);
        place.setRent(rent);

        when(placesRepo.save(place)).thenReturn(place);
        place = placeService.addPlace(place);
    }

    @When("the user adds a place with an empty name")
    public void the_user_adds_a_place_with_an_empty_name() {
        place = new Places();
        place.setOwner(user);
        place.setPlace_name(""); // invalid
        try {
            placeService.addPlace(place);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the place should be saved successfully")
    public void the_place_should_be_saved_successfully() {
        assertNotNull(place);
        assertEquals("Test Place", place.getPlace_name());
        assertEquals(5000, place.getRent());
    }

    @Then("the system should reject the place")
    public void the_system_should_reject_the_place() {
        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException
                || exception instanceof ResourceNotFoundException);
    }
}
