package base_tests;

import io.restassured.RestAssured;

import org.junit.BeforeClass;


public class BaseTests {

    @BeforeClass
    public static void setup() {
        String basePath = System.getProperty("base_path");
        if (basePath == null) {
            basePath = "";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("base_host");
        if(baseHost == null) {
            baseHost = "https://reqres.in";
        }
        RestAssured.baseURI = baseHost;
    }
}
