package tests.AuthenticationCRUD;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertEquals;
import static utilities.ObjectMapperUtils.getJsonNode;

public class US4_GetCurrentUserProfileInformation extends BaseUrl {

    JsonNode expected = getJsonNode("UserProfile");

    @Test
    void happyPathOfLogout() {

        setSpec(UserType.CUSTOMER);
        Response response = given()
                .spec(spec)
                .when()
                .get("/api/me");

        response.then()
                .statusCode(200)
                .body("name", equalTo(expected.get("name").asText()))
                .body("email",equalTo(expected.get("email").asText()))
                .body("role",equalTo(expected.get("role").asText()));
        String actualId = response.jsonPath().getString("id").replaceAll("[<>]", "");
        String expectedId = expected.get("id").asText();
        assertEquals(expectedId, actualId);

    }

    @Test
    void invalidBearerToken() {

        setSpec(UserType.CUSTOMER);

        overrideToken("invalidToken@$");
        Response response = given()
                .spec(spec)
                .when()
                .get("/api/me");

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
                .get("/api/me");

        response.then()
                .statusCode(401)
                .body("message", equalTo("Unauthenticated."));
    }


}
