package api.tdd.pet_store;

import api.pojo_classes.pet_store.Category;
import api.pojo_classes.pet_store.AddAPet;
import api.pojo_classes.pet_store.Tags;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.Arrays;

public class CRUDWithLombok {
    Faker faker = new Faker();

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

        Tags tags = Tags
                .builder()
                .id(45)
                .name("Boss")
                .build();

        AddAPet addPet = AddAPet
                .builder()
                .id(178)
                .category(category)
                .name("MK")
                .photoURLs(Arrays.asList("Monkey's Photo URL"))
                .tags(Arrays.asList(tags))
                .status("available")
                .build();
    }

}
