package api.tdd.go_rest;

import api.pojo_classes.go_rest.CreateUser;
import api.pojo_classes.go_rest.UpdateUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CRUD {
    static Logger logger = LogManager.getLogger(api.tdd.pet_store.CRUDWithLombok.class);

    static Response response;
    /** ObjectMapper is a Class which is coming from fasterxml(Jackson) to convert Java class to JSON */

    ObjectMapper objectMapper = new ObjectMapper();
    Faker faker = new Faker();

    int expected_user_id;

    String exp_name;
    String exp_email;
    String exp_gender;
    String exp_status;

    @BeforeTest
    public void beforeTest(){
        System.out.println("Starting the API test");
        RestAssured.baseURI = ConfigReader.getProperty("GoRestBaseUrL");
    }

    @Test
    public void goRestCRUD() throws JsonProcessingException {

        //Creating a POJO (Bean - when encapsulated) object
        CreateUser createUser = new CreateUser();

        // assigning the values to the attributes
        createUser.setName("Oksana Pukach");
        createUser.setGender("female");
        createUser.setEmail(faker.internet().emailAddress());
        createUser.setStatus("active");

        System.out.println("--------------POST---------------");

        response = RestAssured
                .given().log().all()
                //.header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createUser))
                .when().post( "/public/v2/users/")
                .then().log().all()
                // validating the status code with rest assured
                .assertThat().statusCode(201)
                // validating the time of the execution (x-runtime: 0.053983) with rest assured
                .time(Matchers.lessThan(10000L))
                // validating the value from the body with the help of hamcrest
                .body("name", equalTo("Oksana Pukach"))
                // validating response content type
                .contentType(ContentType.JSON)
                .extract().response();

        logger.debug("The expected status should be " + createUser.getStatus() + " and we fount " + JsonPath.read(response.asString(), "status"));

        assertThat(
                // reason why we are asserting
                "Checking if the expected status is matching with the actual one",
                //actual value
                JsonPath.read(response.asString(), "status"),
                //expected value
                is(createUser.getStatus())
        );



        System.out.println("--------------GET-a-user---------------");

        expected_user_id = response.jsonPath().getInt("id");

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().get( "/public/v2/users/" + expected_user_id)
                .then().log().all()
                .assertThat().statusCode(200)
                .time(Matchers.lessThan(10000L))
                .body("name", equalTo("Oksana Pukach"))
                .contentType(ContentType.JSON)
                .extract().response();

        System.out.println("--------------PUT---------------");

        UpdateUser updateUser = new UpdateUser();
        updateUser.setName("Myhailo Kobza");
        updateUser.setEmail(faker.internet().emailAddress());

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(updateUser))
                .when().put( "/public/v2/users/" + expected_user_id)
                .then().log().all()
                .assertThat().statusCode(200)
                .time(Matchers.lessThan(10000L))
                .body("name", equalTo("Myhailo Kobza"))
                .contentType(ContentType.JSON)
                .extract().response();

        exp_name = updateUser.getName();
        // exp_email = updateGoRestUser.getEmail();
        // exp_gender = createGoRestUser.getGender();
        // exp_status = createGoRestUser.getStatus();

        Assert.assertEquals(getAttributeValueInt("id"), expected_user_id);
        Assert.assertEquals(getAttributeValueString("name"), exp_name);
        Assert.assertEquals(getAttributeValueString("email"), updateUser.getEmail());
        Assert.assertEquals(getAttributeValueString("gender"), createUser.getGender());
        Assert.assertEquals(getAttributeValueString("status"), createUser.getStatus());

        System.out.println("--------------DELETE-user---------------");

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().delete( "/public/v2/users/" + expected_user_id)
                .then().log().all()
                .assertThat().statusCode(204)
                .time(Matchers.lessThan(10000L))
                .extract().response();
    }

    public static String getAttributeValueString(String attribute){return response.jsonPath().getString(attribute);}
    public static int getAttributeValueInt(String attribute){return response.jsonPath().getInt(attribute);}
}
