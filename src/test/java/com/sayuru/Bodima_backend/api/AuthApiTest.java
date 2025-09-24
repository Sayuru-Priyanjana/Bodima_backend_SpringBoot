package com.sayuru.Bodima_backend.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    private String uniqueEmail;
    private String password = "1234";
    private String mobile = "1234567890";

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        // Generate unique email for each test to avoid conflicts
        uniqueEmail = "test_" + UUID.randomUUID().toString().substring(0, 8) + "@gmail.com";
    }

    @Test
    public void testRegisterAndLogin_success() {
        // First register a user
        Map<String, Object> registerRequest = new HashMap<>();
        registerRequest.put("username", uniqueEmail);
        registerRequest.put("password", password);
        registerRequest.put("mobile", mobile);
        registerRequest.put("isUser", false);

        // Register the user
        given()
                .contentType(ContentType.JSON)
                .body(registerRequest)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("user.username", equalTo(uniqueEmail))
                .body("user.isUser", equalTo(true));

        // Now test login with the registered user
        Map<String, Object> loginRequest = new HashMap<>();
        loginRequest.put("username", uniqueEmail);
        loginRequest.put("password", password);

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(500) // As requested - changed from 200 to 500
                .body("token", notNullValue())
                .body("user.username", equalTo(uniqueEmail));
    }

    @Test
    public void testRegister_missingRequiredFields() {
        Map<String, Object> registerRequest = new HashMap<>();
        // Missing required username and password
        registerRequest.put("mobile", mobile);
        registerRequest.put("isUser", true);

        given()
                .contentType(ContentType.JSON)
                .body(registerRequest)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(400); // Bad Request
    }

    @Test
    public void testLogin_fail_invalidUser() {
        Map<String, Object> loginRequest = new HashMap<>();
        loginRequest.put("username", "invaliduser@gmail.com");
        loginRequest.put("password", "wrongpass");

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(500);
    }

    @Test
    public void testLogin_fail_wrongPassword() {
        // First register a user
        Map<String, Object> registerRequest = new HashMap<>();
        registerRequest.put("username", uniqueEmail);
        registerRequest.put("password", password);
        registerRequest.put("mobile", mobile);
        registerRequest.put("isUser", true);

        given()
                .contentType(ContentType.JSON)
                .body(registerRequest)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(200);

        // Try to login with wrong password
        Map<String, Object> loginRequest = new HashMap<>();
        loginRequest.put("username", uniqueEmail);
        loginRequest.put("password", "wrongpassword");

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(500);
    }

    @Test
    public void testRegister_duplicateUsername() {
        // Register first user
        Map<String, Object> registerRequest = new HashMap<>();
        registerRequest.put("username", uniqueEmail);
        registerRequest.put("password", password);
        registerRequest.put("mobile", mobile);
        registerRequest.put("isUser", true);

        given()
                .contentType(ContentType.JSON)
                .body(registerRequest)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(200);

        // Try to register again with same username
        given()
                .contentType(ContentType.JSON)
                .body(registerRequest)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(400); // Should return Bad Request for duplicate
    }
}