package api.tdd;

import api.pojo_classes.go_rest.CreateGoRestUser;
import api.pojo_classes.go_rest.UpdateGoRestUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static org.hamcrest.Matchers.equalTo;

public class GoRest {

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
        CreateGoRestUser createGoRestUser = new CreateGoRestUser();

        // assigning the values to the attributes
        createGoRestUser.setName("Oksana Pukach");
        createGoRestUser.setGender("female");
        createGoRestUser.setEmail(faker.internet().emailAddress());
        createGoRestUser.setStatus("active");

        System.out.println("--------------POST a user---------------");

        response = RestAssured
                .given().log().all()
                //.header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createGoRestUser))
                .when().post( "/public/v2/users/")
                .then().log().all()
                // validating the status code with rest assured
                .assertThat().statusCode(201)
                // validating the time of the execution (x-runtime: 0.053983) with rest assured
                .time(Matchers.lessThan(2000L))
                // validating the value from the body with the help of hamcrest
                .body("name", equalTo("Oksana Pukach"))
                // validating response content type
                .contentType(ContentType.JSON)
                .extract().response();

        System.out.println("--------------GET a specific user---------------");

        expected_user_id = response.jsonPath().getInt("id");

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().get( "/public/v2/users/" + expected_user_id)
                .then().log().all()
                .assertThat().statusCode(200)
                .time(Matchers.lessThan(2000L))
                .body("name", equalTo("Oksana Pukach"))
                .contentType(ContentType.JSON)
                .extract().response();

        System.out.println("--------------Updating a user with PUT---------------");

        UpdateGoRestUser updateGoRestUser = new UpdateGoRestUser();
        updateGoRestUser.setName("Myhailo Kobza");
        updateGoRestUser.setEmail(faker.internet().emailAddress());

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(updateGoRestUser))
                .when().put( "/public/v2/users/" + expected_user_id)
                .then().log().all()
                .assertThat().statusCode(200)
                .time(Matchers.lessThan(4000L))
                .body("name", equalTo("Myhailo Kobza"))
                .contentType(ContentType.JSON)
                .extract().response();

        exp_name = updateGoRestUser.getName();
        // exp_email = updateGoRestUser.getEmail();
        // exp_gender = createGoRestUser.getGender();
        // exp_status = createGoRestUser.getStatus();

        Assert.assertEquals(getAttributeValueInt("id"), expected_user_id);
        Assert.assertEquals(getAttributeValueString("name"), exp_name);
        Assert.assertEquals(getAttributeValueString("email"), updateGoRestUser.getEmail());
        Assert.assertEquals(getAttributeValueString("gender"), createGoRestUser.getGender());
        Assert.assertEquals(getAttributeValueString("status"), createGoRestUser.getStatus());

        System.out.println("--------------DELETE user---------------");

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().delete( "/public/v2/users/" + expected_user_id)
                .then().log().all()
                .assertThat().statusCode(204)
                .time(Matchers.lessThan(4000L))
                .extract().response();
    }

    public static String getAttributeValueString(String attribute){return response.jsonPath().getString(attribute);}
    public static int getAttributeValueInt(String attribute){return response.jsonPath().getInt(attribute);}
}
