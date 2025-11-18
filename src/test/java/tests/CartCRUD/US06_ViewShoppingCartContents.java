package tests.CartCRUD;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertEquals;

public class US06_ViewShoppingCartContents extends BaseUrl {

    @Test
    void viewCartContents_HappyPath() {
        setSpec(UserType.CUSTOMER);

        Response response = given()
                .spec(spec)
                .when()
                .get("/api/cart");

        response.then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("cart", notNullValue())
                .body("cart", is(not(empty())))
                .body("cart[0].product_id", notNullValue())
                .body("cart[0].quantity", greaterThan(0))
                .body("cart[0].price", greaterThan(0.0f));
    }




}