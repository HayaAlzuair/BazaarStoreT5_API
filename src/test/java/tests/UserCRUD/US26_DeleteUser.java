package tests.UserCRUD;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class US26_DeleteUser extends BaseUrl {
    @Test
    void DELETEUser(){
        setSpec(UserType.ADMIN);

        int userId = 4072;

        Response response = given(spec)
                .delete("/api/users/" + userId);

        response.prettyPrint();

        response
                .then()
                .statusCode(200)
                .time(lessThan(3000L));
    }

}
