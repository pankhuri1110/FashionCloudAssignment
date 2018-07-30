package com.fashioncloud.test;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import com.fashioncloud.genericlib.SolventSelenium;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import java.util.Iterator;
import java.util.List;


public class VerifyToDoAPIs {

	private String Base_URI = SolventSelenium.BASE_URL + "/api/todos";

	private static List ids = null;

	/**
	 * This is the data provider to test for multiple to do items.
	 * Data can be provided using XML or excel or other means.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@DataProvider(name = "ToDoItems")
	public static String[] toDoItems() {
		return new String[] { "Electricity Bill" };
	}

	/**
	 * Verifying API for Adding to do item.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@Test(priority = 1, dataProvider = "ToDoItems")
	public void verifyAddToDoItemAPI(String item) {

		RestAssured.baseURI  = Base_URI; 

		ValidatableResponse res = given().contentType("application/json").
				body("{\"text\":\"" + item + "\"}").
				when().
				post("").then().
				assertThat().
				statusCode(200).
				and().
				contentType(ContentType.JSON).
				and().
				body("text", hasItem(item));

		ids = res.extract().body().jsonPath().getList("_id");
		System.out.println(ids);
	}

	/**
	 * Verifying API for Getting to do item.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@Test(priority = 2, dataProvider = "ToDoItems")
	public void verifyGetToDoItemAPI(String item) {
		RestAssured.baseURI  = Base_URI; 

		given().contentType("application/json").
		when().
		get("").then().
		assertThat().
		statusCode(200).
		and().
		contentType(ContentType.JSON).
		and().
		body(matchesJsonSchemaInClasspath("todo.json")).
		and().
		body("text", hasItem(item));
	}

	/**
	 * 
	 * This clean up method to delete all the items created for testing.
	 *
	 * @since June 2018
	 * @author hemantbanafal
	 */
	@AfterTest
	public void cleanUp() {
		for (Iterator iterator = ids.iterator(); iterator.hasNext();) {
			String id = (String) iterator.next();
			given().contentType("application/json").
			when().
			delete("/" + id).then().
			assertThat().
			statusCode(200);
		}
	}
}