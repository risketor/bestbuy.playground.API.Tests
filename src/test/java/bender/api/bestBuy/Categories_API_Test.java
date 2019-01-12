package bender.api.bestBuy;

import bender.testData.TestData;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import javax.json.Json;
import javax.json.JsonObject;
import java.util.Random;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by A.Bartolome 11/01/2019
 *
 * CATEGORIES ENDPOINT
 *
 */
public class Categories_API_Test {


    private String endpoint = TestData.CATEGORY_ENDPOINT;

    /**
     * GET /categories - 200
     * Returns all categories that match the given filter criteria. If no filters are included, defaults to returning a paginated list of all categories.
     */
    @Test
    public void GET_200() {
        RestAssured.baseURI = TestData.baseURI;

        given()
                .contentType("application/json; charset=utf-8")
                .expect()
                .statusCode(200)
                .log().all().request().and().response()

                .when()
                .get(endpoint);
    }


    /**
     * POST /categories - 200
     * Creates a new category
     */
    @Test
    public void POST_201() {
        RestAssured.baseURI = TestData.baseURI;

        // As ID should be unique, a random one is generated
        Random rand = new Random();
        int value = rand.nextInt(500000);

        JsonObject request = Json.createObjectBuilder()
                .add("id", Integer.toString(value))
                .add("name", TestData.TEST_NAME)
                .build();

        JsonPath response = given()
                .contentType("application/json")
                .body(request.toString())
                .expect()
                .statusCode(201)
                .body("name", equalTo(TestData.TEST_NAME))
                .log().all().request().and().response()

                .when()
                .post(endpoint).jsonPath();
    }

    /**
     * POST /categories - 400 - Invalid Parameters
     * Creates a new category
     * Missing or empty field(s) will return Invalid Parameters
     */
    @Test
    public void POST_400() {
        RestAssured.baseURI = TestData.baseURI;

        JsonObject request = Json.createObjectBuilder()
                  .build();

        JsonPath response = given()
                .contentType("application/json")
                .body(request.toString())
                .expect()
                .statusCode(400)
                .body("message", equalTo("Invalid Parameters"))
                .log().all().request().and().response()

                .when()
                .post(endpoint).jsonPath();
    }

    /**
     * POST /categories - 400 - Invalid Parameters
     * Creates a new category
     * ID should be unique
     */
    @Test
    public void POST_400_id_must_be_unique() {
        RestAssured.baseURI = TestData.baseURI;

        // As ID should be unique, a random one is generated
        Random rand = new Random();
        int value = rand.nextInt(500000);

        JsonObject request = Json.createObjectBuilder()
                .add("id", Integer.toString(value))
                .add("name", TestData.TEST_NAME)
                .build();

        JsonPath response = given()
                .contentType("application/json")
                .body(request.toString())
                .expect()
                .statusCode(201)
                .body("name", equalTo(TestData.TEST_NAME))
                .log().all().request().and().response()

                .when()
                .post(endpoint).jsonPath();

        JsonPath response_duplicated_id = given()
                .contentType("application/json")
                .body(request.toString())
                .expect()
                .statusCode(400)
                .body("message", equalTo("Validation error"))
                .log().all().request().and().response()

                .when()
                .post(endpoint).jsonPath();
    }

    /**
     * DELETE /categories/{id} - 200
     * Deletes a single category based on the ID supplied
     * First will create a new category to get an ID, then Delete it
     */
    @Test
    public void DELETE_200() {
        RestAssured.baseURI = TestData.baseURI;

        // As ID should be unique, a random one is generated
        Random rand = new Random();
        int value = rand.nextInt(500000);

        JsonObject request = Json.createObjectBuilder()
                .add("id", Integer.toString(value))
                .add("name",  TestData.TEST_NAME)
                .build();

        // Create a category
        JsonPath response = given()
                .contentType("application/json")
                .body(request.toString())
                .expect()
                .statusCode(201)
                .body("name", equalTo( TestData.TEST_NAME))
                .log().all().request().and().response()

                .when()
                .post(endpoint).jsonPath();

        // Delete the category
        given()
                .contentType("application/json; charset=utf-8")
                .expect()
                .statusCode(200)
                .body("name", equalTo( TestData.TEST_NAME))
                .log().all().request().and().response()

                .when()
                .delete(endpoint + response.get("id"));
    }

    /**
     * DELETE /categories/{id} - 404
     * Deletes a non-existing category, response will be 404
     */
    @Test
    public void DELETE_404() {
        RestAssured.baseURI = TestData.baseURI;

        given()
                .contentType("application/json; charset=utf-8")
                .expect()
                .statusCode(404)
                .log().all().request().and().response()

                .when()
                .delete(endpoint + TestData.NON_EXISTING_ID);
    }


    /**
     * GET /categories/{id} - 200
     * Returns a category based on an ID,
     *
     */
    @Test
    public void GET_ID_200() {
        RestAssured.baseURI = TestData.baseURI;

        given()
                .contentType("application/json; charset=utf-8")
                .expect()
                .statusCode(200)
                .body("id", equalTo(TestData.CATEGORY_ID),
                        "name", equalTo(TestData.CATEGORY_NAME))
                .log().all().request().and().response()

                .when()
                .get(endpoint + TestData.CATEGORY_ID);
    }

    /**
     * GET /categories/{id} - 404
     * Returns 404 when a non-existing category
     */
    @Test
    public void GET_ID_404() {
        RestAssured.baseURI = TestData.baseURI;

        given()
                .contentType("application/json; charset=utf-8")
                .expect()
                .statusCode(404)
                .body("name", equalTo("NotFound"))
                .log().all().request().and().response()

                .when()
                .get(endpoint + TestData.NON_EXISTING_ID);
    }


    /**
     * PATCH /categories/{id} - 200
     * Updates a category based on an ID
     */
    @Test
    public void PATCH_200() {
        RestAssured.baseURI = TestData.baseURI;

        // As ID should be unique, a random one is generated
        Random rand = new Random();
        int value = rand.nextInt(500000);

        JsonObject request = Json.createObjectBuilder()
                .add("id", Integer.toString(value))
                .add("name",  TestData.TEST_NAME)
                .build();

        // Create a category
        JsonPath response = given()
                .contentType("application/json")
                .body(request.toString())
                .expect()
                .statusCode(201)
                .log().all().request().and().response()

                .when()
                .post(endpoint).jsonPath();

        JsonObject request_updated = Json.createObjectBuilder()
                .add("name",  TestData.TEST_NAME)
                .build();

        // Update the category - name to different value
        given()
                .contentType("application/json")
                .body(request_updated.toString())
                .expect()
                .statusCode(200)
                .body("name", equalTo( TestData.TEST_NAME))
                .log().all().request().and().response()

                .when()
                .patch(endpoint + response.get("id"));
    }


    /**
     * PATCH /categories/{id} - 404
     * non-existing ID
     */
    @Test
    public void PATCH_404() {
        RestAssured.baseURI = TestData.baseURI;

        // As ID should be unique, a random one is generated
        Random rand = new Random();
        int value = rand.nextInt(500000);

        JsonObject request = Json.createObjectBuilder()
                .add("id", Integer.toString(value))
                .add("name",  TestData.TEST_NAME)
                .build();

        // Update the product for a non-existing ID
        given()
                .contentType("application/json")
                .body(request.toString())
                .expect()
                .statusCode(404)
                .body("name", equalTo("NotFound"))
                .body("message", equalTo("No record found for id '" + TestData.NON_EXISTING_ID + "'"))
                .log().all().request().and().response()

                .when()
                .patch(endpoint + TestData.NON_EXISTING_ID);
    }
}