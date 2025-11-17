package tests.ProductsCRUD;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;





public class US12_BrowseAllProducts extends BaseUrl{

    @Test
    void getAllProductsTest() {

        setSpec(UserType.CUSTOMER);

        Response response = given(spec).get("/api/products");
        response.prettyPrint();

        response
                .then()
                .statusCode(200)
                .time(lessThan(2000L))
                .body("id", notNullValue())
                .body("size()", greaterThanOrEqualTo(0));
    }
}








