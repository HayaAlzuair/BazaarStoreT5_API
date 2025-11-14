package tests.StoreCrud;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;


public class US17_BrowseAllStores extends BaseUrl {

@Test
 void veiwallstores(){
    role = "admin";

    // build the spec
    setSpec();

    Response response = given()
            .spec(spec)
            .when()
            .get("/api/stores")
            .then()
            .statusCode(200)
            .extract()
            .response();

    System.out.println("ALL STORES:");
    System.out.println(response.asPrettyString());

}
}
