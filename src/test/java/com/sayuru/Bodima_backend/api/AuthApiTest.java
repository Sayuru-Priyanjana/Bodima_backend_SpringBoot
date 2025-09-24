package com.sayuru.Bodima_backend.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void testLogin_success() {
        Map<String, Object> user = new HashMap<>();
        user.put("username", "test@gmail.com");
        user.put("password", "1234");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("user.username", equalTo("test@gmail.com"))
                .body("user.isUser", equalTo(false));
    }

    @Test
    public void testLogin_fail_invalidUser() {
        Map<String, Object> user = new HashMap<>();
        user.put("username", "invaliduser");
        user.put("password", "wrongpass");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(500);
    }
}
