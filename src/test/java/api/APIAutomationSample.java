package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIAutomationSample {
    public static void main(String[] args) {

        /**
         Response is an Interface coming from RestAssured library
         The Response variable "response" stores all the comments of API calls including the request and response
         RestAssured is written with BDD flow
         */
        Response response;
        response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer cd6f43f79e931dc381c5c228f3e80c9f6990ec23e970e0325e206b9d241e551e")
                .body("{\n" +
                        "    \"name\": \"Lena Popondopulos\",\n" +
                        "    \"email\": \"Delph8@yahoo.com\",\n" +
                        "    \"gender\": \"female\",\n" +
                        "    \"status\": \"active\"\n" +
                        "}")
                .when().post("https://gorest.co.in/public/v2/users")
                .then().extract().response();

        System.out.println(response.asString());
    }
}
