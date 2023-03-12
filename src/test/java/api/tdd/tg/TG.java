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

    @Test
    public void postStudent(){

        logger.info("Beginning of the test");

        String expFirstName = faker.name().firstName();
        String expLastName = faker.name().lastName();
        String expEmail = expFirstName + expLastName + "@gmail.com";
        String expDOB = "1980-09-23";

        PostStudent postStudent = PostStudent
                .builder()
                .firstName(expFirstName)
                .lastName(expLastName)
                .email(expEmail)
                .dob(expDOB)
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(postStudent)
                .when().post(ConfigReader.getProperty("TGStudentsUrl"))
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();

        String actFirstName = JsonPath.read(response.asString(), "firstName");
        String actLastName = JsonPath.read(response.asString(), "lastName");
        String actEmail = JsonPath.read(response.asString(), "email");
        String actDOB = JsonPath.read(response.asString(), "dob");

        logger.debug("The expected first name is " + expFirstName + " and we fount " + actFirstName);
        assertThat(
                "Checking if the expected first name is matching with the actual one",
                actFirstName,
                is(expFirstName)
        );

        logger.debug("The expected last name is " + expLastName + " and we fount " + actLastName);
        assertThat(
                "Checking if the expected last name is matching with the actual one",
                actLastName,
                is(expLastName)
        );

        logger.debug("The expected email is " + expEmail + " and we fount " + actEmail);
        assertThat(
                "Checking if the expected email is matching with the actual one",
                actEmail,
                is(expEmail)
        );

        logger.debug("The expected dob is " + expDOB + " and we fount " + actDOB);
        assertThat(
                "Checking if the expected dob is matching with the actual one",
                actDOB,
                is(expDOB)
        );

    }

}
