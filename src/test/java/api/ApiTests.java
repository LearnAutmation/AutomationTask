package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class ApiTests {

    private String token;
    private String baseUrl;

    @BeforeClass
    public void setup() {
        baseUrl = ConfigReader.get("api.baseUrl");
        String username = ConfigReader.get("api.username");
        String password = ConfigReader.get("api.password");

        String payload = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        Response res = given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/auth/login");

        System.out.println("Login response:\n" + res.prettyPrint());


        res.then().statusCode(200).body("accessToken", notNullValue());
        token = res.jsonPath().getString("accessToken");
    }

    /*
    Fetches the currently authenticated user's details using the token
     */

    @Test(priority = 1)
    public void getAuthenticatedUser() {
        Response res = given().baseUri(baseUrl)
                .header("Authorization", "Bearer " + token)
                .when().get("/auth/me");

        System.out.println("Get Authenticated User response:\n" + res.prettyPrint()); // ✅ Log response

                res.then().statusCode(200).body("email", notNullValue());
    }

    /*
      Creates a cart for a user with specified products
     */
    @Test(priority = 2)
    public void createCartForUser() {
        String payload = "{ \"userId\": 1, \"products\": [ {\"id\": 1, \"quantity\": 2} ] }";

        Response res = given().baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(payload)
                .when().post("/carts/add");
        System.out.println("Get Authenticated User response:\n" + res.prettyPrint()); // ✅ Log response

               res.then().statusCode(201)
                .body("userId", equalTo(1));
    }

    /*
      Updates title and price of product ID 1
     */
    @Test(priority = 3)
    public void updateProductInfo() {
        String payload = "{ \"title\": \"Updated Title\", \"price\": 123.45 }";

        Response res = given().baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(payload)
                .when().put("/products/1");
        System.out.println("Get Authenticated User response:\n" + res.prettyPrint()); // ✅ Log response

                res.then().statusCode(200)
                .body("title", equalTo("Updated Title"))
                .body("price", equalTo(123.45f));
    }

     /*
        Validates the JSON response of a product against the schema file
      */
    @Test(priority = 4)
    public void validateProductSchema() {
        InputStream schema = getClass().getClassLoader().getResourceAsStream("product_schema.json");

        Response res = given().baseUri(baseUrl)
                .when().get("/products/1");
        System.out.println("Get Authenticated User response:\n" + res.prettyPrint()); // ✅ Log response

                res.then().statusCode(200)
                .body(matchesJsonSchema(schema));
    }

    /*
    Confirms error handling for invalid login attempt
     */
    @Test(priority = 5)
    public void loginWithInvalidCredentials() {
        String payload = "{\"username\": \"wronguser\", \"password\": \"wrongpass\"}";

        given().baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(payload)
                .when().post("/auth/login")
                .then().statusCode(400)
                .body("message", notNullValue());
    }
}
