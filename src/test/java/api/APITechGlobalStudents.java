package api;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APITechGlobalStudents {

    public static void main(String[] args) {

        Faker faker = new Faker();
        Response response;

        /** POST */
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstName\": \"" + faker.name().firstName() + "\",\n" +
                        "    \"lastName\": \"" + faker.name().lastName() + "\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"dob\": \"1990-12-14\"" +
                        "\n}")
                .when().post("https://tech-global-training.com/students")
                .then().log().all().extract().response();

        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstName\": \"" + faker.name().firstName() + "\",\n" +
                        "    \"lastName\": \"" + faker.name().lastName() + "\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"dob\": \"1990-12-14\"" +
                        "\n}")
                .when().post("https://tech-global-training.com/students")
                .then().log().all().extract().response();

        int post_id = response.jsonPath().getInt("id");

        /** GET All users */
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .when().get("https://tech-global-training.com/students")
                .then().log().all().extract().response();

        /** GET Specific user */
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .when().get("https://tech-global-training.com/students/" + post_id)
                .then().log().all().extract().response();

        /** PUT */
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstName\": \"" + faker.name().firstName() + "\",\n" +
                        "    \"lastName\": \"" + faker.name().lastName() + "\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress() + "\",\n" +
                        "    \"dob\": \"1990-12-15\"" +
                        "\n}")
                .when().put("https://tech-global-training.com/students/" + post_id)
                .then().log().all().extract().response();

        /** PATCH */
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstName\": \"" + faker.name().firstName() + "\",\n" +
                        "    \"lastName\": \"" + faker.name().lastName() + "\"" +
                        "\n}")
                .when().patch("https://tech-global-training.com/students/" + post_id)
                .then().log().all().extract().response();

        /** DELETE Specific user */
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .when().delete("https://tech-global-training.com/students/" + post_id)
                .then().log().all().extract().response();

        /** DELETE All users */
        response = RestAssured
                .given().log().all()
                .header("Content-Type", "application/json")
                .when().delete("https://tech-global-training.com/students/deleteAll")
                .then().log().all().extract().response();
    }
}
