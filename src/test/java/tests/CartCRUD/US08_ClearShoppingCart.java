package tests.CartCRUD;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class US08_ClearShoppingCart extends BaseUrl {

    @Test
    void happyPath_ClearCart() {
        setSpec(UserType.CUSTOMER);

        Response response = given()
                .spec(spec)
                .when()
                .post("/api/cart/clear");

        response.then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("message", equalTo("Cart cleared successfully"));
    }

    @Test
    void verifyCartIsEmptyAfterClear() {
        setSpec(UserType.CUSTOMER);

        Response response = given()
                .spec(spec)
                .when()
                .get("/api/cart");

        response.then()
                .statusCode(200)
                .body("cart", empty());
    }

}