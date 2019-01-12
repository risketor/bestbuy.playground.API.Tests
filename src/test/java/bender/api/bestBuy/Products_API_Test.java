package bender.api.bestBuy;

import bender.testData.TestData;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import javax.json.Json;
import javax.json.JsonObject;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by A.Bartolome 11/01/2019
 *
 * PRODUCTS ENDPOINT
 */
public class Products_API_Test {

    private String endpoint = TestData.PRODUCTS_ENDPOINT;

    /**
     * GET /products - 200
     * Returns all products that match the given filter criteria. If no filters are included, defaults to returning a paginated list of all products.
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
     * POST /products - 200
     * Creates a new product
     */
    @Test
    public void POST_200() {
        RestAssured.baseURI = TestData.baseURI;

        JsonObject request = Json.createObjectBuilder()
                .add("name",  TestData.TEST_NAME)
                .add("type", "Test type")
                .add("upc", "99.9")
                .add("description", "cartoons")
                .add("model", "1.2")
                .build();

        JsonPath response = given()
                .contentType("application/json")
                .body(request.toString())
                .expect()
                .statusCode(201)
                .body("name", equalTo( TestData.TEST_NAME))
                .log().all().request().and().response()

                .when()
                .post(endpoint).jsonPath();
    }

    /**
     * POST /products - 400 - Invalid Parameters
     * Creates a new product
     * Missing or empty field(s) will return Invalid Parameters
     */
    @Test
    public void POST_400() {
        RestAssured.baseURI = TestData.baseURI;

        JsonObject request = Json.createObjectBuilder()
                // name field will be empty == invalid parameter
                .add("name", "")
                .add("type", "Test type")
                .add("upc", "99.9")
                .add("description", "cartoons")
                .add("model", "1.2")
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
     * DELETE /products/{id} - 200
     * Deletes a single product based on the ID supplied
     * First will create a new product to get an ID, then Delete it
     */
    @Test
    public void DELETE_200() {
        RestAssured.baseURI = TestData.baseURI;

        JsonObject request = Json.createObjectBuilder()
                .add("name",  TestData.TEST_NAME)
                .add("type", "Test type")
                .add("upc", "99.9")
                .add("description", "cartoons")
                .add("model", "1.2")
                .build();

        // Create a product
        JsonPath response = given()
                .contentType("application/json")
                .body(request.toString())
                .expect()
                .statusCode(201)
                .log().all().request().and().response()

                .when()
                .post("/products/").jsonPath();

        // Delete the product
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
     * DELETE /products/{id} - 404
     * Deletes a non-existing product, response will be 404
     */
    @Test
    public void DELETE_404() {
        RestAssured.baseURI = TestData.baseURI;

        given()
                .contentType("application/json; charset=utf-8")
                .expect()
                .statusCode(404)
                .body("name", equalTo("NotFound"))
                .log().all().request().and().response()

                .when()
                .delete(endpoint + TestData.NON_EXISTING_ID);
    }


    /**
     * GET /products/{id} - 200
     * Returns a product based on an ID,
     *
     */
    @Test
    public void GET_ID_200() {
        RestAssured.baseURI = TestData.baseURI;

        given()
                .contentType("application/json; charset=utf-8")
                .expect()
                .statusCode(200)
                .body("id", equalTo(TestData.PRODUCT_ID),
                        "name", equalTo(TestData.PRODUCT_NAME))
                .log().all().request().and().response()

                .when()
                .get(endpoint + TestData.PRODUCT_ID);
    }

    /**
     * GET /products/{id} - 404
     * Returns 404 when a non-existing product
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
     * PATCH /products/{id} - 200
     * Updates a product based on an ID
     */
    @Test
    public void PATCH_200() {
        RestAssured.baseURI = TestData.baseURI;

        JsonObject request = Json.createObjectBuilder()
                .add("name",  TestData.TEST_NAME)
                .add("type", "Test type")
                .add("upc", "99.9")
                .add("description", "cartoons")
                .add("model", "1.2")
                .build();

        // Create a product
        JsonPath response = given()
                .contentType("application/json")
                .body(request.toString())
                .expect()
                .statusCode(201)
                .log().all().request().and().response()

                .when()
                .post("/products/").jsonPath();

        JsonObject request_updated = Json.createObjectBuilder()
                .add("name",  TestData.TEST_NAME)
                .add("type", "Test type")
                .add("upc", "99.9")
                .add("description", "cartoons")

                // model value now is different
                .add("model", "2.0")
                .build();

        // Update the product - model to different value
        given()
                .contentType("application/json")
                .body(request_updated.toString())
                .expect()
                .statusCode(200)
                .body("model", equalTo("2.0"))

                .log().all().request().and().response()

                .when()
                .patch(endpoint + response.get("id"));
    }

    /**
     * PATCH /products/{id} - 404
     * non-existing ID
     */
    @Test
    public void PATCH_404() {
        RestAssured.baseURI = TestData.baseURI;

        JsonObject request = Json.createObjectBuilder()
                .add("name",  TestData.TEST_NAME)
                .add("type", "Test type")
                .add("upc", "99.9")
                .add("description", "cartoons")
                .add("model", "1.2")
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