package me.dio.parking.controller;

import io.restassured.RestAssured;
import me.dio.parking.controller.dto.ParkingCreateDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerTest {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest() {
        RestAssured.port = randomPort;
    }

    @Test
    void whenFingAllThenCheckResult() {
        RestAssured.given()
                .when()
                .get("/parking")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void whenCreateThenCheckIsCreated() {

        ParkingCreateDTO createDTO = new ParkingCreateDTO();
        createDTO.setColor("MARROM");
        createDTO.setLicense("TTT-6789");
        createDTO.setModel("MIURA");
        createDTO.setState("PR");

        RestAssured.given()
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDTO)
                .post("/parking")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("license", Matchers.equalTo("TTT-6789"))
                .body("color", Matchers.equalTo("MARROM"))
                .body("model", Matchers.equalTo("MIURA"))
                .body("state", Matchers.equalTo("PR"));
    }
}