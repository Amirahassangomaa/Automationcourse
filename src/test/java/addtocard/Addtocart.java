package addtocard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Addtocart {

    WebDriver driver ;
    String url = "https://www.saucedemo.com/inventory.html";

    //locators for loginToWebsite
    By username=  By.id("user-name");
    String userName ="standard_user";
    By password=  By.id("password");
    String passWord ="secret_sauce";
    By button = By.id("login-button");

    //locators for test that checkIfTheItemAddedToCart
    By carticon = By.xpath("//a[@class=\"shopping_cart_link\"]");
    By itemNameExist = By.xpath("//*[@class=\"inventory_item_name\"]");
   // By itemPriceExist= By.xpath("//*[@class=\"inventory_item_price\"]/text()[2]");
    By itemPriceExist = By.xpath("//*[@class=\"inventory_item_price\"]");

    @BeforeTest
    public void openUrl ()
    {
        driver =new ChromeDriver();
        driver.get(url);
    }

    public void loginToWebsite ()
    {
        driver.findElement(username).sendKeys(userName);
        driver.findElement(password).sendKeys(passWord);
        driver.findElement(button).click();
    }

    @Test
    public void additemtoCart() {

        loginToWebsite();
        driver.findElement(By.xpath(addItemNameToCart("Sauce Labs Backpack"))).click();
    }

    public String addItemNameToCart(String itemName) {
        String addtocartbuttonXpath = String.format("//div/a/div[text()=\"%s\"]/../../following-sibling::div[1]/button[@class=\"btn btn_primary btn_small btn_inventory \"]", itemName);
        return addtocartbuttonXpath;
    }

    @Test
    public void checkIfTheItemAddedToCart()
    {

        driver.findElement(carticon).click();
        driver.findElement(itemNameExist).getSize();

    }

    @Test
    public void checkIfTheItemHasTheSameNameAndPrice() {

        WebElement itemNameElement = driver.findElement(itemNameExist);
        String itemNameInCart = itemNameElement.getText();
        WebElement itemPriceElement = driver.findElement(itemPriceExist);
        String itemPriceInCart = itemPriceElement.getText();

        // Assert that the item name and price are the same as expected
        Assert.assertEquals(itemNameInCart, "Sauce Labs Backpack", "Item name in cart does not match expected");
        Assert.assertEquals(itemPriceInCart, "$29.99", "Item price in cart does not match expected");
    }

}
