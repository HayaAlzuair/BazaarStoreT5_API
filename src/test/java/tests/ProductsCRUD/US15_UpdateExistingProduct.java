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


public class US15_UpdateExistingProduct extends BaseUrl {



    @Test
    void updateExistingProductSuccessfully() throws IOException {
        setSpec(UserType.STORE_MANAGER);


        String productId = ProductIdHandler.getProductId();

        if (productId == null || productId.isEmpty()) {
            throw new RuntimeException("No product ID found! Create a product first.");
        }


        JsonNode payload = getJsonNode("UpdateProduct");


        Response response = given(spec)
                .body(payload)
                .put("/api/products/" + productId);

        response.prettyPrint();

        response.then()
                .statusCode(200);
        System.out.println("Updated Product ID = " + productId);

        deleteProductById(productId);


    }

    //bug
    @Test
    void updateProductNotFound() {
        setSpec(UserType.STORE_MANAGER);
        JsonNode payload = getJsonNode("UpdateProductNotFound");
        Response response = given(spec)
                .body(payload)
                .put("/api/products/99000");

        response.prettyPrint();
        response.then()
                .statusCode(404)
                .body("error", containsString("not found"));
    }

    @Test
    void updateProductValidationError() throws IOException {
        // جلب الـ productId من ملف الـ JSON اللي يخزن آخر منتج تم إنشاؤه
        String productIdStr = ProductIdHandler.getProductId();
        if (productIdStr == null || productIdStr.isEmpty()) {
            throw new RuntimeException("No product ID found! Create a product first.");
        }
        int productId = Integer.parseInt(productIdStr);

        setSpec(UserType.STORE_MANAGER);

        // جلب بيانات التحقق من الخطأ
        JsonNode payload = getJsonNode("ProductInvalid");

        // إرسال طلب التحديث
        Response response = given(spec)
                .body(payload)
                .put("/api/products/" + productId);

        response.prettyPrint();

        // التحقق من النتيجة
        response.then()
                .statusCode(422)
                .body("errors", notNullValue());
    }


    @Test
    void updateProductServerError() {
        setSpec(UserType.STORE_MANAGER);
        JsonNode payload = getJsonNode("UpdateProduct");
        Response response = given(spec)
                .body(payload)
                .put("/api/products/9900");

        response.prettyPrint();
        response.then()
                .statusCode(500)
                .body("error", containsString("Product update failed"));
    }


    // دالة حذف داخل نفس الكلاس
    public void deleteProductById(String productId) {
        if (productId == null || productId.isEmpty()) {
            System.out.println("No product ID provided to delete!");
            return;
        }

        given(spec)
                .delete("/api/products/" + productId)
                .then().statusCode(200);

        System.out.println("Deleted Product ID = " + productId);
    }
}
