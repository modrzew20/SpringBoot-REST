package com.example.springbootrest.api;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.hamcrest.core.Is.is;

@SpringBootTest
public class ReservationControllerTest {

    private final String URL = "http://localhost:8080/api/";

    public static Response doGetRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        return
                given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                        when().get(endpoint).
                        then().contentType(ContentType.JSON).extract().response();
    }


    @Test
    public void addReserv() {

        String clientsUUID = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("accessLevel","ResourceAdministrator")
                .formParam("login","login1")
                .formParam("password","password1")
                .when().post(URL + "user/add")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("uuid");

        String laneUUID = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("type","vip")
                .when().post(URL + "lane/add")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("uuid");


        Response response = doGetRequest(URL + "reservation");
        List<String> jsonResponse = response.jsonPath().getList("$");
        int size = jsonResponse.size();

        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("clientsUUID",clientsUUID)
                .formParam("laneUUID",laneUUID)
                .formParam("start","2021-12-13T12:10:31")
                .formParam("end","2021-12-13T12:30:31")
                .when().post(URL + "reservation/add")
                .then()
                .assertThat()
                .statusCode(200);

        RestAssured.given().contentType("application/x-www-form-urlencoded; charset=utf-8").get(URL + "reservation").then().assertThat()
                .statusCode(200).body("size()", is(size+1));


    }

    @Test
    public void deleteReserv() {
        ContentType contentType = ContentType.URLENC;

        String clientsUUID = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("accessLevel","ResourceAdministrator")
                .formParam("login","login2")
                .formParam("password","password2")
                .when().post(URL + "user/add")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("uuid");

        String laneUUID = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("type","vip")
                .when().post(URL + "lane/add")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("uuid");

        String reservUUID = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("clientsUUID",clientsUUID)
                .formParam("laneUUID",laneUUID)
                .formParam("start","2021-12-13T12:10:31")
                .formParam("end","2021-12-13T12:30:31")
                .when().post(URL + "reservation/add")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("uuid");

        Response response = doGetRequest(URL + "reservation");
        List<String> jsonResponse = response.jsonPath().getList("$");
        int size = jsonResponse.size();


        RestAssured.given().contentType(contentType).delete(URL + "reservation/delete/" + reservUUID).then().assertThat()
                .statusCode(200);

        RestAssured.given().contentType("application/x-www-form-urlencoded; charset=utf-8").get(URL + "reservation").then().assertThat()
                .statusCode(200).body("size()", is(size-1));


    }


    @Test
    public void unsuccessfulldeleteReserv() {
        ContentType contentType = ContentType.URLENC;

        String clientsUUID = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("accessLevel","ResourceAdministrator")
                .formParam("login","login3")
                .formParam("password","password3")
                .when().post(URL + "user/add")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("uuid");

        String laneUUID = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("type","vip")
                .when().post(URL + "lane/add")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("uuid");

        String reservUUID = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("clientsUUID",clientsUUID)
                .formParam("laneUUID",laneUUID)
                .formParam("start","2021-12-13T12:10:31")
                .formParam("end","2025-12-13T12:30:31")
                .when().post(URL + "reservation/add")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("uuid");

        Response response = doGetRequest(URL + "reservation");
        List<String> jsonResponse = response.jsonPath().getList("$");
        int size = jsonResponse.size();


        RestAssured.given().contentType(contentType).delete(URL + "reservation/delete/" + reservUUID).then().assertThat()
                .statusCode(400);

        RestAssured.given().contentType("application/x-www-form-urlencoded; charset=utf-8").get(URL + "reservation").then().assertThat()
                .statusCode(200).body("size()", is(size));


    }

    @Test
    public void addReservationTest() {

        // create user
        String clientsUUID = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("accessLevel","ResourceAdministrator")
                .formParam("login","login4")
                .formParam("password","password4")
                .when().post(URL + "user/add")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("uuid");

        String laneUUID = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("type","vip")
                .when().post(URL + "lane/add")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("uuid");

        // dodanie rezerwacji poprawne
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("clientsUUID",clientsUUID)
                .formParam("laneUUID",laneUUID)
                .formParam("start","2021-12-13T12:10:31")
                .formParam("end","2021-12-13T12:30:31")
                .when().post(URL + "reservation/add")
                .then()
                .assertThat()
                .statusCode(200);

        // w tym samym czasie
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("clientsUUID",clientsUUID)
                .formParam("laneUUID",laneUUID)
                .formParam("start","2021-12-13T12:10:31")
                .formParam("end","2021-12-13T12:30:31")
                .when().post(URL + "reservation/add")
                .then()
                .assertThat()
                .statusCode(400);

        // gdy naklada sie na poprzednia rezerwacje
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("clientsUUID",clientsUUID)
                .formParam("laneUUID",laneUUID)
                .formParam("start","2021-12-13T12:00:31")
                .formParam("end","2021-12-13T13:00:31")
                .when().post(URL + "reservation/add")
                .then()
                .assertThat()
                .statusCode(400);

        // gdy zawiera sie w innej rezerwacji
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("clientsUUID",clientsUUID)
                .formParam("laneUUID",laneUUID)
                .formParam("start","2021-12-13T12:15:31")
                .formParam("end","2021-12-13T12:20:31")
                .when().post(URL + "reservation/add")
                .then()
                .assertThat()
                .statusCode(400);


        //gdy konczy sie w trakcie innej rezrewacji
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("clientsUUID",clientsUUID)
                .formParam("laneUUID",laneUUID)
                .formParam("start","2021-12-13T12:05:31")
                .formParam("end","2021-12-13T12:25:31")
                .when().post(URL + "reservation/add")
                .then()
                .assertThat()
                .statusCode(400);

        //gdy zaczyna sie w trakcie innej rezerwacji
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("clientsUUID",clientsUUID)
                .formParam("laneUUID",laneUUID)
                .formParam("start","2021-12-13T12:15:31")
                .formParam("end","2021-12-13T12:40:31")
                .when().post(URL + "reservation/add")
                .then()
                .assertThat()
                .statusCode(400);
    }

}