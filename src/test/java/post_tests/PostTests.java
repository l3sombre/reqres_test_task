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
     * tests whether HTTP status code is valid for POST-method
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
     * tests whether the values in the response payload are valid
     * when null-valued parameters have been sent in request body
     */
    @Test
    public void testNullValuesInBody() {
        JSONUser user = new JSONUser(null,null);

        given().
            header("Content-Type", "application/json").
            contentType(ContentType.JSON).
            body(user.toJSONString()).
                when().post("/api/users").
                    then().
                    body("name", notNullValue()).and().
                    body("job", notNullValue());
    }


    /**
     * tests whether the keys in the response payload are valid
     * when invalid key-value pairs have been sent in request body
     */
    @Test
    public void testInvalidKeysInBody() {
        JSONObject obj = new JSONObject();
        obj.put("flower", "dandelion");
        obj.put("job", "stylist");

        given().contentType(ContentType.JSON).
                body(obj.toJSONString()).when().post("/api/users")
                .then().assertThat().
                body("$", hasKey("name")).and().
                body("$", hasKey("job"));
    }

    /**
     * tests and verifies the response payload of POST-method
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
     * tests and verifies the response header of POST-method
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
