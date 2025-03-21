package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		//execute the code when place_id is null
		//write code that will give you the place_id
		stepDefinition m = new stepDefinition();
		
		if(stepDefinition.place_id==null)
		{
		m.add_place_payload_with("surya", "hindi", "Asia");
		m.user_calls_with_http_request("AddPlaceAPI", "POST");
		m.verify_place_id_created_maps_to_using("surya", "getPlaceAPI");
		}
	}
	
}
