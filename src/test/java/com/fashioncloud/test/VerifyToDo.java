package com.fashioncloud.test;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import com.fashioncloud.genericlib.SolventSelenium;
import com.fashioncloud.pagelib.ToDoHomePage;

public class VerifyToDo {
	
	private ToDoHomePage todo= null;
	
	/**
	 * This is the setup method to launch the browser and open To Do Home Page.
	 * Initilizing Page Objcets and API utility class
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@BeforeTest
	public void launchBrowser() {
		SolventSelenium.launchBrowser();
	}
	
	/**
	 * This is the data provider to test for multiple to do items.
	 * Data can be provided using XML or excel or other means.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@DataProvider(name = "ToDoItems")
	public static String[] toDoItems() {
		return new String[] { "Electricity Bill", "Mobile bill", "#$%&$*(ˆ%$#" };
	}
	
	/**
	 * Verifying assential elments on the HomePage.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@Test(priority = 1)
	public void verifyToDoHomePage() {
		todo = new ToDoHomePage();
		todo.verifyToDoHomePage();
	}
	
	/**
	 * Verifying Add To Do item functionality.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@Test(priority = 2, dataProvider = "ToDoItems")
	public void verifyAddToDoItem(String item) {
		//String item = "Mobile Bill";
		todo = new ToDoHomePage();
		todo.addToDoItem(item);
		Assert.assertTrue(todo.verifyItemPresent(item), "Item " + item + " is not present. Add Todo action failed.");
	}
	
	/**
	 * Verifying Remove To Do item functionality.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@Test(priority = 3,  dataProvider = "ToDoItems")
	public void verifyRemoveToDoItem(String item) {
		todo = new ToDoHomePage();
		todo.removeTodoItem(item);
		Assert.assertFalse(todo.verifyItemPresent(item), "Item " + item + " is still present. Remove Todo action failed.");
	}
	
	/**
	 * Clean up block to close the browser.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	@AfterTest
	public void quitBrowser() {
		SolventSelenium.driver.quit();
	}

}
