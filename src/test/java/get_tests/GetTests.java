package get_tests;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static io.restassured.RestAssured.*;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.*;
import base_tests.BaseTests;


public class GetTests extends BaseTests {


    /**
     * Tests whether HTTP status code is valid for GET-method;
     * 1st test-case
     */
    @Test
    public void testStatusCode(){
        given().pathParam("id", "1").
            when().get("/api/users/{id}").
                then().assertThat().statusCode(200);
    }

    /**
     * Tests whether specified delay time is greater or equal to actual one;
     * 12th test-case
     */
    @Test
    public void testDelayTime(){
        given().formParam("delay", "3").
            when().get("/api/users/").
                then().
                time(greaterThanOrEqualTo(3L), SECONDS);
    }

    /**
     * Tests whether the status code is 404 and the response payload is empty
     * when the invalid path parameter has sent;
     * 13th test-case
     */
    @Test
    public void testUserWithInvalidId(){
        String response = given().
                when().get("/api/users/string").
                    then().assertThat().statusCode(404).extract().toString();
        assertNotNull(response);
    }

    /**
     * Tests and verifies the response payload of GET-method;
     * 5th test-case
     */
    @Test
    public void testResponsePayload(){
        String firstName = "George";
        String lastName = "Bluth";
        String email = "george.bluth@reqres.in";

        given().
            pathParam("id", "1").
                when().get("/api/users/{id}").
                    then().
                        assertThat().body("data.id", equalTo(1)).and().
                        body("data.email", equalTo(email)).and().
                        body("data.first_name", equalTo(firstName)).and().
                        body("data.last_name", equalTo(lastName)).and().
                        body("data.avatar", notNullValue());
    }

    /**
     * Tests and verifies the response header of GET-method;
     * 9th test-case
     */
    @Test
    public void testResponseHeader(){
       given().
           when().get("/api/users/1").
               then().assertThat().contentType("application/json; charset=utf-8").and().
               header("Connection", "keep-alive");
    }
}
