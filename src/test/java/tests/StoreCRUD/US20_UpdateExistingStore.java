package tests.StoreCRUD;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static tests.StoreCRUD.US18_CreateNewStore.storeId;
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
            int nonExistentId = 999999;
            String updateBody = "{ \"name\": \"New Name\", \"description\": \"New Desc\", \"location\": \"New Location\" }";
            Response response = given(spec)
                    .contentType("application/json")
                    .body(updateBody)
                    .when()
                    .put("/api/stores/" + nonExistentId);
            response.prettyPrint();
            response
                    .then()
                    .statusCode(404)
                    .body("error", equalTo("Store not found"));
        }


    @Test
    void UpdateError() {
        setSpec(UserType.ADMIN);
        String invalidId = "abc";

        String updateBody = "{ \"name\": \"New Name\", \"description\": \"New Desc\", \"location\": \"New Location\" }";

        Response response = given(spec)
                .contentType("application/json")
                .body(updateBody)
                .when()
                .put("/api/stores/" + invalidId);

        response.prettyPrint();
        response.then()
                .statusCode(422)
                .body("message", equalTo("The id field must be a number"));
    }

    }

