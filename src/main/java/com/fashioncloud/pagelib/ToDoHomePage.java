package com.fashioncloud.pagelib;

import java.util.List;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.fashioncloud.genericlib.SolventSelenium;

public class ToDoHomePage extends SolventSelenium {

	@FindBy(xpath = "//div[@class='jumbotron text-center']/h1[text() = 'Simple ToDo List ']")
	public WebElement toDoTitle;

	@FindBy(xpath = "//input[@type='text' and @placeholder = 'Get Milk']")
	public WebElement toDoTextBox;

	@FindBy(xpath = "//button[text()= 'Add']")
	public WebElement addButton;

	@FindBy(xpath = "//div[@ng-repeat='todo in todos']/label")
	public List<WebElement> todoItems;

	/**
	 * Initializing Page Factory elements in the constructor 
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	public ToDoHomePage() {
		PageFactory.initElements(driver, this);
	}


	/**
	 * Method to verify the assential element on the Home Page are displayed to user.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	public void verifyToDoHomePage() {
		SoftAssert softAssertion = new SoftAssert();
		softAssertion.assertTrue(toDoTitle.isDisplayed(), "ToDo Title is not available on the To Do Home page");
		softAssertion.assertTrue(toDoTextBox.isDisplayed(), "Text box is not available on the To Do Home page");
		softAssertion.assertTrue(addButton.isDisplayed(), "Add Button is not available on the To Do Home page");
	}

	/**
	 * Page Object method to add To Do item
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	public void addToDoItem(String item) {
		toDoTextBox.sendKeys(item);
		addButton.click();
	}
	
	/**
	 * Page Object method to verify if the To do item is presend or not.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	public boolean verifyItemPresent(String item) {
		boolean isPresent = false;
		for (WebElement todoItem : todoItems) {
			if(todoItem.getText().equalsIgnoreCase(item))
				isPresent = true;
		}
		return isPresent;
	}

	/**
	 * Page Object method to remove To Do Item. 
	 * Catching StaleElementReferenceException as the DOM is getting updated after the remove action.
	 *
	 * @since July 2018
	 * @author pankhurisharma
	 */
	public void removeTodoItem(String item) {
		List <WebElement> items =  todoItems;
		try {
			for (WebElement todoItem : items) {
				String itemName = todoItem.getText().trim();
				if(itemName.equalsIgnoreCase(item))
					todoItem.click();
			}
		}
		catch(StaleElementReferenceException e) {
			e.toString();
			System.out.println("Trying to recover from a stale element :" + e.getMessage());
		}
	}
}
