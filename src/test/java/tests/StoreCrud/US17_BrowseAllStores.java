package tests.StoreCrud;

import base_urls.BaseUrl;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;


public class US17_BrowseAllStores extends BaseUrl {
    @Test
    void browsallStores() {
        setSpec(UserType.ADMIN);

        // نفذ الطلب
        Response response = given()
                .spec(spec)
                .when()
                .get("/api/stores");
        response.prettyPrint();


    }
}
