package api.tdd.go_rest;

import api.pojo_classes.go_rest.CreateUserWithLombok;
import api.pojo_classes.go_rest.UpdateUserWithLombok;
import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class schemaValidation {
    static Logger logger = LogManager.getLogger(api.tdd.pet_store.CRUDWithLombok.class);

    static Response response;
    Faker faker = new Faker();

    @BeforeSuite
    public void testStarts(){
        logger.info("Starting POST schema validation");
    }

    @BeforeTest
    public void beforeTest(){
        RestAssured.baseURI = ConfigReader.getProperty("GoRestBaseUrL");
    }

    @Test
    public void goRestPOSTLombok() throws FileNotFoundException {

        CreateUserWithLombok createUser = CreateUserWithLombok
                .builder()
                .name("Taras Shevchenko")
                .email(faker.internet().emailAddress())
                .gender("male")
                .status("active")
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(createUser)
                .when().post( "/public/v2/users/")
                .then().log().all()
                .and().statusCode(201)
                .contentType(ContentType.JSON)
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new FileInputStream("src/test/java/api/json_schema/go_rest/go_rest_post_response.json")))
                .extract().response();

    }

}
