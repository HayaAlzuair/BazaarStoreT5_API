package tests.StoreCrud;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertNotNull;
import static utilities.ObjectMapperUtils.getJsonNode;

public class US19_CreateNewStore extends BaseUrl {

    public static String storeId;

    @Test
    void CreateNewStore() {
        setSpec(UserType.ADMIN);
        JsonNode payload = getJsonNode("Store");
        Response response = given(spec).body(payload).post("/api/stores/create");
        response.prettyPrint();
        response.then()
          .statusCode(201)

                .body("product.name", equalTo(payload.get("name").asText()))
                .body("product.description", equalTo(payload.get("description").asText()))
                .body("product.location", equalTo(payload.get("location").asText()))
                .body("product.admin_id", equalTo(payload.get("admin_id").asInt()));



        storeId = response.jsonPath().getString("product.id");



    }
}