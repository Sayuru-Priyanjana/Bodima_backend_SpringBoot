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
public class AuthApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }
    @Test
    void testUserRegistration() {
        String payload = """
        {
          "username": "testuser@example.com",
          "password": "password123",
          "isUser": false
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(200)
                .body("user.username", equalTo("testuser@example.com"))
                .body("token", notNullValue());
    }

//    @Test
//    void testUserLogin() {
//        String payload = """
//    {
//      "username": "testlogin@example.com",
//      "password": "password123",
//      "isUser": true
//    }
//    """;
//
//        // Try register first
//        given()
//                .contentType(ContentType.JSON)
//                .body(payload)
//                .post("/api/auth/register")
//                .then()
//                .statusCode(anyOf(is(200), is(400))); // 400 if already exists
//
//        // Small delay to let DB transaction complete in CI
//        try { Thread.sleep(500); } catch (InterruptedException e) { }
//
//        // Now login
//        given()
//                .contentType(ContentType.JSON)
//                .body(payload)
//                .when()
//                .post("/api/auth/login")
//                .then()
//                .statusCode(200)   // ✅ should now succeed
//                .body("user.username", equalTo("testlogin@example.com"))
//                .body("token", notNullValue());
//    }

}
