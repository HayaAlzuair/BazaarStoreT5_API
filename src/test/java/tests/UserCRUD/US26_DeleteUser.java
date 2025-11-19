package tests.UserCRUD;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.AuthenticationCRUD.US1_RegisterNewUserAccount;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class US26_DeleteUser extends BaseUrl {
    public class US2_DeleteUserAccount {
        @Test
        public void deleteUser() {
            setSpec(UserType.ADMIN);

            String userId = US1_RegisterNewUserAccount.userId;

            Response response = given(spec)
                    .delete("/api/users/" + userId);

            response.prettyPrint();

            response
                    .then()
                    .statusCode(200)
                    .time(lessThan(3000L));
        }
    }

}
