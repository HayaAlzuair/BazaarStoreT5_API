package tests.CartCRUD;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class US05_AddProductToShoppingCart extends BaseUrl {
public static String CartId;
    @Test
    void happyPath_AddProductToCart() {
        setSpec(UserType.CUSTOMER);

        String payload = """
                {
                  "product_id": 1,
                  "quantity": 2
                }
                """;

        Response response = given()
                .spec(spec)
                .body(payload)
                .post("/api/cart/add");

        response.then()
                .statusCode(200)
                .body("message", equalTo("Product added to cart successfully"));
        CartId = response.jsonPath().getString("cart.id");
    }

    @Test
    void negativePath_AddProductToCart() {
        setSpec(UserType.CUSTOMER);

        String payload = """
                {
                  "product_id": ,
                  "quantity": 2
                }
                """;

        Response response = given()
                .spec(spec)
                .body(payload)
                .post("/api/cart/add");

        response.then()
                .statusCode(422)
                .body("message", equalTo("Product added to cart successfully"));
    }

}