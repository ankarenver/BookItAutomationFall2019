package com.bookit.step_definitions;

import com.bookit.pojos.Room;
import com.bookit.utilities.APIUtilities;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.List;


public class APIStepDefinitions {

    private RequestSpecification requestSpecification;
    private Response response;
    private String token;
    private JsonPath jsonPath;
    private ContentType contentType;

    @Given("authorization token is provided for {string}")
    public void authorization_token_is_provided_for(String role) {
        token = APIUtilities.getToken(role);
    }

    @Given("user accepts content type as {string}")
    public void user_accepts_content_type_as(String string) {
        if (string.toLowerCase().contains("json")){
            contentType = ContentType.JSON;
        }else if (string.toLowerCase().contains("xml")){
            contentType = ContentType.XML;
        }else if (string.toLowerCase().contains("html")){
            contentType = ContentType.HTML;
        }
    }

    @When("user sends GET request to {string}")
    public void user_sends_GET_request_to(String path) {
        response = RestAssured.given().accept(contentType).auth().oauth2(token).when().get(path).prettyPeek();
    }

    @Then("user should be able to see {int} rooms")
    public void user_should_be_able_to_see_rooms(Integer expectedNumberOfRooms) {
        List<Object> rooms = response.jsonPath().get();
        Assert.assertEquals((int)expectedNumberOfRooms,rooms.size());
    }

    @Then("user verifies that response status code is {int}")
    public void user_verifies_that_response_status_code_is(Integer expectedStatusCode) {
        Assert.assertEquals((int)expectedStatusCode,response.getStatusCode());
    }

    @Then("user should be able to see all room names")
    public void user_should_be_able_to_see_all_room_names() {
        List<Room> rooms = response.jsonPath().getList("",Room.class);
        rooms.forEach(room -> System.out.println(room.getName()));
    }


    @Then("user payload contains following room names:")
    public void user_payload_contains_following_room_names(List<String> dataTable) {
        List<String> roomNames = response.jsonPath().getList("name");
        Assert.assertTrue(roomNames.containsAll(dataTable));
    }
}
