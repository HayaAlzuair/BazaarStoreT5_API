package tests.StoreCrud;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static tests.StoreCrud.US19_CreateNewStore.storeId;
import static utilities.ObjectMapperUtils.getJsonNode;

public class US20_UpdateExistingStore extends BaseUrl {



    @Test
    void PUTStore(){
        setSpec(UserType.ADMIN);
        JsonNode payload = getJsonNode("UpdateStore");
        Response response = given(spec)
                .body(payload)
                .put("/api/stores/" + storeId);

        response.prettyPrint();
        response.then().statusCode(200);


    }
}
