package com.team25.event.planner.tests;

import com.team25.event.planner.base.BaseTest;
import com.team25.event.planner.constants.Constants;
import com.team25.event.planner.pages.EventDetailsPage;
import com.team25.event.planner.pages.HomePage;
import com.team25.event.planner.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalTime;
import java.util.*;

import static org.testng.Assert.*;

public class FilterEventsTest extends BaseTest {

    @BeforeClass
    public void start(){
        driver.get(Constants.HOME_PAGE_URL);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
    }

    public void login(String email, String password) {
        driver.get(Constants.LOGIN_URL);
        LoginPage loginPage = new LoginPage(driver);
        waitForElementToBePresent(By.cssSelector("input[formControlName='email']"));
        loginPage.login(email, password);
    }

    public void logout(){
        driver.get(Constants.HOME_PAGE_URL);
        HomePage homePage = new HomePage(driver);
        homePage.logout();
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
    }

    @Test
    public void testPageScrollingValid(){
        HomePage homePage = new HomePage(driver);
        driver.navigate().refresh();
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();

        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

        homePage.nextPage();
        homePage.nextPage();
        homePage.nextPage();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();

        assertEquals(currentPage, 2);
        assertEquals(totalPages, 2);

        homePage.previousPage();
        homePage.previousPage();
        homePage.previousPage();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();

        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

    }

