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


    Response response;
    private RequestSpecification request;
    private JSONObject requestBody = new JSONObject();
    private String CreateURI = "https://petstore.swagger.io/v2/pet";

    public CreatePetSteps() {
        request = RestAssured.given();
    }

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
        requestBody.put(name, value);
        request.body(requestBody.toJSONString());
        callTheAPI();

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
        callTheAPI();

    }

    @Then("^I should see the product created successfully$")
    public void iShouldSeeTheProductCreatedSuccessfully() {
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    private void callTheAPI() {
        response = request.post(CreateURI);
    }


    @Given("^I perform POST with invalid category id like \"([^\"]*)\"$")
    public void iPerformPOSTWithInvalidCategoryIdLike(String id) {
        requestBody.put("category.id", id);
        requestBody.put("category.name", "food");
        request.body(requestBody.toJSONString());
        callTheAPI();

    }

    @Given("^I perform POST with invalid tag id like \"([^\"]*)\"$")
    public void iPerformPOSTWithInvalidTagIdLike(String id) {
        requestBody.put("tags[0].id", id);
        requestBody.put("tags[0].name", "food");
        request.body(requestBody.toJSONString());
        callTheAPI();
    }
}
