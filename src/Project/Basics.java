package Project;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import static io.restassured.RestAssured.*;

public class Basics {
	
	//given when then
	
	public static void main(String[] args) {
		RestAssured.baseURI="https://rahulshettyacademy.com";
		//post - POST
		String response = given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200).
		header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		//System.out.println(response);
		JsonPath jp = reusableMethods.rawToJson(response);
		String placeid = jp.getString("place_id");
		System.out.println("Place id : "+ jp.getString("place_id"));
		
		//update  --- PUT 
		String address = "Infor";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").
		body("{\r\n" + 
				"\"place_id\":\""+placeid+"\",\r\n" + 
				"\"address\":\""+address+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}").when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200).
		body("msg",equalTo("Address successfully updated"));
		
		//get - GET
		String getresponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid)
		.when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jp1 = reusableMethods.rawToJson(getresponse);
		String address2 = jp1.getString("address");
		System.out.println("address : "+ address2);
		//ASSERT - we need testng
		Assert.assertEquals(address2, address);
		System.out.println("Adress has been updated successfulyy");
		
	}

}
