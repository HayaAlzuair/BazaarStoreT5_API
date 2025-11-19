package tests.StoreCRUD;

import base_urls.BaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static tests.StoreCRUD.US18_CreateNewStore.storeId;

public class US21_DeleteStore extends BaseUrl {



    @Test
    void DELETEStore(){
        setSpec(UserType.ADMIN);

        Response response = given(spec).
                delete("/api/stores/" +storeId );
        response.prettyPrint();

        response
                .then()
                .statusCode(200)
                .time(lessThan(3000L));
    }
}
