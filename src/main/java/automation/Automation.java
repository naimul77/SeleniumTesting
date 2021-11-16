package automation;

import java.util.Properties;
import java.util.Scanner;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.edge.EdgeDriver;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Automation {

    private static Properties prop;
    private static String browser;
    private static String website;
    private static WebDriver driver;
    private static int total;

    public static void main(String[] args) {
        String configFilename = "/Users/naimul7/JavaProjects/SeleniumTesting/src/main/java/automation/config.properties";

        /* Initial Condition of WebDriver and Properties instance */
        prop = new Properties();
        driver = null;

        /* Prompt User to Read or Write into Config File for Website Automation Setup / Scripting */
        System.out.println("Do you need to write any properties into the configuration file for website automation? (Y/N)");
        System.out.print("Enter your choice: ");

        /* Write new properties into config file for various websites */
        if(new Scanner(System.in).nextLine().toUpperCase().charAt(0) == 'Y')
            writePropertiesFile(configFilename);

        /* Read Config file for Website Automation */
        configureAutomation(configFilename);

        /* Thank You Message */
        System.out.println("\nThank you for running this program!");
    }

    /* Website Automation */
    public static void configureAutomation(String filename) {
        /* Initialize the Properties instance 'prop' for reading from config.properties file */
        try {
            InputStream input =  new FileInputStream(filename);
            prop.load(input);

            /* Set the data fields for the browser and the number of websites to automates */
            browser = prop.getProperty("browser").toLowerCase();
            total = Integer.parseInt(prop.getProperty("websites"));

            /* Select the corresponding WebDriver for the Test Automation */
            driver = selectDriver();
            if(driver == null)
                System.out.println("Sorry! " + browser + " is not one of the supported browsers for this website automation program!\n");

            /* Search specific website in a specific browser for a specific item */
            search("Pokemon Brilliant Diamond",true);
            input.close();
        } catch(FileNotFoundException fnfex) { fnfex.printStackTrace();
        } catch(IOException ioex) { ioex.printStackTrace();
        } finally { driver.close(); }
    }

    /* Returns to create the desired WebDriver according to the Browser of Choice */
    public static WebDriver selectDriver() {
        System.setProperty("webdriver." + browser + ".driver", "/Users/naimul7/Downloads/" + browser + "driver");
        switch(browser) {
            case "ie": return new InternetExplorerDriver();
            case "edge": return new EdgeDriver();
            case "chrome": return new ChromeDriver();
            case "gecko": return new FirefoxDriver();
            case "safari": return new SafariDriver();
            default: return null;
        }
    }

    /* Searches All Supported Websites for the specified Keyword through Automation */
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

    /* Search Functionality for Various Websites: Current Support for: eBay, GameStop, BestBuy, Amazon, Target */
    public static void search(String web, String key, int webIndex) {
        /* Unexpected Modal Handling UNSUCCESSFUL: Handle Sign-Up Modal for BestBuy during automated test run */
        if(web.equals("bestbuy")) {
            /*
                For ease of debugging the issue,
                I have configured my data to run the website automation
                for BestBuy as the last website in the script
            */
            /* I have tried WebDriverWait with ExpectedConditions as well as Clicking on a Static Area in the Webpage outside of the Modal */
            /*
                Requesting for an Effective Solution that is Applicable to all Websites:
                A modal appears in the screen prompting to sign in or sign up
            */
        }

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

    /*
        Plan for future references:
        ease of scaling
        automation capability
        of various websites
        under a single framework
    */
    public static void writePropertiesFile(String filename) {
        try {
            OutputStream output = new FileOutputStream(filename);
            /* write into the config.properties file using the following format for any number of properties */
            /* prop.setProperty("key", "value"); */
            output.close();
        } catch(FileNotFoundException fnfex) { fnfex.printStackTrace();
        } catch(IOException ioex) { ioex.printStackTrace();
        } finally { System.out.println("Writing properties into config.properties file was successful. "); }
    }
}
