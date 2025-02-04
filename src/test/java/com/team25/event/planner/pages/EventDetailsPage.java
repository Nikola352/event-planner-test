package com.team25.event.planner.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class EventDetailsPage {
    private final WebDriver driver;

    public EventDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By eventName = By.cssSelector(".title-section mat-card-title");
    private final By eventType = By.cssSelector(".title-section mat-card-subtitle");
    private final By address = By.cssSelector(".event-details .detail-item:nth-child(2) .detail-text span.primary-text");
    private final By place = By.cssSelector(".event-details .detail-item:nth-child(2) .detail-text span.secondary-text");
    private final By description = By.cssSelector(".event-details .detail-item:nth-child(3) p");
    private final By organizer = By.cssSelector(".event-details .detail-item:nth-child(4) .detail-text span.primary-text");
    private final By numParticipants = By.cssSelector(".event-details .detail-item:nth-child(4) .detail-text span.secondary-text");
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

    public List<String> getActivities() {
        return driver.findElements(activityTitle).stream().map(WebElement::getText).toList();
    }
}
