package step_definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;


public class CreatePetSteps {


    RequestSpecification request = RestAssured.given();
    Response response;
    JSONObject requestBody = new JSONObject();

    @Given("^I have set up the ability to create products$")
    public void iHaveSetUpTheAbilityToCreateProducts() {
        request.header("Content-Type", "application/json");
    }

    @Then("^I should see the product created with the id \"([^\"]*)\"$")
    public void iShouldSeeTheProductCreatedWithTheId(String id) {

        int statusCode = response.getStatusCode();
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(jsonPathEvaluator.get("id").toString(), id);
    }

    @Given("^I perform POST with (.*) as \"([^\"]*)\"$")
    public void iPerformPOSTWithAs(String name, String value) {
        //request.header("Content-Type","application/json");
        requestBody.put(name, value);
        request.body(requestBody.toJSONString());
        response = request.post("https://petstore.swagger.io/v2/pet");

    }

    @Then("^I should see the product created with the product status \"([^\"]*)\"$")
    public void iShouldSeeTheProductCreatedWithTheProductStatus(String status) {
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(jsonPathEvaluator.get("status").toString(), status);
    }


    @Then("^I should see an error response with status code \"([^\"]*)\"$")
    public void iShouldSeeAnErrorResponseWithStatusCode(int code) {
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, code);

    }

    @And("^I perform POST with all valid data$")
    public void iPerformPOSTWithAllValidData() {

        requestBody.put("id", 22345);
        requestBody.put("name", "doggie food");
        requestBody.put("category.id", 3);
        requestBody.put("category.name", "food");
        requestBody.put("photoUrls[0]", "http://www.drsfostersmith.com/images/Categoryimages/normal/p-22811-97171KZ_001.jpg");
        requestBody.put("photoUrls[1]", "http://www.drsfostersmith.com/images/Categoryimages/normal/p-22811-97171KZ_001.jpg");
        requestBody.put("tags[0].id", 2);
        requestBody.put("tags[0].name", "food");
        requestBody.put("status", "pending");
        request.body(requestBody.toJSONString());
        System.out.println(request.toString());
        response = request.post("https://petstore.swagger.io/v2/pet");
    }

    @Then("^I should see the product created successfully$")
    public void iShouldSeeTheProductCreatedSuccessfully() {
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
