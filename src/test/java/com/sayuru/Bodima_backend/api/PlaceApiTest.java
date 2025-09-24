package com.sayuru.Bodima_backend.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlaceApiTest {

    @LocalServerPort
    private int port;

    private String jwtToken;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        // Login to get JWT token
        Map<String, Object> user = new HashMap<>();
        user.put("username", "test@gmail.com");
        user.put("password", "1234");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .response();

        jwtToken = response.jsonPath().getString("token");
    }

    @Test
    @Tag("integration")
    public void testAddPlace_success() {
        Map<String, Object> place = new HashMap<>();
        Map<String, Object> owner = new HashMap<>();
        owner.put("id", 4);
        place.put("owner", owner);
        place.put("place_name", "Beachfront Villa");
        place.put("location", "6.080272369763181, 80.19198050299455");
        place.put("address", "123 Beach Road, Colombo 03");
        place.put("rent", 75000.00);
        place.put("description", "Beautiful beachfront property with 3 bedrooms and ocean view");
        place.put("type", "House");
        place.put("rooms", 3);
        place.put("furniture_availability", true);
        place.put("mobile", "+94771234567");
        place.put("views", 0);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(place)
                .when()
                .post("/api/places")
                .then()
                .statusCode(200)
                .body("place_name", equalTo("Beachfront Villa"))
                .body("rent", equalTo(75000.0f))
                .body("rooms", equalTo(3))
                .body("furniture_availability", equalTo(true));
    }

    @Test
    @Tag("integration")
    public void testAddPlace_fail_missingName() {
        Map<String, Object> place = new HashMap<>();
        place.put("rent", 3000.0);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(place)
                .when()
                .post("/api/places")
                .then()
                .statusCode(500);
    }
}
