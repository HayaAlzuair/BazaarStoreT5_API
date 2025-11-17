package tests.AuthenticationCRUD;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertEquals;
import static utilities.ObjectMapperUtils.getJsonNode;

public class US1_RegisterNewUserAccount extends BaseUrl {

    public static String email;
    public static String userId;

    @Test
    void happyPathOfRegister() {
        setSpec(UserType.CUSTOMER);
        JsonNode payload = getJsonNode("Authentication");
        email = Faker.instance().internet().emailAddress();
        ((ObjectNode) payload).put("email", email);
        Response response = given(spec).body(payload).post("/api/register");

        response.then()
                .statusCode(201)
                .body("user.name", equalTo(payload.get("name").asText()))
                .body("user.email", equalTo(payload.get("email").asText()));

        userId = response.jsonPath().getString("user.id");

    }

    @Test
    void passwordsNotMatch() {
        setSpec(UserType.CUSTOMER);
        JsonNode payload = getJsonNode("PasswordsNotMatch");
        Response response = given(spec).body(payload).post("/api/register");

        response.then().statusCode(422);
        String message =getMessage(response, "password");
        assertEquals("The password field confirmation does not match.", message);
    }

    @Test
    void duplicateEmail() {
        setSpec(UserType.CUSTOMER);
        JsonNode payload = getJsonNode("DuplicateEmail");
        Response response = given(spec).body(payload).post("/api/register");

        response.then().statusCode(422);
        String message =getMessage(response, "email");
        assertEquals("The email has already been taken.", message);
    }

    @Test
    void missingRequiredField() {
        setSpec(UserType.CUSTOMER);
        JsonNode payload = getJsonNode("MissingRequiredField");
        Response response = given(spec).body(payload).post("/api/register");

        response.then().statusCode(422);
        String message =getMessage(response, "name");
        assertEquals("The name field is required.", message);
    }


    @Test
    void invalidEmail() {
        setSpec(UserType.CUSTOMER);
        JsonNode payload = getJsonNode("InvalidEmail");
        Response response = given(spec).body(payload).post("/api/register");

        response.then().statusCode(422);
        String message =getMessage(response, "email");
        assertEquals("The email field must be a valid email address.", message);

    }


    public static String getMessage(Response response, String key) {
        return response.jsonPath().getList(key, String.class).get(0);
    }

}
