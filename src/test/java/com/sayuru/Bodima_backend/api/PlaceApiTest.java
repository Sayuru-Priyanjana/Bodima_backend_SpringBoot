package com.sayuru.Bodima_backend.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlaceApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void testAddPlace() {
        String placePayload = """
        {
          "place_name": "Test Apartment",
          "latitude": 6.9271,
          "longitude": 79.8612,
          "address": "Colombo, Sri Lanka",
          "rent": 25000,
          "description": "Nice apartment in city center",
          "type": "Apartment",
          "rooms": 2,
          "furniture_availability": true,
          "mobile": "0771234567",
          "views": 0,
          "owner": {
            "id": 1,
            "username": "testuser@example.com",
            "mobile": "0771234567",
            "user": true
          }
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(placePayload)
                .when()
                .post("/api/places")
                .then()
                .statusCode(200)
                .body("place_name", equalTo("Test Apartment"))
                .body("rent", equalTo(25000.0f))
                .body("address", equalTo("Colombo, Sri Lanka"));
    }
}
