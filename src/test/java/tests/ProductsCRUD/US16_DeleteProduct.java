package tests.ProductsCRUD;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utilities.ProductIdHandler;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static utilities.ObjectMapperUtils.getJsonNode;

public class US16_DeleteProduct extends BaseUrl  {

    JsonNode ProdectById = getJsonNode("ProdectById");
    @Test
    void deleteProductSuccessfully() throws IOException {
        setSpec(UserType.STORE_MANAGER);

        String productId = ProductIdHandler.getProductId();

        if (productId == null || productId.isEmpty()) {
            throw new RuntimeException("No product ID found to delete!");
        }

        Response response = given(spec)
                .delete("/api/products/" + productId);

        response.then()
                .statusCode(200);

        System.out.println("Deleted Product ID = " + productId);
    }


    @Test
    void deleteProductServerError() {
        setSpec(UserType.STORE_MANAGER);

        int invalidProductId = ProdectById.get("invalidProductId").asInt();
        Response response1 = given(spec)
                .delete("/api/products/" + invalidProductId);
        response1.prettyPrint();

        response1
                .then()
                .statusCode(500)
                .body("error", containsString("failed"));
    }

}
