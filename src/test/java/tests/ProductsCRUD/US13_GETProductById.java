// java
package tests.ProductsCRUD;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utilities.ObjectMapperUtils.getJsonNode;

public class US13_GETProductById extends BaseUrl {
    JsonNode ProdectById = getJsonNode("ProdectById");

    @Test
    void getProductByIdTest() {
        setSpec(BaseUrl.UserType.CUSTOMER);

        int productId = ProdectById.get("productId").asInt();
        Response response = given(spec)
                .get("/api/products/" + productId);
        response.prettyPrint();

        int status = response.statusCode();
        if (status == 200) {
            response.then()
                    .statusCode(200)
                    .time(lessThan(2000L))
                    .body("id", equalTo(productId))
                    .body("name", notNullValue())
                    .body("price", notNullValue())
                    .body("sku", notNullValue())
                    .body("category_id", notNullValue());
            return;
        }

        if (status == 404) {
            response.then()
                    .statusCode(404)
                    .body("error", equalTo("Product not found"));
            return;
        }

        Assert.fail("Unexpected status code: " + status);
    }

    @Test
    void getProductByIdNegativeTest() {
        setSpec(UserType.STORE_MANAGER);

        int invalidProductId = ProdectById.get("invalidProductId").asInt();
        Response response2 = given(spec)
                .get("/api/products/" + invalidProductId);
        response2.prettyPrint();

        response2
                .then()
                .statusCode(404)
                .body("error", equalTo("Product not found"));
    }
}








