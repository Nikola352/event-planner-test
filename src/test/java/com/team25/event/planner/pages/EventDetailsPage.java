package com.team25.event.planner.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class EventDetailsPage {
    private final WebDriver driver;

    public EventDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By eventName = By.cssSelector(".title-section mat-card-title");
    private final By eventType = By.cssSelector(".title-section mat-card-subtitle");
    private final By eventDate = By.cssSelector(".event-details .detail-item:nth-child(1) .detail-text span.primary-text");
    private final By eventTime = By.cssSelector(".event-details .detail-item:nth-child(1) .detail-text span.secondary-text");
    private final By address = By.cssSelector(".event-details .detail-item:nth-child(2) .detail-text span.primary-text");
    private final By place = By.cssSelector(".event-details .detail-item:nth-child(2) .detail-text span.secondary-text");
    private final By description = By.cssSelector(".event-details .detail-item:nth-child(3) p");
    private final By organizer = By.cssSelector(".event-details .detail-item:nth-child(4) .detail-text span.primary-text");
    private final By numParticipants = By.cssSelector(".event-details .detail-item:nth-child(4) .detail-text span.secondary-text");
    private final By privacyChip = By.cssSelector(".privacy-chip span.wrapper span");
    private final By activityTitle = By.cssSelector(".timeline-container .timeline-card mat-card-title");

    public String getEventName() {
        return driver.findElement(eventName).getText();
    }

    public String getEventType() {
        return driver.findElement(eventType).getText();
    }

    public String getAddress() {
        return driver.findElement(address).getText();
    }

    public String getPlace() {
        return driver.findElement(place).getText();
    }

    public String getDescription() {
        return driver.findElement(description).getText();
    }

    public String getOrganizer() {
        return driver.findElement(organizer).getText().substring(13);
    }

    public int getNumParticipants() throws NumberFormatException {
        return Integer.parseInt(driver.findElement(numParticipants).getText().substring(18));
    }

    public Date getStartDate() {
        try {
            String startDate = driver.findElement(eventDate).getText().split(" - ")[0];
            SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
            Date date = sdf.parse(startDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }catch (ParseException e) {
            return null;
        }
    }

    public Date getStartDateAndTime() {
        try {
            String startDate = driver.findElement(eventDate).getText().split(" - ")[0];
            SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
            Date date = sdf.parse(startDate);

            String startTime = driver.findElement(eventTime).getText().split(" - ")[0];
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
            Date time = timeFormat.parse(startTime);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, time.getHours());
            calendar.set(Calendar.MINUTE, time.getMinutes());
            calendar.set(Calendar.SECOND, time.getSeconds());
            calendar.set(Calendar.MILLISECOND, 0);

            return calendar.getTime();
        }catch (ParseException e) {
            return null;
        }
    }

    public Date getEndDate() {
        try {
            String startDate = driver.findElement(eventDate).getText().split(" - ")[1];
            SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
            Date date = sdf.parse(startDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }catch (ParseException e) {
            return null;
        }
    }

    public Date getEndDateAndTime() {
        try {
            String startDate = driver.findElement(eventDate).getText().split(" - ")[1];
            SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
            Date date = sdf.parse(startDate);

            String startTime = driver.findElement(eventTime).getText().split(" - ")[1];
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Date time = timeFormat.parse(startTime);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, time.getHours());
            calendar.set(Calendar.MINUTE, time.getMinutes());
            calendar.set(Calendar.SECOND, time.getSeconds());
            calendar.set(Calendar.MILLISECOND, 0);

            return calendar.getTime();
        }catch (ParseException e) {
            return null;
        }
    }

    public String getPrivacyType() {
        return driver.findElement(privacyChip).getText();
    }

    public List<String> getActivities() {
        return driver.findElements(activityTitle).stream().map(WebElement::getText).toList();
    }
}
