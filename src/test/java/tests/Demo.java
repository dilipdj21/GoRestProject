package tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.User;
import utilities.APIEndPoints;
import utilities.TestDataReader;

public class Demo extends BaseTest {

	@Test
	public void getUser() throws JsonMappingException, JsonProcessingException {
		Response response = getAPI(APIEndPoints.USER_ENDPOINT);
		AssertJUnit.assertEquals(response.statusCode(), 200);

		ObjectMapper mapper = new ObjectMapper();
		User[] users = mapper.readValue(response.getBody().asString(), User[].class);
		AssertJUnit.assertEquals(users.length, 10);

	}

	@Test
	public void createUser() throws JsonMappingException, JsonProcessingException {

//		String requestBody="{\n"
//				+ "    \"name\": \"Rep. Anmeyatma Khanna\",\n"
//				+ "    \"email\": \"test"+System.currentTimeMillis()+"123@gmail.com\",\n"
//				+ "    \"gender\": \"male\",\n"
//				+ "    \"status\": \"inactive\"\n"
//				+ "}";

		JSONObject data = (JSONObject) TestDataReader.getData("createUser");
		String email = "test" + System.currentTimeMillis() + "123@gmail.com";
		User user = new User(); // Java object to
		user.setName(data.get("name").toString());
		user.setEmail(email);
		user.setGender(data.get("gender").toString());
		user.setStatus(data.get("status").toString());

		Response response = postAPI(APIEndPoints.USER_ENDPOINT, user);

//		Response response = RestAssured.given().body(user).when().post(APIEndPoints.USER_ENDPOINT).then().log().all()
//				.extract().response();
		AssertJUnit.assertEquals(response.statusCode(), 201);

//		JsonPath jsonpath = response.jsonPath();
//		int id = jsonpath.getInt("id");

		ObjectMapper objectmapper = new ObjectMapper();
		User responseuser = objectmapper.readValue(response.getBody().asString(), User.class); // To JSON String
		System.out.println("email is"+responseuser.getEmail());
		System.out.println("gender is"+responseuser.getGender());
		System.out.println(responseuser.getName());

		AssertJUnit.assertEquals(user.getEmail(), responseuser.getEmail());

//
//		String UpdaterequestBody = "{\n" + "    \"name\": \"Rep. Anmeyatma Khanna\",\n" + "    \"email\": \"test"
//				+ System.currentTimeMillis() + "123@gmail.com\",\n" + "    \"gender\": \"female\",\n"
//				+ "    \"status\": \"inactive\"\n" + "}";
		User updateUser = new User();
		updateUser.setName(data.get("name").toString());
		updateUser.setEmail(email);
		updateUser.setGender(data.get("update_gender").toString());
		updateUser.setStatus(data.get("status").toString());

		Response updateResponse = patchAPI(APIEndPoints.USER_ENDPOINT + "/" + responseuser.getId(), updateUser);
//		Response updateResponse = RestAssured.given().body(updateUser).when()
//				.patch(APIEndPoints.USER_ENDPOINT + "/" + responseuser.getId()).then().log().all().extract().response();
		AssertJUnit.assertEquals(updateResponse.statusCode(), 200);

		User responseUpdateUser = objectmapper.readValue(updateResponse.getBody().asString(), User.class);
		AssertJUnit.assertEquals(updateUser.getGender(), responseUpdateUser.getGender());

	}

	@Test
	public void updateUser() {

	}

}
