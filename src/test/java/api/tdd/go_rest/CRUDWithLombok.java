package api.tdd.go_rest;

import api.pojo_classes.go_rest.CreateUserWithLombok;
import api.pojo_classes.go_rest.UpdateUserWithLombok;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class CRUDWithLombok {

    static Response response;
    Faker faker = new Faker();
    int user_id;

    @BeforeTest
    public void beforeTest(){
        System.out.println("Starting the API test");
        RestAssured.baseURI = ConfigReader.getProperty("GoRestBaseUrL");
    }

    @Test
    public void goRestCRUDWithLombok(){

        CreateUserWithLombok createUser = CreateUserWithLombok
                .builder()
                .name("Taras Shevchenko")
                .email(faker.internet().emailAddress())
                .gender("male")
                .status("active")
                .build();

        /** -----------------------POST-----------------------*/

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(createUser)
                .when().post( "/public/v2/users/")
                .then().log().all()
                .and().statusCode(201)
                .extract().response();

        user_id = response.jsonPath().getInt("id");

        /** -----------------------GET-----------------------*/

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().get( "/public/v2/users/" + user_id)
                .then().log().all()
                .and().statusCode(200)
                .extract().response();

        /** -----------------------GET-ALL-----------------------*/

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().get( "/public/v2/users")
                .then().log().all()
                .and().statusCode(200)
                .extract().response();

        /** -----------------------PUT-----------------------*/

        createUser.setName(faker.name().fullName());
        createUser.setEmail(faker.internet().emailAddress());

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(createUser)
                .when().put( "/public/v2/users/" + user_id)
                .then().log().all()
                .and().statusCode(200)
                .extract().response();

        UpdateUserWithLombok updateUserWithLombok = UpdateUserWithLombok
                .builder()
                .email(faker.internet().emailAddress())
                .gender("female")
                .status("inactive")
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(updateUserWithLombok)
                .when().put( "/public/v2/users/" + user_id)
                .then().log().all()
                .and().statusCode(200)
                .extract().response();

        /** -----------------------PATCH-----------------------*/

        createUser.setName(faker.name().fullName());

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .body(createUser)
                .when().put( "/public/v2/users/" + user_id)
                .then().log().all()
                .and().statusCode(200)
                .extract().response();

        /** -----------------------DELETE-----------------------*/

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", ConfigReader.getProperty("GoRestToken"))
                .when().delete( "/public/v2/users/" + user_id)
                .then().log().all()
                .and().statusCode(204)
                .extract().response();

    }

}
