package delete_tests;

import org.junit.Test;
import static io.restassured.RestAssured.*;
import base_tests.BaseTests;
import json_user.JSONUser;


public class DeleteTests extends BaseTests {


    /**
     * tests whether HTTP status code is valid for DELETE-method
     */
    @Test
    public void testStatusCode() {
        JSONUser user = new JSONUser("anna", "vet");

        String userId = given().body(user.toJSONString()).
                when().post("/api/users") .
                    then().assertThat().statusCode(201).and().
                    extract().path("id");

        given().pathParam("id", "userId").
                when().delete("/api/users/{id}")
                .then().statusCode(204);
    }

    /**
     * tests whether HTTP status code is 404 after deleting just created user
     * after attempting to get the one
     */
    @Test
    public void testIfUserIsDeleted(){
        JSONUser user = new JSONUser("anna", "vet");

        String userId = given().body(user.toJSONString()).
                when().post("/api/users").
                    then().assertThat().statusCode(201).and().
                    extract().path("id");

        given().pathParam("id", "userId").
                when().delete("https://reqres.in/api/users/{id}");

        given().
                pathParam("id", "userId").
                when().get("/api/users/{id}").
                then().statusCode(404);
    }
}
