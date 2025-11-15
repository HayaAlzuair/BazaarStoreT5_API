package base_urls;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseUrl {

    protected RequestSpecification spec;
    private static final String baseUrl = "https://bazaarstores.com";


    public enum UserType {
        ADMIN,
        STORE_MANAGER,
        CUSTOMER
    }

    protected void setSpec(UserType userType) {
        String token = switch (userType) {
            case ADMIN -> getAdminToken();
            case STORE_MANAGER -> getStoreManagerToken();
            case CUSTOMER -> getCustomerToken();
        };

        spec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Authorization", "Bearer " + token)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    // -----------------------------------------
    // Login و Token Extraction
    // -----------------------------------------
    private String getToken(String email, String password) {
        String credentials = """
                {
                    "email": "%s",
                    "password": "%s"
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
    // Tokens لكل مستخدم
    // -----------------------------------------
    private String getAdminToken() {
        return getToken("admin@sda.com", "Password.12345");
    }

    private String getStoreManagerToken() {
        return getToken("storemanager@sda.com", "Password.12345");
    }

    private String getCustomerToken() {
        return getToken("customer@sda.com", "Password.12345");
    }
}
