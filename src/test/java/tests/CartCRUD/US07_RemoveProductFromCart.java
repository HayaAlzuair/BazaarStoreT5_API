package tests.CartCRUD;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static tests.CartCRUD.US05_AddProductToShoppingCart.CartId;

public class US07_RemoveProductFromCart extends BaseUrl {

    @Test
    void happyPath_RemoveProductFromCart() {
        setSpec(UserType.CUSTOMER);

        String payload = """
                {
                  "product_id": 1
                }
                """;

        Response response = given()
                .spec(spec)
                .body(payload)
                .delete("/api/cart/remove" + CartId);

        response.then()
                .statusCode(200)
                .body("message", equalTo("Product removed from cart successfully"));
    }

    @Test
    void removalFails_InvalidProductId() {
        setSpec(UserType.CUSTOMER);

        String payload = """
                {
                  "product_id": 9999
                }
                """;

        Response response = given()
                .spec(spec)
                .body(payload)
                .delete("/api/cart/remove");

        response.then()
                .statusCode(400)
                .body("message", equalTo("Failed to remove product from cart"));
    }

}