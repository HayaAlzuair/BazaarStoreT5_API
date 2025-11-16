package tests.FavoritesCRUD;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class US09_GETAllFavorites extends BaseUrl {
    @Test
    void GetAllFavoritesTest() {
        setSpec(UserType.CUSTOMER);

        Response response = given(spec)
                .get("/api/favorites");
        response.prettyPrint();

        response
                .then()
                .statusCode(200)
                .time(lessThan(1000L))
                .body("product_id", notNullValue())
                .body("size()", greaterThanOrEqualTo(0));
    }
}
