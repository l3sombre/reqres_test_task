package post_tests;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import base_tests.BaseTests;
import json_user.JSONUser;


public class PostTests extends BaseTests {


    /**
     * Tests whether HTTP status code is valid for POST-method;
     * 2nd test-case
     */
    @Test
    public void testStatusCode() {
        JSONUser user = new JSONUser("alla", "singer");

        given().
        body(user.toJSONString()).
            when().post("/api/users").
                then().assertThat().statusCode(201);
    }


    /**
     * Tests whether the values in the response payload are valid
     * when null-valued parameters have been sent in request body;
     * 14th test-case
     */
    @Test
    public void testNullValuesInBody() throws AssertionError {
        JSONUser user = new JSONUser(null,null);
        boolean success = false;

        try {
            given().
                    header("Content-Type", "application/json").
                    contentType(ContentType.JSON).
                    body(user.toJSONString()).
                    when().post("/api/users").
                    then().
                    body("name", notNullValue()).and().
                    body("job", notNullValue());
            success = true;
        }
        catch (AssertionError error) {
        }
         if (success) {
             throw new AssertionError("The test has to be failed because of null parameter values in the request.");
         }

    }


    /**
     * Tests whether the keys in the response payload are valid
     * when invalid key-value pairs have been sent in request body;
     * 15th test-case
     */
    @Test
    public void testInvalidKeysInBody() throws AssertionError {
        JSONObject obj = new JSONObject();
        obj.put("flower", "dandelion");
        obj.put("job", "stylist");

        boolean success = false;
        try {
            given().contentType(ContentType.JSON).
                    body(obj.toJSONString()).when().post("/api/users")
                    .then().assertThat().
                    body("$", hasKey("name")).and().
                    body("$", hasKey("job"));
            success = true;
        }
        catch (AssertionError error) {
        }
        if (success) {
            throw new AssertionError("The test has to be failed because of invalid key values in the request.");
        }


    }

    /**
     * Tests and verifies the response payload of POST-method;
     * 6th test-case
     */
    @Test
    public void testResponsePayload(){
        JSONUser user = new JSONUser("maria", "gardener");

        given().contentType(ContentType.JSON).body(user.toJSONString()).
                when().post("/api/users").
                    then().assertThat().
                    body("$", hasKey("name")).and().
                    body("$", hasKey("job")).and().
                    body("$", hasKey("id")).and().
                    body("$", hasKey("createdAt")).and().
                    body("name", equalTo(user.getUserName())).and().
                    body("job", equalTo(user.getUserJob())).and().
                    body("id", notNullValue()).and().
                    body("createdAt", notNullValue()).and().
                    body("id", not(equals(""))).and().
                    body("createdAt", not(equals("")));
    }

    /**
     * Tests and verifies the response header of POST-method;
     * 10th test-case
     */
    @Test
    public void testResponseHeader(){
        JSONUser user = new JSONUser("maria", "gardener");

        given().contentType(ContentType.JSON).body(user.toJSONString()).
                when().post("/api/users").
                    then().
                    assertThat().contentType("application/json; charset=utf-8").and().
                    header("Connection", "keep-alive");
    }
}
