package apitest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;


import io.restassured.RestAssured;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;


import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class APITest {

	private static String accessToken;
	private static String createdOrderId;
	
	@BeforeAll
	public static void setup() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		LocalDateTime currentDate = LocalDateTime.now();
		String formattedDateTime = currentDate.format(formatter);
		
		String name = "Nada" + formattedDateTime;
		
		RestAssured.baseURI = "https://simple-books-api.glitch.me";
		System.out.println(name);
		
	    String requestBody = "{\n" + "  \"clientName\": \"" + name + "\",\n" + "  \"clientEmail\": \"" + name
				+ "@example.com\"\n" + "}";
	    
	    System.out.println(requestBody);
				
		Response response = given().header("Content-Type", "application/json").body(requestBody).when()
				.post("/api-clients/").then().log().all().extract().response();
		
		int statusCode = response.getStatusCode();
		
		if (statusCode == 201) {
			// Success: Extract and store the access token
			accessToken = response.jsonPath().getString("accessToken");
			System.out.println("Access token received: " + accessToken);
		} else if (statusCode == 409) {
			System.out.println("Client already registered. Please use a different email and name.");
		} else if (statusCode == 404) {
			System.out.println("The endpoint was not found. Please check the URL.");
		} else {
			System.out.println("Unexpected status code: " + statusCode);
		}
	}
	
	@Test
	@Order(1)
	public void getAllBooks() {
		Response response = given().auth().oauth2(accessToken) // Bearer Token Authentication
				.when().get("/books").then().statusCode(200).extract().response();

		System.out.println("Response: " + response.asString());

	}
	
	@Test
	@Order(2)
	public void postOrder() {
		String requestBody = "{\n" + "  \"bookId\": 1,\n" + "  \"customerName\": \"John Doe\"\n" + "}";

		Response response = given().auth().oauth2(accessToken).header("Content-Type", "application/json")
				.body(requestBody).when().post("/orders").then().statusCode(201).body("created", equalTo(true)).extract().response();
		
		createdOrderId = response.jsonPath().getString("orderId");

	}
	
	@Test
	@Order(3)
	public void getAllOrders() {
		Response response = given().auth().oauth2(accessToken).when().get("/orders").then().statusCode(200).extract()
				.response();
		System.out.println("getallresponse" + response.asString());

	}

	@Test
	@Order(4)
	public void patchOrder() {
		String requestBody = "{\n" + "  \"customerName\": \"Jane test\"\n" + "}";

		Response response = given().auth().oauth2(accessToken).header("Content-Type", "application/json")
				.body(requestBody).when().patch("/orders/" + createdOrderId).then().statusCode(204).extract()
				.response();

		System.out.println(response.getStatusCode());
	}

	@Test
	@Order(5)
	public void deleteOrder() {

		 given()
         .header("Authorization", "Bearer " + accessToken)
     .when()
         .delete("/orders/" + createdOrderId)
     .then()
         .statusCode(204); 
		 
     // Verify that the order is deleted using GET
     given()
         .header("Authorization", "Bearer " + accessToken)
     .when()
         .get("/orders/" + createdOrderId)
     .then()
         .statusCode(404); 

	}

	
}
