package base_urls;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseUrl {
    protected RequestSpecification spec;
    private static final String baseUrl = "https://bazaarstores.com";
    private static final Map<String, String> tokenMap = new HashMap<>();

    // Default role (can be changed in test class before @BeforeMethod runs)
    protected String role = "admin";

    @BeforeMethod
    public void setSpec() {
        String token = switch (role.toLowerCase()) {
            case "admin" -> getAdminToken();
            case "storemanager" -> getStoreManagerToken();
            case "customer" -> getCustomerToken();
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };

        spec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Cookie", "token=" + token)
                .setContentType(ContentType.JSON)
                .build();
    }

    public String getAdminToken() {
        return getToken("admin@sda.com", "Password.12345");
    }

    public String getStoreManagerToken() {
        return getToken("storemanager@sda.com", "Password.12345");
    }

    public String getCustomerToken() {
        return getToken("customer@sda.com", "Password.12345");
    }

    private String getToken(String email, String password) {
        if (tokenMap.containsKey(email)) {
            return tokenMap.get(email);
        }

        String credentials = String.format("""
                {
                    "email": "%s",
                    "password": "%s"
                }
                """, email, password);

        Response response = given()
                .body(credentials)
                .contentType(ContentType.JSON)
                .post(baseUrl + "/api/login")
                .then()
                .log().all()
                .extract()
                .response();

        String token = response.jsonPath().getString("token");

        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Failed to retrieve token for user: " + email);
        }

        tokenMap.put(email, token);
        return token;
    }
}
