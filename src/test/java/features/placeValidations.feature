Feature: Validating AddPlace API's

@AddPlace @Regression
Scenario Outline: Verify if place is being succesfully added using AddPlaceAPI
 Given AddPlace Payload with "<name>" "<language>" "<address>"
 When user calls "AddPlaceAPI" with "POST" http request
 Then the API call got success with StatusCode 200
 And  "status" in the response body is "OK"
 And  "scope" in the response body is "APP"
 And verify place_Id created maps to "<name>" using "getPlaceAPI"
 
 
 Examples: 
 |	name	  | language	|	address	           |
 |	AAHouse | English   | World Cross Center |
 #|  BBHouse	| Spanish		| Coppell texas			 |
 
 @DeletePlace @Regresssion
 Scenario: verify if Delete place functionality is working 
 Given DeletePlace  Payload
  When user calls "deletePlaceAPI" with "POST" http request
 Then the API call got success with StatusCode 200
 And  "status" in the response body is "OK"