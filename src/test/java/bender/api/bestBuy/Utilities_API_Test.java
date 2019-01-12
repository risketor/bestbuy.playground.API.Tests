package bender.api.bestBuy;

import bender.testData.TestData;
import com.jayway.restassured.RestAssured;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by A.Bartolome 11/01/2018
 * <p>
 * UTILITIES ENDPOINTS
 */
public class Utilities_API_Test {


    private String endpoint_version = TestData.VERSION_ENDPOINT;
    private String endpoint_healthcheck = TestData.HEALTHCHECK_ENDPOINT;

    /**
     * GET /version - 200
     * Returns the current version of the API Playground being run
     */
    @Test
    public void GET_200_Version() {
        RestAssured.baseURI = TestData.baseURI;

        given()
                .contentType("application/json; charset=utf-8")
                .expect()
                .statusCode(200)
                .log().all().request().and().response()

                .when()
                .get(endpoint_version);
    }

    /**
     * GET /healthcheck - 200
     * Returns healthcheck information about the system
     */
    @Test
    public void GET_200_Healthcheck() {
        RestAssured.baseURI = TestData.baseURI;

        given()
                .contentType("application/json; charset=utf-8")
                .expect()
                .statusCode(200)
                .log().all().request().and().response()

                .when()
                .get(endpoint_healthcheck);
    }
}