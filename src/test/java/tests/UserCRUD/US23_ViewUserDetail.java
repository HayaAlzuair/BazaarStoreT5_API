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
    void UserNotFound() {
        setSpec(UserType.ADMIN);

        int nonExistentId = 8888;

        Response response = given(spec)
                .when()
                .get("/api/users/" + nonExistentId);

        response.prettyPrint();


        response
                .then()
                .statusCode(404)
                .body("error", equalTo("User not found"));


    }
}