    @Test
    public void testDisplayAllEventsValid(){
        HomePage homePage = new HomePage(driver);
        driver.navigate().refresh();
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        homePage.nextPage();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 2);
        assertEquals(totalPages, 2);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 6);

        homePage.previousPage();
    }

    @Test
    public void testLoadPageWhenUserIsUnauthenticatedValid(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();

        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        homePage.nextPage();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();

        assertEquals(currentPage, 2);
        assertEquals(totalPages, 2);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 6);

    }

    @Test
    public void testLoadPageWhenUserIsRegularValid() {
        login("peric@petar.com", "password1");
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));


        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();

        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        homePage.nextPage();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();

        assertEquals(currentPage, 2);
        assertEquals(totalPages, 2);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 6);

        logout();
    }

    @Test
    public void testLoadPageWhenUserIsAdminValid(){
        login("mika@mikic.com", "password1");
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();

        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        homePage.nextPage();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();

        assertEquals(currentPage, 2);
        assertEquals(totalPages, 2);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 6);

        logout();
    }

    @Test
    public void testLoadPageWhenUserIsEventOrganizerValid(){
        login("account1@example.com", "password1");
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();

        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        homePage.nextPage();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();

        assertEquals(currentPage, 2);
        assertEquals(totalPages, 2);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 6);

        logout();
    }

    @Test
    public void testLoadPageWhenUserIsOwnerValid(){
        login("marko.p@example.com", "password1");
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();

        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        homePage.nextPage();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();

        assertEquals(currentPage, 2);
        assertEquals(totalPages, 2);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 6);

        logout();
    }

    public EventDetailsPage openEventDetailsPage(WebElement eventCard){
        WebElement detailsButton = eventCard.findElement(By.cssSelector("a[mat-raised-button]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", detailsButton);

        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).click(detailsButton).keyUp(Keys.CONTROL).perform();

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        EventDetailsPage eventDetailsPage = new EventDetailsPage(driver);
        waitForElementToBeVisible(By.cssSelector(".title-section mat-card-title"));
        return eventDetailsPage;
    }
    @Test
    public void testFilterEventsByName(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

        String eventName = "Conference";
        homePage.enterName(eventName);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 4);

        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getEventName().toLowerCase().contains(eventName.toLowerCase()));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();

        eventName = "Co";
        homePage.enterName(eventName);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 8);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getEventName().toLowerCase().contains(eventName.toLowerCase()));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();

        homePage.enterName("");
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);
        homePage.nextPage();
        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 2);
        assertEquals(totalPages, 2);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 6);

        homePage.refreshFilter();
        homePage.search();
    }

    @Test
    public void testFilterEventsByNameNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

        homePage.enterName("Conference 2024");
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.search();
    }

    @Test
    public void testFilterEventsByDescription(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));

        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

        String eventDescription = "Conference";
        homePage.enterDescription(eventDescription);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 1);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getDescription().toLowerCase().contains(eventDescription.toLowerCase()));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();

        eventDescription = "tech";
        homePage.enterDescription(eventDescription);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 3);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getDescription().toLowerCase().contains(eventDescription.toLowerCase()));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();

        homePage.enterName("");
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);
        homePage.nextPage();
        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 2);
        assertEquals(totalPages, 2);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 6);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByDescriptionNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

        homePage.enterDescription("Conference 2024");
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterByMaximumParticipants(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));

        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

        int maxParticipants = 100;
        homePage.enterMaxParticipantsNumber(maxParticipants);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 4);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getNumParticipants() <= maxParticipants);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }
        homePage.refreshFilter();

        maxParticipants = 299;
        homePage.enterMaxParticipantsNumber(maxParticipants);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 6);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getNumParticipants() <= maxParticipants);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();

        homePage.enterMaxParticipantsNumber(5000);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);
        homePage.nextPage();
        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 2);
        assertEquals(totalPages, 2);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 6);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterByMaximumParticipantsNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

        homePage.enterMaxParticipantsNumber(0);
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByCountry(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

        String country = "Serbia";
        homePage.enterCountry(country);
        homePage.search();

        waitForElementToBePresent(By.xpath("//app-home-all-events//app-home-event-card"));
        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getPlace().contains(country));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.nextPage();

        waitForElementToBePresent(By.xpath("//app-home-all-events//app-home-event-card"));
        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 2);
        assertEquals(totalPages, 2);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 6);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getPlace().contains(country));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }


        homePage.refreshFilter();

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByCountryNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

        homePage.enterCountry("USA");
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByCity(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);

        String city = "Novi Sad";
        homePage.enterCity(city);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 4);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getPlace().contains(city));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByCityNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        homePage.enterCity("Uzice");
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByEventType(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventType = "Conference";
        homePage.enterEventType(eventType);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 4);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getEventType().toLowerCase().contains(eventType.toLowerCase()));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        eventType = "Workshop";
        homePage.enterEventType(eventType);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 4);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getEventType().toLowerCase().contains(eventType.toLowerCase()));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByEventTypeNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        homePage.enterEventType("Wedding");
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByStartDate(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.DECEMBER, 25, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        homePage.enterStartDate(date);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 2);

        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertEquals(eventDetailsPage.getStartDate(), date);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();

        calendar.set(2026, Calendar.DECEMBER, 20);
        date = calendar.getTime();
        homePage.enterStartDate(date);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 2);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertEquals(eventDetailsPage.getStartDate(), date);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByStartDateNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2027, Calendar.JANUARY, 1);
        Date date = calendar.getTime();
        homePage.enterStartDate(date);
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByEndDate(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.DECEMBER, 20,0,0,0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        homePage.enterEndDate(date);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 2);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertEquals(eventDetailsPage.getEndDate(), date);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();

        calendar.set(2026, Calendar.DECEMBER, 18,0,0,0);
        date = calendar.getTime();
        homePage.enterStartDate(date);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 2);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertEquals(eventDetailsPage.getEndDate(), date);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByEndDateNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2027, Calendar.JANUARY, 1);
        Date date = calendar.getTime();
        homePage.enterEndDate(date);
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByStartDateAndTime(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.DECEMBER, 25, 9, 0,0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        homePage.enterStartDate(date);
        homePage.enterStartTime(LocalTime.of(9,0));
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 2);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertEquals(eventDetailsPage.getStartDateAndTime(), date);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }


        homePage.refreshFilter();

        calendar.set(2026, Calendar.DECEMBER, 20, 16, 0,0);
        date = calendar.getTime();
        homePage.enterStartDate(date);
        homePage.enterStartTime(LocalTime.of(16,0));
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 1);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertEquals(eventDetailsPage.getStartDateAndTime(), date);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByStartDateAndTimeNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2027, Calendar.JANUARY, 1);
        Date date = calendar.getTime();
        homePage.enterStartDate(date);
        homePage.enterStartTime(LocalTime.of(19,0));
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByEndDateAndTime(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.DECEMBER, 25, 13,30,0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        homePage.enterEndDate(date);
        homePage.enterEndTime(LocalTime.of(13,30));
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 1);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertEquals(eventDetailsPage.getEndDateAndTime(), date);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();

        calendar.set(2026, Calendar.DECEMBER, 20, 15,0);
        date = calendar.getTime();
        homePage.enterEndDate(date);
        homePage.enterEndTime(LocalTime.of(15,0));
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 1);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertEquals(eventDetailsPage.getEndDateAndTime(), date);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByEndDateAndTimeNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2027, Calendar.JANUARY, 1);
        Date date = calendar.getTime();
        homePage.enterEndDate(date);
        homePage.enterEndTime(LocalTime.of(19,0));
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByStartDateAndTimeAndEndDateAndTime(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.DECEMBER, 25, 9,0,0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDateTime = calendar.getTime();
        homePage.enterStartDate(startDateTime);
        homePage.enterStartTime(LocalTime.of(9,0));
        calendar.set(2026, Calendar.DECEMBER, 25, 12,30,0);
        Date endDateTime = calendar.getTime();
        homePage.enterEndDate(endDateTime);
        homePage.enterEndTime(LocalTime.of(12,30));
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 1);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertEquals(eventDetailsPage.getStartDateAndTime(), startDateTime);
            assertEquals(eventDetailsPage.getEndDateAndTime(), endDateTime);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();

        calendar.set(2026, Calendar.DECEMBER, 20,16,0,0);
        startDateTime = calendar.getTime();
        homePage.enterStartDate(startDateTime);
        homePage.enterStartTime(LocalTime.of(16,0));
        calendar.set(2026, Calendar.DECEMBER, 20,20,0,0);
        endDateTime = calendar.getTime();
        homePage.enterEndDate(endDateTime);
        homePage.enterEndTime(LocalTime.of(20,0));
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 1);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertEquals(eventDetailsPage.getStartDateAndTime(), startDateTime);
            assertEquals(eventDetailsPage.getEndDateAndTime(), endDateTime);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByStartDateAndTimeAndEndDateAndTimeNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.JANUARY, 1);
        Date date = calendar.getTime();
        homePage.enterStartDate(date);
        homePage.enterStartTime(LocalTime.of(1,0));
        homePage.enterEndDate(date);
        homePage.enterEndTime(LocalTime.of(11,0));
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByNameAndDescription(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventName = "work";
        String eventDescription = "leader";

        homePage.enterName(eventName);
        homePage.enterDescription(eventDescription);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 1);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getEventName().toLowerCase().contains(eventName.toLowerCase()));
            assertTrue(eventDetailsPage.getDescription().toLowerCase().contains(eventDescription.toLowerCase()));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByNameAndDescriptionNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventName = "money";
        String eventDescription = "leader";

        homePage.enterName(eventName);
        homePage.enterDescription(eventDescription);
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByNameAndCity(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventName = "match";
        String eventCity = "Niš";

        homePage.enterName(eventName);
        homePage.enterCity(eventCity);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 2);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getEventName().toLowerCase().contains(eventName.toLowerCase()));
            assertTrue(eventDetailsPage.getPlace().contains(eventCity));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByNameAndCityNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventName = "match";
        String eventCity = "Uzice";

        homePage.enterName(eventName);
        homePage.enterCity(eventCity);
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByCountryAndCity(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventCountry = "Serbia";
        String eventCity = "Novi Sad";

        homePage.enterCountry(eventCountry);
        homePage.enterCity(eventCity);
        homePage.search();

        waitForElementToBePresent(By.xpath("//app-home-all-events//app-home-event-card"));


        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 4);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getPlace().contains(eventCountry));
            assertTrue(eventDetailsPage.getPlace().contains(eventCity));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByCountryAndCityNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventCountry = "Serbia";
        String eventCity = "Uzice";

        homePage.enterCountry(eventCountry);
        homePage.enterCity(eventCity);
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByEventTypeAndCountryAndCity(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventType = "Concert";
        String eventCountry = "Serbia";
        String eventCity = "Belgrade";

        homePage.enterCountry(eventCountry);
        homePage.enterCity(eventCity);
        homePage.enterEventType(eventType);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 3);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getPlace().contains(eventCountry));
            assertTrue(eventDetailsPage.getPlace().contains(eventCity));
            assertTrue(eventDetailsPage.getEventType().equals(eventType));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByEventTypeAndCountryAndCityNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventType = "Concert";
        String eventCountry = "Serbia";
        String eventCity = "Uzice";

        homePage.enterCountry(eventCountry);
        homePage.enterCity(eventCity);
        homePage.enterEventType(eventType);
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByNameAndEventType(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventType = "Conference";
        String eventName = "Ai";

        homePage.enterName(eventName);
        homePage.enterEventType(eventType);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 2);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getEventName().toLowerCase().contains(eventName.toLowerCase()));
            assertTrue(eventDetailsPage.getEventType().equals(eventType));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByNameAndEventTypeNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventType = "Conference";
        String eventName = "Aii";

        homePage.enterName(eventName);
        homePage.enterEventType(eventType);
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByStartDateAndEndDateAndMaxParticipants(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        int maxParticipants = 200;

        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.DECEMBER, 25, 9,0,0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDateTime = calendar.getTime();
        homePage.enterStartDate(startDateTime);
        homePage.enterStartTime(LocalTime.of(9,0));
        calendar.set(2026, Calendar.DECEMBER, 25, 12,30,0);
        Date endDateTime = calendar.getTime();
        homePage.enterEndDate(endDateTime);
        homePage.enterEndTime(LocalTime.of(12,30));
        homePage.enterMaxParticipantsNumber(maxParticipants);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 1);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertEquals(eventDetailsPage.getStartDateAndTime(), startDateTime);
            assertEquals(eventDetailsPage.getEndDateAndTime(), endDateTime);
            assertTrue(eventDetailsPage.getNumParticipants() <= maxParticipants);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByStartDateAndEndDateAndMaxParticipantsNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        int maxParticipants = 200;

        Calendar calendar = Calendar.getInstance();
        calendar.set(2027, Calendar.DECEMBER, 25, 9,0,0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDateTime = calendar.getTime();
        homePage.enterStartDate(startDateTime);
        homePage.enterStartTime(LocalTime.of(9,0));
        calendar.set(2027, Calendar.DECEMBER, 25, 12,30,0);
        Date endDateTime = calendar.getTime();
        homePage.enterEndDate(endDateTime);
        homePage.enterEndTime(LocalTime.of(12,30));
        homePage.enterMaxParticipantsNumber(maxParticipants);
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByNameAndMaxParticipantsAndCountryAndCity(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventName = "Conference";
        int maxParticipants = 500;
        String country = "Serbia";
        String city = "Novi Sad";

        homePage.enterName(eventName);
        homePage.enterMaxParticipantsNumber(maxParticipants);
        homePage.enterCountry(country);
        homePage.enterCity(city);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 2);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getEventName().toLowerCase().contains(eventName.toLowerCase()));
            assertTrue(eventDetailsPage.getNumParticipants() <= maxParticipants);
            assertTrue(eventDetailsPage.getPlace().contains(country));
            assertTrue(eventDetailsPage.getPlace().contains(city));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByNameAndMaxParticipantsAndCountryAndCityNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventName = "Conference";
        int maxParticipants = 500;
        String country = "Serbia";
        String city = "Uzice";

        homePage.enterName(eventName);
        homePage.enterMaxParticipantsNumber(maxParticipants);
        homePage.enterCountry(country);
        homePage.enterCity(city);
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByDescriptionAndEndDateAndEndTime(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventDescription = "football derby";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.DECEMBER, 20, 20,00,0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        homePage.enterEndDate(date);
        homePage.enterEndTime(LocalTime.of(20,00));
        homePage.enterDescription(eventDescription);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 1);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getDescription().toLowerCase().contains(eventDescription.toLowerCase()));
            assertTrue(eventDetailsPage.getEndDateAndTime().equals(date));
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByDescriptionAndEndDateAndEndTimeNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventDescription = "football match";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.DECEMBER, 20, 20,00,0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        homePage.enterEndDate(date);
        homePage.enterEndTime(LocalTime.of(20,00));
        homePage.enterDescription(eventDescription);
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByEventTypeAndStartDateAndStartTime(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventType = "Football Match";

        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.DECEMBER, 23, 18, 30,0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        homePage.enterStartDate(date);
        homePage.enterStartTime(LocalTime.of(18,30));
        homePage.enterEventType(eventType);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 1);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertEquals(eventDetailsPage.getStartDateAndTime(), date);
            assertEquals(eventDetailsPage.getEventType(), eventType);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByEventTypeAndStartDateAndStartTimeNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventType = "Football Match";

        Calendar calendar = Calendar.getInstance();
        calendar.set(2027, Calendar.DECEMBER, 23, 18, 30,0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        homePage.enterStartDate(date);
        homePage.enterStartTime(LocalTime.of(18,30));
        homePage.enterEventType(eventType);
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByCountryAndStartDateAndEndDate(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventCountry = "Serbia";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.DECEMBER, 17, 0,0,0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();
        homePage.enterStartDate(startDate);
        calendar.set(2026, Calendar.DECEMBER, 17, 0,0,0);
        Date endDate = calendar.getTime();
        homePage.enterEndDate(endDate);
        homePage.enterCountry(eventCountry);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 1);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertEquals(eventDetailsPage.getStartDate(), startDate);
            assertEquals(eventDetailsPage.getEndDate(), endDate);
            assertTrue(eventDetailsPage.getPlace().contains(eventCountry));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByCountryAndStartDateAndEndDateNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventCountry = "Serbia";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2027, Calendar.DECEMBER, 17, 0,0,0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();
        homePage.enterStartDate(startDate);
        calendar.set(2027, Calendar.DECEMBER, 17, 0,0,0);
        Date endDate = calendar.getTime();
        homePage.enterEndDate(endDate);
        homePage.enterCountry(eventCountry);
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByDescriptionAndCountry(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventDescription = "match";
        String eventCountry = "Serbia";

        homePage.enterDescription(eventDescription);
        homePage.enterCountry(eventCountry);
        homePage.search();

        currentPage = homePage.getCurrentPage();
        totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 1);
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 2);
        for (WebElement eventCard : currentPageEvents) {
            EventDetailsPage eventDetailsPage = openEventDetailsPage(eventCard);
            assertTrue(eventDetailsPage.getDescription().toLowerCase().contains(eventDescription.toLowerCase()));
            assertTrue(eventDetailsPage.getPlace().contains(eventCountry));

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }

    @Test
    public void testFilterEventsByDescriptionAndCountryNoResults(){
        HomePage homePage = new HomePage(driver);
        waitForElementToBePresent(By.xpath("//div[contains(@class, 'pagination')]/p"));
        homePage.openFilterList();

        int currentPage = homePage.getCurrentPage();
        int totalPages = homePage.getTotalPages();
        assertEquals(currentPage, 1);
        assertEquals(totalPages, 2);
        List<WebElement> currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 10);

        String eventDescription = "match";
        String eventCountry = "USA";

        homePage.enterDescription(eventDescription);
        homePage.enterCountry(eventCountry);
        homePage.search();

        String message = homePage.getNoResultsMessage();
        assertEquals(message, "No results");
        currentPageEvents = homePage.getCurrentPageEvents();
        assertEquals(currentPageEvents.size(), 0);

        homePage.refreshFilter();
        homePage.openFilterList();
        homePage.search();
    }
}
