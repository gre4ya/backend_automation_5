package steps.databaseSteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import utils.DBUtil;

import java.math.BigDecimal;

public class DatabaseSteps {
    static String mainQuery;

    @Given("user is able to connect to the database")
    public void user_is_able_to_connect_to_the_database() {
        DBUtil.createDBConnection();

    }

    @When("user send {string} to the database")
    public void user_send_to_the_database(String query) {
        mainQuery = query;
        DBUtil.executeQuery(query);

    }

    @Then("Validate the {int}")
    public void validate_the(Integer expectedSalary) {
        Assert.assertEquals(DBUtil.getCellValue(mainQuery), new BigDecimal(expectedSalary));
    }
}
