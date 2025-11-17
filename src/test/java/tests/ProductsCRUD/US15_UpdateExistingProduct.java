package tests.ProductsCRUD;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utilities.ObjectMapperUtils.getJsonNode;

public class US15_UpdateExistingProduct extends BaseUrl {

    JsonNode productJson = getJsonNode("UpdateProduct");
    int productId = productJson.get("id").asInt();

    @Test
    void updateProductSuccessfully() {
        setSpec(UserType.STORE_MANAGER);
        JsonNode payload = getJsonNode("UpdateProduct");

        Response response = given(spec)
                .body(payload)
                .put("/api/products/" + productId);

        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    void updateProductNotFound() {
        setSpec(UserType.STORE_MANAGER);

        JsonNode payload = getJsonNode("UpdateProductNotFound");
        Response response = given(spec)
                .body(payload)
                .put("/api/products/99000");

        response.prettyPrint();

        response.then()
                .statusCode(404)
                .body("error", containsString("not found"));
    }

    @Test
    void updateProductValidationError() {
        setSpec(UserType.STORE_MANAGER);

        JsonNode payload = getJsonNode("ProductInvalid");

        Response response = given(spec)
                .body(payload)
                .put("/api/products/" + productId);

        response.prettyPrint();

        response.then()
                .statusCode(422)
                .body("errors", notNullValue());
    }

    @Test
    void updateProductServerError() {
        setSpec(UserType.STORE_MANAGER);

        JsonNode payload = getJsonNode("UpdateProduct");
        Response response = given(spec)
                .body(payload)
                .put("/api/products/9900");

        response.prettyPrint();

        response.then()
                .statusCode(500)
                .body("error", containsString("Product update failed"));
    }
}
