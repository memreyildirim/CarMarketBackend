package com.example.springboot;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

import static io.restassured.RestAssured.given;

@Slf4j
public class ApiUtils {

    public static Long createTestCar(){
        //Base URL ayarla
        RestAssured.baseURI = "http://localhost:10160";

        File testImage = new File("src/test/test_car.jpg");

        //Isteği gönderme
        return given()
                .auth().basic("mina@gmail.com", "mina123")
                .contentType("multipart/form-data") // JSON yerine FORM-DATA
                .multiPart("photo", testImage) // Dosya gönderimi
                .param("brandId", 12)
                .param("model", "3008")
                .param("carSpecification", "1.5 BlueHdi GT, EAT8")
                .param("engineVolume", 1.5f)
                .param("isNew", true)
                .param("price", 2350000.00)
                .param("releaseDatetime", "2024-03-12T09:00:00")
        .when()
                .post("/api/v1/cars")
        .then()
                .log().all()
                .statusCode(200) //response ok doğrulama
                .extract().body().as(Long.class);
    }

    public static void deleteTestCar(Long carId){
        if(carId != null){
            given()
                    .auth().basic("mina@gmail.com", "mina123")
                    .pathParam("id", carId)
            .when()
                    .delete("api/v1/cars/{id}")
            .then()
                    .statusCode(204); // Spring ResponseEntity.noContent() returns 204
        }
    }
}
