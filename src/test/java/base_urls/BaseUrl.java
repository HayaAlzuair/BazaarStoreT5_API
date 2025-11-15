package base_urls;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;

import static io.restassured.RestAssured.given;

public class BaseUrl {

    protected RequestSpecification spec;
    private static final String baseUrl = "https://bazaarstores.com";

    @BeforeMethod
    public void setSpec() {

        String token = getAdminToken();


        spec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Authorization", "Bearer " + token)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    // ------------------------------
    // Login for default admin
    // ------------------------------
    String getToken() {
        String credentials = """
                {
                    "email" : "admin@sda.com",
                    "password" : "Password.12345"
                }""";

        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(credentials)
                .post(baseUrl + "/api/login")
                .jsonPath()
                .getString("authorisation.token");
    }

    // -----------------------------------------
    // Overloaded token method for any user
    // -----------------------------------------
    String getToken(String email, String password) {
        String credentials = """
                {
                    "email" : "%s",
                    "password" : "%s"
                }""".formatted(email, password);

        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(credentials)
                .post(baseUrl + "/api/login")
                .jsonPath()
                .getString("authorisation.token");
    }

    // -----------------------------------------
    // Predefined tokens
    // -----------------------------------------
    String getAdminToken() {
        return getToken("admin@sda.com", "Password.12345");
    }

    String getStoreManagerToken() {
        return getToken("storemanager@sda.com", "Password.12345");
    }

    String getCustomerToken() {
        return getToken("customer@sda.com", "Password.12345");
    }
}
