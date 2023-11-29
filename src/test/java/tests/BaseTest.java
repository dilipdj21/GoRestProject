package tests;

import java.util.Base64;

import org.testng.annotations.BeforeTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.APIEndPoints;
import utilities.EnvPropertiesReader;
import utilities.TestConstants;

public class BaseTest extends EnvPropertiesReader {

	@BeforeTest
	public void setup() {
		loadPropertiesFile();
		RestAssured.baseURI = properties.getProperty("url");
		byte[] decodedBytes = Base64.getDecoder().decode(properties.getProperty("token"));
		String decodedString = new String(decodedBytes);
		RestAssured.requestSpecification = RestAssured.given().header("Accept", TestConstants.ACCEPT)
				.header("Content-Type", TestConstants.CONTENT_TYPE).header("Authorization", "Bearer " + decodedString)
				.log().all();

	}

	public Response getAPI(String endpoint) {
		Response response = RestAssured.given().when().get(endpoint).then().log().all().extract().response();
		return response;
	}
	
	public Response postAPI(String endpoint,Object payload) {
		Response response = RestAssured.given().body(payload).when().post(endpoint).then().log().all()
		.extract().response();
		return response;
		
	}
	
	public Response patchAPI(String endpoint,Object payload) {
		Response response = RestAssured.given().body(payload).when().patch(endpoint).then().log().all()
		.extract().response();
		return response;
		
	}

}
