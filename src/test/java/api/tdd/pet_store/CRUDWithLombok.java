package api.tdd.pet_store;

import api.pojo_classes.pet_store.Category;
import api.pojo_classes.pet_store.AddAPet;
import api.pojo_classes.pet_store.Tags;
import api.pojo_classes.pet_store.UpdateAPet;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.Arrays;

public class CRUDWithLombok {

    Response response;
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

        Assert.assertEquals(actual_id, expected_id);
        Assert.assertEquals(actualTag0_id, expectedTag0_id);
        Assert.assertEquals(actualTag1_id, expectedTag1_id);

        /** --------------------PUT-------------------- */

        Category update_category = Category
                .builder()
                .id(11)
                .build();

        UpdateAPet updatePet = UpdateAPet
                .builder()
                .id(178)
                .category(update_category)
                .name("MK")
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
