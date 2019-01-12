package bender.testData;

/**
 * Created by A.Bartolome 11/01/2018
 *
 * Test Data file: with URI, endpoints, existing test data, mock test data, etc...
 */
public class TestData {

    // BESTBUY PLAYGROUND URI
    public static final String baseURI = "http://localhost:3030/";

    // ENDPOINTS
    public static final String CATEGORY_ENDPOINT = "/categories/";
    public static final String PRODUCTS_ENDPOINT = "/products/";
    public static final String SERVICES_ENDPOINT = "/services/";
    public static final String STORES_ENDPOINT = "/stores/";
    public static final String VERSION_ENDPOINT = "/version/";
    public static final String HEALTHCHECK_ENDPOINT = "/healthcheck/";

    // MOCKING TEST DATA
    public static final String NON_EXISTING_ID = "1234567890";
    public static final String TEST_NAME = "Bender's thing";

    // TEST DATA FROM EXISTING ITEMS
    public static final String CATEGORY_NAME = "Gift Ideas";
    public static final String CATEGORY_ID = "abcat0010000";
    public static final String PRODUCT_NAME = "Duracell - AAA Batteries (4-Pack)";
    public static final int PRODUCT_ID = 43900;
    public static final String SERVICE_NAME = "Apple Shop";
    public static final int SERVICE_ID = 4;
    public static final String STORE_NAME = "Minnetonka";
    public static final int STORE_ID = 4;
}
