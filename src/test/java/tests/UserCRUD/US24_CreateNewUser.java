package tests.UserCRUD;

import base_urls.BaseUrl;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertNotNull;
import static tests.AuthenticationCRUD.US1_RegisterNewUserAccount.userId;

public class US24_CreateNewUser extends BaseUrl {

public static String email;

    @Test
    void adminCanCreateUser() {
        setSpec(UserType.ADMIN);

        String payload = """
            {
              "name": "reem",
          "email" : "%s"   ,
              "password": "Test1234",
              "password_confirmation": "Test1234",
              "role" : "customer"
            }
            """;
        email = Faker.instance().internet().emailAddress();
        payload = String.format(payload,email);


        Response response = given(spec)
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/api/users/create");

        response.prettyPrint();

        response.then()
                .statusCode(201)
                .body("user.name", equalTo("reem"))
                .body("user.email", equalTo("reeeml21@sda.com"));

    }
    @Test
    void invaliddata() {
        setSpec(UserType.ADMIN);

        String payload = """
            {
              "name": "",
          "email" : "%s"   ,
              "password": "Test1234",
              "password_confirmation": "Test1234",
              "role" : "customer"
            }
            """;
        email = Faker.instance().internet().emailAddress();
        payload = String.format(payload,email);


        Response response = given(spec)
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/api/users/create");

        response.prettyPrint();

        response.then()
                .statusCode(201)
                .body("user.name", equalTo("reem"))
                .body("user.email", equalTo("reeeml21@sda.com"));

        assertNotNull(userId);
    }
}
