package tests.UserCRUD;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static tests.AuthenticationCRUD.US1_RegisterNewUserAccount.userId;
import static utilities.ObjectMapperUtils.getJsonNode;
public class US25_UpdateExistingUser extends BaseUrl {
    @Test
    void UpdateUser() {
        setSpec(UserType.ADMIN);
        JsonNode payload = getJsonNode("UpdateUser");
        Response response = given(spec)
                .body(payload)
                .put("/api/users/" + userId);
        response.prettyPrint();
        response.then().statusCode(200);
    }
    @Test
    void updateNonExistingUser() {
        setSpec(UserType.ADMIN);
        JsonNode payload = getJsonNode("UpdateUser");

        Response response = given(spec)
                .body(payload)
                .put("/api/users/999999");

        response.prettyPrint();
        response.then().statusCode(404);
    }
    @Test
    void updateUserWithInvalidData() {
        setSpec(UserType.ADMIN);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode invalidPayload = objectMapper.createObjectNode();
        invalidPayload.put("name", "");
        Response response = given(spec)
                .body(invalidPayload)
                .put("/api/users/4072");

        response.prettyPrint();
        response.then().statusCode(400);
    }
}
