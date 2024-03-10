package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/** This is the code for the test case where customer wants to buy laptop on the flipkart. So the steps are
 * searching for product
 * then adding it to cart, proceed for login and placing order, putting delivery address, in the last chose
 * the payment in which want to make payment.*/
public class ShopNow {

    WebDriver driver;
    public void butProduct(String browser) throws InterruptedException {
        if(browser.equalsIgnoreCase("Chrome")){
            WebDriverManager.chromedriver().clearDriverCache().setup();
            driver=new ChromeDriver();
        }else if(browser.equalsIgnoreCase("Firefox")){
            WebDriverManager.firefoxdriver().clearDriverCache().setup();
            driver=new FirefoxDriver();
        } else if(browser.equalsIgnoreCase("Safari")) {
            WebDriverManager.safaridriver().clearDriverCache().setup();
            driver=new SafariDriver();
        } else if (browser.equalsIgnoreCase("Internet Explore")) {
            WebDriverManager.iedriver().clearDriverCache().setup();
            driver=new InternetExplorerDriver();
        } else if (browser.equalsIgnoreCase("Edge")) {
            WebDriverManager.edgedriver().clearDriverCache().setup();
            driver=new EdgeDriver();
        }
        /** wait for 2 second to complete previous actions*/
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));
        String url="https://www.flipkart.com/";
        /** Test case-1 : Open Flipkart Website and verify it  */
        driver.get(url);
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        /** Verifying whether page is loaded successfully or not */
        Boolean ispageloaded= js.executeScript("return document.readyState").toString().equals("complete");
        String curl=driver.getCurrentUrl();
        WebElement searchbox;

        /** Test case-2 : Search Product and Add it to Cart:
        * if page loaded successfully then go for next of searching item */
        assert ispageloaded.equals(true):"Page loaded successfully";
       /* if(ispageloaded){
            if (curl.equals(url)){
                System.out.println("Page loaded successfully");
            }
        }else {
            driver.get(url);
        }

        */
            /** seraching laptop keyword in serach box on page */
            searchbox=driver.findElement(By.name("q"));
            searchbox.sendKeys("laptop");
            searchbox.sendKeys(Keys.ENTER);

            /** Opening product by clicking on it */
            WebElement product1=driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[2]/div[2]/div/div/div/a/div[2]/div[1]/div[2]"));
            product1.click();
            System.out.println("product name = "+product1.getText());

            /** Switching to new window which is opened after clicking on the product */
            driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());

            /** adding product to cart */
            driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[1]/div[2]/div/ul/li[1]/button")).click();

            /** Test case-3 Go to cart and verify the product and Proceed to Checkout: */
            String url2;
            url2=driver.getCurrentUrl();
            if (url2.contains("viewcart")){
                System.out.println("view cart page is loaded");
                System.out.println(driver.getCurrentUrl());
            }else{
                driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div[1]/div[2]/div[6]/div/div/a")).click();
            }


        /** getting cart product element */

//            WebElement cartProduct= driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div/div[1]/div/div[2]/div/div[1]/div[1]/div[1]/a"));
//            if(product1.equals(cartProduct.getText())){
//                System.out.println("product in cart is verified successfully");
//            }else{
//                System.out.println("cart product is mismatch ");
//            }
            TimeUnit.SECONDS.sleep(2);
            ispageloaded= js.executeScript("return document.readyState").toString().equals("complete");

            /** To click on Place order button to proceed further*/
            if(ispageloaded)
            driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div/div[1]/div/div[3]/div/form/button")).click();

            /** Test case-4 : User Authentication */
            /** Entering username
             ***** Here user credential are dummy so login process will not be completed
             * In flipkart I am not able to login as it required new OTP every time as it receives on registered
             * email so upto login page it will work */
            WebElement logintext = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div[1]/div[1]/div/div/div/div/div[1]/div/form/div[1]/input"));
            logintext.sendKeys("xyz@gmail.com");
            logintext.sendKeys(Keys.ENTER);

            WebElement pass=driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div[1]/div[1]/div/div/div/div/div[1]/div/form/div[2]/input"));
            pass.sendKeys("123456");

            /** Click on login button after entering login details*/
            driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div[1]/div[1]/div/div/div/div/div[1]/div/form/div[4]/button")).click();

            /** Verifying successful login */
           assert driver.findElement(By.partialLinkText("Deliver")).isDisplayed():"view cart page showed";

            /** filling Shipping Information Enter valid shipping information (address, city, state, and zip
             code) */
            driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div[1]/div[2]/div/div/div/div[3]/div[2]")).click();
            WebElement name=driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div[1]/div[2]/div/div/div/div[3]/div[2]/label/div[2]/div/form/div/div[2]/div[1]/input"));
            name.sendKeys("John Carter");
            WebElement number=driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div[1]/div[2]/div/div/div/div[3]/div[2]/label/div[2]/div/form/div/div[2]/div[2]/input"));
            number.sendKeys("9755218513");
            WebElement pincode=driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div[1]/div[2]/div/div/div/div[3]/div[2]/label/div[2]/div/form/div/div[3]/div[1]/input"));
            pincode.sendKeys("400092");
            WebElement locality=driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div[1]/div[2]/div/div/div/div[3]/div[2]/label/div[2]/div/form/div/div[3]/div[2]/input"));
            locality.sendKeys("Mumbai");
            WebElement addr=driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div[1]/div[2]/div/div/div/div[3]/div[2]/label/div[2]/div/form/div/div[4]/div/div[1]/textarea"));
            addr.sendKeys("D-404 City Tower airport road Boravali west Mumbai");
            WebElement city=driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div[1]/div[2]/div/div/div/div[3]/div[2]/label/div[2]/div/form/div/div[5]/div[1]/div[1]/input"));
            city.sendKeys("Mumbai");
            WebElement state=driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div[1]/div[2]/div/div/div/div[3]/div[2]/label/div[2]/div/form/div/div[5]/div[2]/div/div[2]/select"));
            Select select=new Select(state);
            select.selectByValue("Maharashtra");

            WebElement submitdeliver=driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div[1]/div[2]/div/div/div/div[1]/label[1]/div[2]/div/form/div/div[8]/button[1]"));
            submitdeliver.click();

            WebElement contnue=driver.findElement(By.xpath("//*[@id=\"to-payment\"]/button"));
            contnue.click();
            /** Review Order:*/
            WebElement accept=driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[1]/div/div/button"));
            accept.click();
            /** Choose a payment method */
            WebElement paymentoption=driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div[1]/div[4]/div/div/div[2]/div/label[6]/div[2]/div/div/div[2]"));
            paymentoption.click();

        driver.close();
        driver.quit();
    }
}
