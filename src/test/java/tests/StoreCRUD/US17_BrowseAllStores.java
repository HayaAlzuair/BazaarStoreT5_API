package tests.StoreCRUD;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class US17_BrowseAllStores extends BaseUrl {

    @Test
    void GETAllStores() {

        setSpec(UserType.ADMIN);
        Response response = given()
                .spec(spec)
                .when()
                .get("/api/stores");
        response.prettyPrint();
        response
                .then()
                .statusCode(200);

    }
}
