package tests.UserCRUD;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.AuthenticationCRUD.US1_RegisterNewUserAccount;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class US23_ViewUserDetail extends BaseUrl {


    @Test
    void GetSpecificUser() {
        {
        setSpec(UserType.ADMIN);

        String userId = US1_RegisterNewUserAccount.userId;
        System.out.println("User ID: " + userId);

        Response response = given(spec).get("/api/users/" + userId);
        response.prettyPrint();
    }
    }
    @Test
    void TestUserNotFound() {
        setSpec(UserType.ADMIN);

        int invalidUserId = 999999;

        Response response = given(spec).get("/api/users/" + invalidUserId);
        response.prettyPrint();

        response
                .then()
                .statusCode(404)
                .body("message", equalTo("User not found"));
    }
}