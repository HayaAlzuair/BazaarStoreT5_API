package tests.ProductsCRUD;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utilities.ObjectMapperUtils.getJsonNode;


public class US14_CreateNewProduct extends BaseUrl {

    public static String productId;


    // Create product successfully

    @Test
    void createNewProductSuccessfully() {
        setSpec(UserType.STORE_MANAGER);


        JsonNode payload = getJsonNode("Product");

        Response response = given(spec)
                .body(payload)
                .post("/api/products/create");
        response.prettyPrint();

        // assert numeric price using double conversion to avoid int/double mismatch
        double expectedPrice = payload.get("price").asDouble();
        double actualPrice = response.jsonPath().getDouble("product.price");
        org.testng.Assert.assertEquals(actualPrice, expectedPrice, 0.01, "product.price mismatch");

        response.then()
                .statusCode(201)
                .body("product.name", equalTo(payload.get("name").asText()))
                .body("product.description", equalTo(payload.get("description").asText()))
                .body("product.sku", equalTo(payload.get("sku").asText()))
                .body("product.category_id", equalTo(payload.get("category_id").asInt()));

        productId = response.jsonPath().getString("product.id");
    }

    /*
    Validation Errors (missing/invalid data)
     */
    @Test
    void createProductValidationError() {
        setSpec(UserType.STORE_MANAGER);

        JsonNode payload = getJsonNode("ProductInvalid");

        Response response = given(spec)
                .body(payload)
                .post("/api/products/create");
        response.prettyPrint();

        response.then()
                .statusCode(422) // Validation Error
                .body("message", equalTo("The sku field is required."))
                .body("errors.sku[0]", notNullValue());
    }



    // Product creation failed

    @Test
    void createProductServerError() {
        setSpec(UserType.STORE_MANAGER);

        JsonNode payload = getJsonNode("UpdateProduct");
        Response response = given(spec)
                .body(payload)
                .post("/api/products/create");
        response.prettyPrint();

        response.then()
                .statusCode(500)
                .body("error", notNullValue());
    }

}
