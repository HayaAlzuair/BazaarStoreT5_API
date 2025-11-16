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
    void NotFounf() {
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
        JsonNode expectedData = getJsonNode("UpdateStore");
        System.out.println(storeId);
        Response response = given(spec).get("/api/stores/" + storeId);
        response.prettyPrint();


        response
                .then()
                .statusCode(200)
                .body(
                        "id", equalTo(Integer.valueOf(storeId)),
                        "name", equalTo(expectedData.get("name").asText()),
                        "description", equalTo(expectedData.get("description").asText()),
                        "admin_id", equalTo(expectedData.get("admin_id").asInt())

                );
    }
}
