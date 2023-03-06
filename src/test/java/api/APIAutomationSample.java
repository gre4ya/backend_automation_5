package api;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

public class APIAutomationSample {
    public static void main(String[] args) {

        /**
         Response is an Interface coming from RestAssured library
         The Response variable "response" stores all the comments of API calls including the request and response
         RestAssured is written with BDD flow
         */
        Faker faker = new Faker();
        Response response;

        /** Creating POST call to create the specific user*/

        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e")
                .body("{\n" +
                        "    \"name\": \"" + faker.name().fullName() + "\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"gender\": \"female\",\n" +
                        "    \"status\": \"active\"\n" +
                        "}")
                .when().post("https://gorest.co.in/public/v2/users")
                .then().log().all().extract().response();

        //System.out.println(response.asString());

        int postId = response.jsonPath().getInt("id");

        /** Creating GET call to retrieve the specific user */

        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e")
                .when().get("https://gorest.co.in/public/v2/users/" + postId)
                .then().log().all().extract().response();

        /** Creating GET call to retrieve All users */

        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e")
                .when().get("https://gorest.co.in/public/v2/users")
                .then().log().all().extract().response();

        /** Creating PUT call to update the specific user */

        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e")
                .body("{\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"status\": \"active\"\n" +
                        "}")
                .when().put("https://gorest.co.in/public/v2/users/" + postId)
                .then().log().all().extract().response();

        /** Creating PATCH call to update specific field of the specific user */

        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e")
                .body("{\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"status\": \"active\"\n" +
                        "}")
                .when().patch("https://gorest.co.in/public/v2/users/" + postId)
                .then().log().all().extract().response();

        int patchId = response.jsonPath().getInt("id");

        Assert.assertEquals(postId, patchId, "Expected id " + postId + " we found " + patchId);

        /** Creating DELETE call to delete specific user */

        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e")
                .when().delete("https://gorest.co.in/public/v2/users/" + postId)
                .then().log().all().extract().response();
    }
}
