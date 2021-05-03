package put_tests;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import base_tests.BaseTests;
import json_user.JSONUser;


public class PutTests extends BaseTests {


    /**
     * Tests and verifies the response payload of PUT-method;
     * 7th test-case
     */
    @Test
    public void testResponsePayload() {
        JSONUser user = new JSONUser("miku", "teacher");

        given().contentType(ContentType.JSON).body(user.toJSONString()).
            when().put("/api/users/2")
                .then().
                body("$", hasKey("name")).and().
                body("$", hasKey("job")).and().
                body("$", hasKey("updatedAt")).and().
                body("name", equalTo(user.getUserName())).and().
                body("job", equalTo(user.getUserJob())).and().
                body("updatedAt", notNullValue()).and().
                body("updatedAt", not(equals("")));
    }


    /**
     * Tests whether HTTP status code is valid for PUT-method;
     * 3rd test-case
     */
    @Test
    public void testStatusCode() {
        JSONUser user = new JSONUser("miku", "teacher");

        given().contentType(ContentType.JSON).body(user.toJSONString())
                .when().put("/api/users/2")
                    .then().assertThat().statusCode(200);
    }


    /**
     * Tests and verifies the response header of PUT-method;
     * 11th test-case
     */
    @Test
    public void testResponseHeader() {
        JSONUser user = new JSONUser("miku", "teacher");

        given().contentType(ContentType.JSON).body(user.toJSONString())
            .when().put("/api/users/2").
                then().assertThat().contentType("application/json; charset=utf-8").and().
                header("Connection", "keep-alive");
    }
}