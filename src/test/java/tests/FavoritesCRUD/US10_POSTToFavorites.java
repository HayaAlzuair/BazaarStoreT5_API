package tests.FavoritesCRUD;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utilities.ObjectMapperUtils.getJsonNode;

public class US10_POSTToFavorites extends BaseUrl {


    @Test
    void addFavoritesTest() {
        setSpec(UserType.CUSTOMER);
        JsonNode payload = getJsonNode("Favorites");
        Response response = given(spec).body(payload).post("/api/favorites/create");
        response.prettyPrint();

        response
                .then()
                .statusCode(200)
                .time(lessThan(1000L))
                .body("success", containsString("successfully!"));

    }

    @Test
    void addFavoritesTestNegative() {
        setSpec(UserType.CUSTOMER);
        JsonNode payload = getJsonNode("Favorites");
        Response response = given(spec).body(payload).post("/api/favorites/create");
        response.prettyPrint();

        response
                .then()
                .statusCode(400)
                .time(lessThan(1000L))
                .body("error", containsString("already"));

    }
}
