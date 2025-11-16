package tests.StoreCrud;

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
        response.then().statusCode(200);

        response.then().body("[0].id", notNullValue());
        response.then().body("[0].name", notNullValue());
        response.then().body("[0].description", notNullValue());
        response.then().body("[0].location", notNullValue());
        response.then().body("[0].admin_id", notNullValue());
        response.then().body("[0].created_at", notNullValue());
        response.then().body("[0].updated_at", notNullValue());

    }
}
