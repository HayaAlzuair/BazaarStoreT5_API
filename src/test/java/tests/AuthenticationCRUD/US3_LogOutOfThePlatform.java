package tests.AuthenticationCRUD;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class US3_LogOutOfThePlatform extends BaseUrl {

    @Test
    void happyPathOfLogout() {

        setSpec(UserType.CUSTOMER);
        Response response = given()
                .spec(spec)
                .when()
                .post("/api/logout");

        response.then()
                    .statusCode(200)
                    .body("message", equalTo("Successfully logged out"));
    }

    @Test
    void invalidBearerToken() {

        setSpec(UserType.CUSTOMER);

        overrideToken("invalidToken@$");
        Response response = given()
                .spec(spec)
                .when()
                .post("/api/logout");

        response.then()
                .statusCode(401)
                .body("message", equalTo("Unauthenticated."));
    }

    @Test
    void emptyBearerToken() {

        setSpec(UserType.CUSTOMER);

        overrideToken("");
        Response response = given()
                .spec(spec)
                .when()
                .post("/api/logout");

        response.then()
                .statusCode(401)
                .body("message", equalTo("Unauthenticated."));
    }

}
