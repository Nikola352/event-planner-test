package com.team25.event.planner.tests;

import com.team25.event.planner.base.BaseTest;
import com.team25.event.planner.constants.Constants;
import com.team25.event.planner.pages.AgendaPage;
import com.team25.event.planner.pages.CreateEventPage;
import com.team25.event.planner.pages.EventDetailsPage;
import com.team25.event.planner.pages.LoginPage;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class CreateEventTest extends BaseTest {

    @BeforeClass
    public void login() {
        driver.get(Constants.LOGIN_URL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("e@e.e", "Test1234");
        waitForElementToBePresent(By.cssSelector("a[routerLink='/user/profile']"));
    }

    @Test
    public void testCreateEvent() {
        driver.get(Constants.CREATE_EVENT_URL);
        CreateEventPage createEventPage = new CreateEventPage(driver);

        // Fill in the form
        createEventPage.enterName("New Event");
        createEventPage.selectEventType("Conference");
        createEventPage.enterDescription("This is a test event description.");
        createEventPage.selectPrivacyType("Public");
        createEventPage.enterStartDate("12/31/2027");
        createEventPage.enterEndDate("01/01/2028");
        createEventPage.enterStartTime("10:00");
        createEventPage.enterEndTime("18:00");
        createEventPage.enterCountry("USA");
        createEventPage.enterCity("New York");
        createEventPage.enterAddress("123 Main St");
        createEventPage.enterMaxParticipants(100);

        // Submit the form
        createEventPage.submitForm();

        // Agenda
        waitForElementToBePresent(By.xpath("//mat-card-title[contains(text(), 'Add Activity')]"));

        String currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl, "URL is null.");
        assertTrue(currentUrl.contains("/agenda"), "Redirection URL does not contain '/agenda'! Current URL: " + currentUrl);

        AgendaPage agendaPage = new AgendaPage(driver);

        agendaPage.enterName("First Activity");
        agendaPage.enterDescription("This is a test activity description...");
        agendaPage.enterStartDate("12/31/2027");
        agendaPage.enterEndDate("12/31/2027");
        agendaPage.enterStartTime("10:00");
        agendaPage.enterEndTime("12:00");
        agendaPage.enterLocation("Main hall");
        agendaPage.clickAddButton();

        List<String> activities = agendaPage.getActivities();
        assertFalse(activities.isEmpty());
        assertEquals(activities.getFirst(), "First Activity");

        agendaPage.enterName("Second Activity");
        agendaPage.enterDescription("This is a test activity description...");
        agendaPage.enterStartDate("01/01/2028");
        agendaPage.enterEndDate("01/01/2028");
        agendaPage.enterStartTime("10:00");
        agendaPage.enterEndTime("12:00");
        agendaPage.clickAddButton();

        activities = agendaPage.getActivities();
        assertEquals(activities.size(), 2);
        assertEquals(activities.getFirst(), "First Activity");
        assertEquals(activities.getLast(), "Second Activity");

        agendaPage.clickFinishButton();

        // Budget-planning (skipped)
        By finishButton = By.xpath("//button/span[contains(text(), 'Finish Budget Plan')]/..");
        waitForElementToBePresent(finishButton);
        driver.findElement(finishButton).click();

        // Event details page
        By eventTitle = By.cssSelector(".title-section mat-card-title");
        waitForElementToBePresent(eventTitle);

        currentUrl = driver.getCurrentUrl();
        assertNotNull(currentUrl, "URL is null.");
        assertTrue(currentUrl.contains("/event"), "Redirection URL does not contain '/event'! Current URL: " + currentUrl);

        EventDetailsPage eventDetailsPage = new EventDetailsPage(driver);

        assertEquals(eventDetailsPage.getEventName(), "New Event");
        assertEquals(eventDetailsPage.getEventType(), "Conference");
        assertEquals(eventDetailsPage.getAddress(), "123 Main St");
        assertEquals(eventDetailsPage.getPlace(), "New York, USA");
        assertEquals(eventDetailsPage.getDescription(), "This is a test event description.");
        assertEquals(eventDetailsPage.getNumParticipants(), 100);

        activities = eventDetailsPage.getActivities();
        assertEquals(activities.size(), 2);
        assertEquals(activities.getFirst(), "First Activity");
        assertEquals(activities.getLast(), "Second Activity");
    }
}