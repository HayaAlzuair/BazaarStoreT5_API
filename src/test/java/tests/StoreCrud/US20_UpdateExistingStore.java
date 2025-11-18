package tests.StoreCrud;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static tests.StoreCrud.US19_CreateNewStore.storeId;
import static utilities.ObjectMapperUtils.getJsonNode;

public class US20_UpdateExistingStore extends BaseUrl {


    @Test
    void PUTStore() {
        setSpec(UserType.ADMIN);
        JsonNode payload = getJsonNode("UpdateStore");
        Response response = given(spec)
                .body(payload)
                .put("/api/stores/" + storeId);

        response.prettyPrint();
        response.then().statusCode(200);


    }
    @Test
    void NotFound() {
        setSpec(UserType.ADMIN);
        JsonNode payload = getJsonNode("UpdateStore");
        Response response = given(spec)
                .body(payload)
                .put("/api/stores/00000002222");

        response.prettyPrint();
        response.then().statusCode(404);

    }

    @Test
    void UpdateError() {
        setSpec(UserType.ADMIN);

            String invalidId = "abc";

            Response response = given(spec)
                    .get("/api/stores/" + invalidId);

            response.prettyPrint();

            response.then()
                    .statusCode(422)
                    .body("message", equalTo("The id field must be a number"));
        }
    }

