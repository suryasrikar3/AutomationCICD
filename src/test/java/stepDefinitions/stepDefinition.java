package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.addPlace;
import pojo.location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class stepDefinition extends Utils{

	
	RequestSpecification res;
	ResponseSpecification resspec;
	Response res1;
	TestDataBuild data = new TestDataBuild();
	static String place_id;

	@Given("AddPlace Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		res= given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));
	}

	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
	    // Write code here that turns the phrase above into concrete actions
		//constructor will be called with the value of resource which you pass in the step definition
		APIResources resourceAPI= APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    
		if(method.equalsIgnoreCase("POST"))
		res1= res.when().post(resourceAPI.getResource());
		else if(method.equalsIgnoreCase("GET"))
			res1= res.when().get(resourceAPI.getResource());
				
	}	


@Then("the API call got success with StatusCode {int}")
public void the_api_call_got_success_with_status_code(Integer int1) {
    // Write code here that turns the phrase above into concrete actions
	res1.then().spec(resspec).extract().response();
	assertEquals(res1.getStatusCode(),200);
}
@Then("{string} in the response body is {string}")
public void in_the_response_body_is(String keyValue, String ExpectedValue) {
    // Write code here that turns the phrase above into concrete actions
	
	assertEquals(getJsonPath(res1,keyValue),ExpectedValue);
    
}


@Then("verify place_Id created maps to {string} using {string}")
public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
    // Write code here that turns the phrase above into concrete actions
   place_id =getJsonPath(res1,"place_id");
   res= given().spec(requestSpecification()).queryParam("place_id", place_id);
   user_calls_with_http_request(resource,"GET");
   String actualName =getJsonPath(res1,"name");
   assertEquals(actualName,expectedName);
}	
	

@Given("DeletePlace  Payload")
public void delete_place_payload() throws IOException {
    // Write code here that turns the phrase above into concrete actions
   res=given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
}

}
