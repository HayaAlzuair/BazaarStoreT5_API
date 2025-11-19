package tests.FavoritesCRUD;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;


public class US11_DELETEFromFavorites extends BaseUrl {

    @Test
    void deleteFromFavoritesTest() {
        setSpec(UserType.CUSTOMER);
        Response response = given(spec).delete("/api/favorites/2976");
        response.prettyPrint();

        response
                .then()
                .statusCode(200)
                .time(lessThan(1000L))
                .body("success",containsString("successfully!"));

    }

    @Test
    void deleteFromFavoritesTestNegative() {
        setSpec(UserType.CUSTOMER);
        Response response = given(spec).delete("/api/favorites/2110");
        response.prettyPrint();

        response
                .then()
                .statusCode(404)
                .time(lessThan(1000L))
                .body("error",containsString("not found."));

    }
}
