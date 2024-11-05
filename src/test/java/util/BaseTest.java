package util;

import io.restassured.RestAssured;
import lombok.SneakyThrows;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.util.Properties;

public class BaseTest {
    protected static Properties properties;
    protected static String adminUsername;
    protected static String adminPassword;
    protected static String userNotFound;
    protected static String invalidPassword;
    protected static String usernameNull;
    protected static String passwordNull;
    protected static String adminEndpoint;

    @BeforeClass
    @SneakyThrows
    public static void setup() {
        // Initialize the Properties object and load from file
        properties = new Properties();
        properties.load(BaseTest.class.getClassLoader().getResourceAsStream("application.properties"));

        // Set up RestAssured base URI
        RestAssured.baseURI = properties.getProperty("api.base.url");

        // Load property values for tests
        adminUsername = properties.getProperty("admin.username");
        adminPassword = properties.getProperty("admin.password");
        userNotFound = properties.getProperty("user.notFound.username");
        invalidPassword = properties.getProperty("user.invalid.password");
        usernameNull = properties.getProperty("user.empty.username");
        passwordNull = properties.getProperty("user.empty.password");
        adminEndpoint = properties.getProperty("admin.login.endpoint");
    }
}
