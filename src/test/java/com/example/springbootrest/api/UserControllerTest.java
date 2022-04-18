package com.example.springbootrest.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class UserControllerTest {

    private final String URL = "http://localhost:8080/api/";

    @Test
    public void create() {
        // create user
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("accessLevel","Client")
                .formParam("login","login5")
                .formParam("password","password5")
                .when().post(URL + "user/add")
                .then()
                .assertThat()
                .statusCode(200)
                .body("active", equalTo(true))
                .body("login", equalTo("login5"))
                .body("password", equalTo("password5"));
    }

    @Test
    public void read() {
        // create user
        String uuid = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("accessLevel","Administrator")
                .formParam("login","login6")
                .formParam("password","password6")
                .when().post(URL + "user/add")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("uuid");

        // read user
        RestAssured.when().get(URL + "user/{uuid}", uuid)
                .then()
                .assertThat()
                .statusCode(200)
                .body("active", equalTo(true))
                .body("login", equalTo("login6"))
                .body("password", equalTo("password6"))
                .body("uuid", equalTo(uuid));
    }

    @Test
    public void update() {
        // create user
        String uuid = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("accessLevel","ResourceAdministrator")
                .formParam("login","login7")
                .formParam("password","password7")
                .when().post(URL + "user/add")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("uuid");

        // update user
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("id",uuid)
                .formParam("login","updatedUser3")
                .formParam("password","password7")
                .when().post(URL + "user/update")
                .then()
                .assertThat()
                .statusCode(200)
                .body("active", equalTo(true))
                .body("login", equalTo("updatedUser3"))
                .body("password", equalTo("password7"))
                .body("uuid", equalTo(uuid));
    }

    @Test
    public void updateWithWrongLogin() {
        // create user
        String uuid = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("accessLevel","ResourceAdministrator")
                .formParam("login","login8")
                .formParam("password","password8")
                .when().post(URL + "user/add")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("uuid");

        // update user
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("id",uuid)
                .formParam("login","")
                .when().post(URL + "user/update")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void validate() {
        // invalid accessLevel
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("accessLevel","invalidAccessLevel")
                .formParam("login","login9")
                .formParam("password","password9")
                .when().post(URL + "user/add")
                .then()
                .assertThat()
                .statusCode(400);

        // valid accessLevel
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("accessLevel","Administrator")
                .formParam("login","login10")
                .formParam("password","password10")
                .when().post(URL + "user/add")
                .then()
                .assertThat()
                .statusCode(200);

        // invalid uuid
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("id","invalidId")
                .formParam("login","login11")
                .formParam("password","password11")
                .when().post(URL + "user/update")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void uniqueLogin() {
        // create user
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("accessLevel","ResourceAdministrator")
                .formParam("login","login12")
                .formParam("password","password12")
                .when().post(URL + "user/add")
                .then()
                .assertThat()
                .statusCode(200);
        // create user with same login
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("accessLevel","ResourceAdministrator")
                .formParam("login","login12")
                .formParam("password","testPassword5")
                .when().post(URL + "user/add")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void emptyLogin() {
        // create user with empty login
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("accessLevel","ResourceAdministrator")
                .formParam("login","")
                .formParam("password","testPassword5")
                .when().post(URL + "user/add")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void activate() {
        // create user
        String uuid = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("accessLevel","ResourceAdministrator")
                .formParam("login","login13")
                .formParam("password","password13")
                .when().post(URL + "user/add")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("uuid");

        // deactivate user
        RestAssured.when().post(URL + "user/deactivate/{uuid}", uuid)
                .then()
                .assertThat()
                .statusCode(200)
                .body("active", equalTo(false))
                .body("login", equalTo("login13"))
                .body("password", equalTo("password13"))
                .body("uuid", equalTo(uuid));

        //assert active = false
        RestAssured.when().get(URL + "user/{uuid}", uuid)
                .then()
                .assertThat()
                .statusCode(200)
                .body("active", equalTo(false))
                .body("login", equalTo("login13"))
                .body("password", equalTo("password13"))
                .body("uuid", equalTo(uuid));

        // activate user
        RestAssured.when().post(URL + "user/activate/{uuid}", uuid)
                .then()
                .assertThat()
                .statusCode(200)
                .body("active", equalTo(true))
                .body("login", equalTo("login13"))
                .body("password", equalTo("password13"))
                .body("uuid", equalTo(uuid));

        //assert active = true
        RestAssured.when().get(URL + "user/{uuid}", uuid)
                .then()
                .assertThat()
                .statusCode(200)
                .body("active", equalTo(true))
                .body("login", equalTo("login13"))
                .body("password", equalTo("password13"))
                .body("uuid", equalTo(uuid));
    }
}
