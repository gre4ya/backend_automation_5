package api.tdd.tg;

import api.pojo_classes.tg.PostStudent;
import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class TG {
    static Logger logger = LogManager.getLogger(TG.class);
    Faker faker = new Faker();
    Response response;

    /*
    {
    "firstName": "{{$randomFirstName}}",
    "lastName": "{{$randomLastName}}",
    "email": "{{$randomEmail}}",
    "dob": "1990-12-14"
    }
     */
    @Test
    public void postStudent(){

        logger.info("Beginning of the test");

        String expFirstName = faker.name().firstName();
        String expLastName = faker.name().lastName();
        String expEmail = expFirstName + expLastName + "@gmail.com";
        String dob = "1980-09-23";

        PostStudent postStudent = PostStudent
                .builder()
                .firstName(expFirstName)
                .lastName(expLastName)
                .email(expEmail)
                .dob(dob)
                .build();


        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(postStudent)
                .when().post(ConfigReader.getProperty("TGStudentsUrl"))
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();

        int actual_idWithJayWay = JsonPath.read(response.asString(), "id");

        String actFirstName = JsonPath.read(response.asString(), "firstName");
        String actLastName = JsonPath.read(response.asString(), "lastName");
        String actEmail = JsonPath.read(response.asString(), "email");
        String actDOB = JsonPath.read(response.asString(), "dob");
    }


}
