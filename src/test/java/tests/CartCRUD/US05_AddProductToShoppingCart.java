package tests.CartCRUD;

import base_urls.BaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.AssertJUnit.assertEquals;
import static utilities.ObjectMapperUtils.getJsonNode;

public class US05_AddProductToShoppingCart extends BaseUrl {
@Test
    void PostProduct(){

   setSpec(UserType.CUSTOMER);


}

}
