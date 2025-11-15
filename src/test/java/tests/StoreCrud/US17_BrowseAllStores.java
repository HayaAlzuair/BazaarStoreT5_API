package tests.StoreCrud;

import base_urls.BaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;


public class US17_BrowseAllStores extends BaseUrl {
    @Test
    void browsallStores() {


        Response response = given()
                .spec(spec)  // استخدام spec الجاهز مع التوكن
                .when()
                .get("/api/stores");
        response.prettyPrint();
    }
}
