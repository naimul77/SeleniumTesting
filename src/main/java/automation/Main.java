package automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the Automation Testing Program for Beginners!\n");

        System.out.println("What would you like to do? ");
        System.out.println("Select one: \n\t(1) Search website for specific keyword \n\t(2) Select category and search");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();

        System.out.println("\nWhich browser would you like to use for the autmation? ");
        System.out.println("Select a browser: (1) Internet Explorer (2) Microsoft Edge (3) Google Chrome (4) Mozilla Firefox (5) Safari");
        System.out.print("Enter your choice: ");
        int browserIndex = input.nextInt();

        /* Clear input buffer */
        input.nextLine();

        System.out.println("\nWhich website would you like to perform your automation in? (Input Format: www.example.com)");
        System.out.print("Write the website url: ");
        String website = input.nextLine();

        String browser = "";
        switch(browserIndex) {
            case 1:
                browser = "ie";
                break;

            case 2:
                browser = "edge";
                break;

            case 3:
                browser = "chrome";
                break;

            case 4:
                browser = "gecko";
                break;

            case 5:
                browser = "safari";
                break;

            default:
                System.out.println("Sorry! The choice you have made is invalid. ");
        }

        System.out.print("\nWould you like to select a search category? (Y/N): ");
        char ch = input.nextLine().charAt(0);

        String category = "";
        if(ch == 'y' || ch == 'Y') {
            System.out.print("\nEnter the search category: ");
            category = input.nextLine();
        }

        System.out.println("\nWhat keyword you wish to search for?");
        System.out.print("Enter the keyword: ");
        String keyword = input.nextLine();

        System.out.println();

        WebDriver driver = search(browser, "https://" + website, keyword, category);

        System.out.println("\nThank you for running this program!");
    }

    public static WebDriver search(String browser, String website, String keyword, String category) {
        System.setProperty("webdriver." + browser + ".driver", "/Users/naimul7/Downloads/" + browser + "driver");

        WebDriver driver = null;
        switch(browser.toLowerCase()) {
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
        }

        /* go to specified website */
        driver.get(website);

        /* Select category if specified */
        if(!category.equals(""))
            driver.findElement(By.id("gh-cat")).sendKeys(category);

        /* Search for specified 'keyword' in ebay.com */
        driver.findElement(By.id("gh-ac")).sendKeys(keyword);

        /* Click 'search' button to invoke search in ebay.com */
        driver.findElement(By.id("gh-btn")).click();

        return driver;
    }
}
