package api.tdd;

import api.pojo_classes.go_rest.CreateGoRestUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class GoRest {

    Response response;
    /**
     ObjectMapper is a Class which is coming from fasterxml to convert Java class to JSON
     */

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeTest
    public void beforeTest(){
        System.out.println("Starting the API test");
        RestAssured.baseURI = ConfigReader.getProperty("GoRestBaseUrL");

    }

    @Test
    public void goRestCRUD() throws JsonProcessingException {
        CreateGoRestUser createGoRestUser = new CreateGoRestUser();

        createGoRestUser.setName("Oksana Pukach");
        createGoRestUser.setGender("female");
        createGoRestUser.setEmail("o.pukach@iucloud.com");
        createGoRestUser.setStatus("active");

        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e")
                .body(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createGoRestUser))
                .when().post( RestAssured.baseURI + "/public/v2/users/")
                .then().log().all().extract().response();


    }
}
