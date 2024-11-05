package admintest;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import util.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AdminLoginTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        String requestBody = "{ \"username\": \"" + adminUsername + "\", \"password\": \"" + adminPassword + "\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(adminEndpoint)
                .then()
                .statusCode(200)
                .log().ifError()
                .body("status", equalTo("success"))
                .body("message", equalTo("Admin Logged in Successfully!"));
    }
    @Test
    public void testUsernameNotFound() {
        // Request payload
        String requestBody = "{ \"username\": \""+userNotFound+"\", \"password\": \""+adminPassword+"\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(adminEndpoint)
                .then()
                .statusCode(404)
                .log().ifError()
                .body("error", equalTo("User not found"));
    }
    @Test
    public void testInvalidPassword() {
        // Request payload
        String requestBody = "{ \"username\": \""+adminUsername+"\", \"password\": \""+invalidPassword+"\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(adminEndpoint)
                .then()
                .statusCode(400)
                .log().ifError()
                .body("error", equalTo("Incorrect password"));
    }
    @Test
    public void testUserMandatory() {
        // Request payload
        String requestBody = "{ \"username\": \""+usernameNull+"\", \"password\": \""+adminPassword+"\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(adminEndpoint)
                .then()
                .statusCode(400)
                .log().ifError()
                .body("message", equalTo("Validation failed"));
    }
    @Test
    public void testPasswordMandatory() {
        // Request payload
        String requestBody = "{ \"username\": \""+adminUsername+"\", \"password\": \""+passwordNull+"\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(adminEndpoint)
                .then()
                .statusCode(400)
                .log().ifError()
                .body("message", equalTo("Validation failed"));
    }
    @Test
    public void testLoginInternalServerError() {
        // Request payload
        String requestBody = "{ \"username\": \""+adminUsername+"\", \"password\": \""+adminPassword+"\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/admin/logi")
                .then()
                .statusCode(500)
                .log().ifError()
                .body("error", equalTo("No static resource api/admin/logi."));
    }
    @Test
    public void testUnauthorizedUser() {
        // Request payload
        String requestBody = "{ \"username\": \"admin12\", \"password\": \"admin12\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(adminEndpoint)
                .then()
                .statusCode(401)
                .log().ifError()
                .body("error", equalTo("Invalid credentials or not authorized as admin"));
    }
}
