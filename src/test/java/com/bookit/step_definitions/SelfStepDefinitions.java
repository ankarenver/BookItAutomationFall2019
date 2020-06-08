package com.bookit.step_definitions;

import com.bookit.database.UserDB;
import com.bookit.pages.SelfPage;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class SelfStepDefinitions {

    SelfPage selfPage = new SelfPage();
    UserDB userDB = new UserDB();

    /**
     *        | first-name | last-name | email              | password | role                | campus-location | batch-number | team-name      |
     *       | Lesly      | SDET      | jhjk1220@email.com | 1111     | student-team-member | VA              | 15           | Online_Hackers |
     */
    @Then("user verifies that information displays correctly:")
    public void user_verifies_that_information_displays_correctly(List<Map<String, String>> dataTable) {
        for (Map<String, String> row : dataTable){
            System.out.println("###############[UI validations]###############");

            String fullName = row.get("first-name")+" "+row.get("last-name");
            Assert.assertEquals(fullName,selfPage.getUserInfo("name"));
            Assert.assertEquals(row.get("role"),selfPage.getUserInfo("role"));
            Assert.assertEquals(row.get("campus-location"),selfPage.getUserInfo("campus"));
            Assert.assertEquals(row.get("batch-number"),selfPage.getUserInfo("batch").replace("#",""));
            Assert.assertEquals(row.get("team-name"),selfPage.getUserInfo("team"));

            System.out.println("###############[Database validations]###############");
            Assert.assertTrue(userDB.checkIfUserExistInDB(row.get("email")));

        }

    }
}
