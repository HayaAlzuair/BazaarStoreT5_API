package tests.AuthenticationCRUD;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utilities.ObjectMapperUtils.getJsonNode;

public class US2_LoginAsRegisteredUser extends BaseUrl{


    @Test
    void happyPathOfLogin() {

        setSpec(BaseUrl.UserType.CUSTOMER);
            JsonNode payload = getJsonNode("login");
            Response response = given(spec).body(payload).post("/api/login");

            response.then()
                    .statusCode(200)
                    .body("status", equalTo("success"));
    }

    @Test
    void invalidCredentials() {

        setSpec(BaseUrl.UserType.CUSTOMER);
        JsonNode payload = getJsonNode("invalidCredentials");
        Response response = given(spec).body(payload).post("/api/login");

        response.then()
                .statusCode(401)
                .body("error", equalTo("Invalid credentials"));
    }


}

