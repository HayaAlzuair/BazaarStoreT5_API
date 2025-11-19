package tests.ProductsCRUD;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utilities.ProductIdHandler;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utilities.ObjectMapperUtils.getJsonNode;



public class US14_CreateNewProduct extends BaseUrl {

    public static String productId;


    // Create product successfully
      @Test
    void createNewProductSuccessfully() throws IOException {
        setSpec(UserType.STORE_MANAGER);

        JsonNode payload = getJsonNode("Product");

        Response response = given(spec)
                .body(payload)
                .post("/api/products/create");
        response.prettyPrint();


        response.then()
                .statusCode(201);

        String productId = response.jsonPath().getString("product.id");


        ProductIdHandler.saveProductId(productId);

        System.out.println("Stored Product ID = " + productId);


    }



    /*
    Validation Errors (missing/invalid data)
     */
    @Test
    void createProductValidationError() {
        setSpec(UserType.STORE_MANAGER);

        JsonNode payload = getJsonNode("ProductInvalid");

        Response response = given(spec)
                .body(payload)
                .post("/api/products/create");
        response.prettyPrint();

        response.then()
                .statusCode(422) // Validation Error
                .body("message", equalTo("The sku field is required."))
                .body("errors.sku[0]", notNullValue());
    }



    // Product creation failed

    @Test
    void createProductServerError() {
        setSpec(UserType.STORE_MANAGER);

        JsonNode payload = getJsonNode("UpdateProduct");
        Response response = given(spec)
                .body(payload)
                .post("/api/products/create");
        response.prettyPrint();

        response.then()
                .statusCode(500)
                .body("error", notNullValue());
    }

}
