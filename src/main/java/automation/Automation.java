package automation;

import java.util.Properties;
import java.util.Scanner;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Automation {

    private static Properties prop;
    private static String browser;
    private static String website;
    private static WebDriver driver;
    private static int total;

    public static void main(String[] args) {
        String configFilename = "/Users/naimul7/JavaProjects/SeleniumTesting/src/main/java/automation/config.properties";

        /* Initial Condition of WebDriver and Properties pointer */
        prop = new Properties();
        driver = null;

        /* Prompt User to Read or Write into Config File for Website Automation Setup / Scripting */
        System.out.println("Do you need to write any properties into the configuration file for website automation? (Y/N)");
        System.out.print("Enter your choice: ");

        /* Write new properties into config file for various websites */
        if(new Scanner(System.in).nextLine().toUpperCase().charAt(0) == 'Y')
            writePropertiesFile(configFilename);

        /* Read Config file for Website Automation */
        readPropertiesFile(configFilename);

        /* Search specific website in a specific browser for a specific item */
        search("Pokemon Brilliant Diamond",true);

        System.out.println("Thank you for running this program!");
    }

    public static void readPropertiesFile(String filename) {

        try {
            InputStream input =  new FileInputStream(filename);
            prop.load(input);
        } catch(FileNotFoundException fnfex) { fnfex.printStackTrace();
        } catch(IOException ioex) { ioex.printStackTrace(); }

        browser = prop.getProperty("browser").toLowerCase();
        total = Integer.parseInt(prop.getProperty("websites"));

        System.setProperty("webdriver." + browser + ".driver", "/Users/naimul7/Downloads/" + browser + "driver");

        switch(browser) {
            case "ie":
                driver = new InternetExplorerDriver();
                break ;

            case "edge":
                driver = new EdgeDriver();
                break;

            case "chrome":
                driver = new ChromeDriver();
                break;

            case "gecko":
                driver = new FirefoxDriver();
                break;

            case "safari":
                driver = new SafariDriver();
                break;

            default: System.out.println("Sorry! " + browser + " is not one of the supported browsers for this website automation program!\n");
        }
    }

    public static void search(String key, boolean all) {

        int i = 1;
        do {
            website = prop.getProperty("website" + Integer.toString(i));

            driver.get(website);

            search(website.substring(12, website.length() - 4), key, i);

            if(i++ == 5)
                all = false;

        } while(all);
    }

    public static void search(String web, String key, int webIndex) {
        /* Handle Sign-Up Popup for BestBuy website */
        if(web.equals("bestbuy"))
            driver.findElement(By.xpath("//*[@id=\"widgets-view-email-modal-mount\"]/div/div/div[1]/div/div/div/div/button/svg"));

        /* Enter keyword into the search field */
        if(web.equals("gamestop"))      /* Game Stop Search Field is located using Xpath */
            driver.findElement(By.xpath(prop.getProperty(web + "searchfield"))).sendKeys(key);
        else                /* All other websites have saerch field located by id */
            driver.findElement(By.id(prop.getProperty(web + "searchfield"))).sendKeys(key);

        if(webIndex > 2)    /* Websites with index above 2 have search button web element located by xpath */
            driver.findElement(By.xpath(prop.getProperty(web + "searchbutton"))).click();
        else            /* Websites with index 2 or below have search button web element located by id */
            driver.findElement(By.id(prop.getProperty(web + "searchbutton"))).click();
    }

    public static void writePropertiesFile(String filename) {
        try {
            OutputStream output = new FileOutputStream(filename);

            prop.setProperty("BestBuySearchField", "gh-search-input");
            prop.setProperty("BestBuySearchButton", "//*[@id=\"shop-header-954da8b6-0ec4-43d4-bc0d-db220aa01223\"]/div/div[1]/header/div[1]/div/div[1]/div/form/button[2]/span/svg");
            prop.setProperty("EbaySearchButton", "gh-btn");
            prop.setProperty("EbaySearchField", "gh-ac");
            prop.setProperty("AmazonSearchField", "twotabsearchtextbox");
            prop.setProperty("AmazonSearchButton", "nav-search-submit-button");

            prop.store(output, null);

            output.close();
        } catch(FileNotFoundException fnfex) { fnfex.printStackTrace();
        } catch(IOException ioex) { ioex.printStackTrace(); }

        System.out.println("Thank you for writing to the config file. ");
    }
}
