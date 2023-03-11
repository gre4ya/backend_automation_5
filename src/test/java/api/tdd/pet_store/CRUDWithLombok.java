package api.tdd.pet_store;

import api.pojo_classes.pet_store.Category;
import api.pojo_classes.pet_store.AddAPet;
import api.pojo_classes.pet_store.Tags;
import api.pojo_classes.pet_store.UpdateAPet;
import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class CRUDWithLombok {

    static Logger logger = LogManager.getLogger(CRUDWithLombok.class);
    Response response;
    @BeforeSuite
    public void testStarts(){
        logger.info("Starting the test suite");
    }
    @BeforeTest
    public void beforeTest(){
        System.out.println("Starting the API test");
        RestAssured.baseURI = ConfigReader.getProperty("PetStoreBaseUrL");
    }

    @Test
    public void addPetToStore(){
        Category category = Category
                .builder()
                .id(29)
                .name("monkey")
                .build();

        Tags tag0 = Tags
                .builder()
                .id(45)
                .name("Boss")
                .build();
        Tags tag1 = Tags
                .builder()
                .id(46)
                .name("Baby")
                .build();


        AddAPet addPet = AddAPet
                .builder()
                .id(178)
                .category(category)
                .name("MK")
                .photoUrls(Arrays.asList("Monkey's Photo URL"))
                .tags(Arrays.asList(tag0, tag1))
                .status("available")
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)

                .body(addPet)
                .when().post("/v2/pet")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();

        // getting the id from the response body
        int actual_id = response.jsonPath().getInt("id");
        int actualTag0_id = response.jsonPath().getInt("tags[0].id");
        int actualTag1_id = response.jsonPath().getInt("tags[1].id");

        // getting the id from the request body
        int expected_id = addPet.getId();
        int expectedTag0_id = tag0.getId();
        int expectedTag1_id = tag1.getId();


        int actual_idWithJayWay = JsonPath.read(response.asString(), "id");
        int petTag_idWithJayWay = JsonPath.read(response.asString(), "tags[0].id");
        logger.info("My actual id with JayWay is " + actual_idWithJayWay);
        logger.info("My pet tag id with JayWay is " + petTag_idWithJayWay);

        logger.info("Actual pet_id is " + actual_id);
        logger.debug("The actual pet_id is " + expected_id + " but we fount " + actual_id);


        // Assertion with Hamcrest

        assertThat(
                // reason why we are asserting
                "I am checking if the expected value is matching with the actual one",
                //actual value
                actual_idWithJayWay,
                //expected value
                is(expected_id)
        );

        Assert.assertEquals(actual_id, expected_id);
        Assert.assertEquals(actualTag0_id, expectedTag0_id);
        Assert.assertEquals(actualTag1_id, expectedTag1_id);

        /** --------------------PUT-------------------- */

        Category update_category = Category
                .builder()
                .id(11)
                .name("MA")
                .build();

        UpdateAPet updatePet = UpdateAPet
                .builder()
                .id(178)
                .category(update_category)
                .photoUrls(Arrays.asList("Monkey's Photo URL"))
                .tags(Arrays.asList(tag0, tag1))
                .status("unavailable")
                .build();

        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(updatePet)
                .when().put("/v2/pet")
                .then().log().all()
                .statusCode(200)
                .extract().response();


    }

}
