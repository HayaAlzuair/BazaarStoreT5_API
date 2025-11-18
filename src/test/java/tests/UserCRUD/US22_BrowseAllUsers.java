package tests.UserCRUD;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class US22_BrowseAllUsers extends BaseUrl {
    @Test
    public void browseAllUsers() {
        setSpec(UserType.ADMIN);

        Response response = given()
                .spec(spec)
                .when()
                .get("/api/users");
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 200, "Status code should be 200");
    }
}
