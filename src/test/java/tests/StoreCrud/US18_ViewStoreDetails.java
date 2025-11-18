package tests.StoreCrud;
import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response; import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static javax.swing.UIManager.get;
import static org.hamcrest.Matchers.equalTo;
import static tests.StoreCrud.US19_CreateNewStore.storeId;
import static utilities.ObjectMapperUtils.getJsonNode;
public class US18_ViewStoreDetails extends BaseUrl {
    @Test
    void GetSpecificStore() {
        setSpec(UserType.ADMIN);
        JsonNode expectedData = getJsonNode("Store");
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
                        "location", equalTo(expectedData.get("location").asText()),
                        "admin_id", equalTo(expectedData.get("admin_id").asInt())

                );


    }

    @Test
    void TestNotFound() {
        setSpec(UserType.ADMIN);
        JsonNode expectedData = getJsonNode("Store");
        Response response = given(spec).get("/api/stores/999999");
        response.prettyPrint();

        response
                .then()
                .statusCode(404)
                .body(
                        "id", equalTo(Integer.valueOf(999999)),
                        "name", equalTo(expectedData.get("name").asText()),
                        "description", equalTo(expectedData.get("description").asText()),
                        "location", equalTo(expectedData.get("location").asText()),
                        "admin_id", equalTo(expectedData.get("admin_id").asInt())

                );


    }
}

