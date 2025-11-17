package tests.ProductsCRUD;


import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static utilities.ObjectMapperUtils.getJsonNode;

public class US16_DeleteProduct extends BaseUrl  {
    JsonNode ProdectById = getJsonNode("ProdectById");
    @Test
    void deleteProductSuccessfully() {
        setSpec(UserType.STORE_MANAGER);


        int productId = ProdectById.get("productId").asInt();
        Response response = given(spec)
                .delete("/api/products/" + productId);
        response.prettyPrint();

        response
                .then()
                .statusCode(200)
                .body("success", containsString("Product deleted successfully"));
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
