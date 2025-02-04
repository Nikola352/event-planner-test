package com.team25.event.planner.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AgendaPage {
    private final WebDriver driver;

    public AgendaPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By nameField = By.cssSelector("input[formControlName='name']");
    private final By descriptionField = By.cssSelector("textarea[formControlName='description']");
    private final By startDateField = By.cssSelector("input[formControlName='startDate']");
    private final By endDateField = By.cssSelector("input[formControlName='endDate']");
    private final By startTimeField = By.cssSelector("input[formControlName='startTime']");
    private final By endTimeField = By.cssSelector("input[formControlName='endTime']");
    private final By locationField = By.cssSelector("input[formControlName='location']");
    private final By addButton = By.cssSelector("button[type='submit']");
    private final By finishButton = By.cssSelector(".finish-btn button");
    private final By activityTitle = By.cssSelector(".timeline-container mat-card mat-card-title");

    public void enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void enterDescription(String description) {
        driver.findElement(descriptionField).sendKeys(description);
    }

    public void enterStartDate(String startDate) {
        driver.findElement(startDateField).sendKeys(startDate);
    }

    public void enterEndDate(String endDate) {
        driver.findElement(endDateField).sendKeys(endDate);
    }

    public void enterStartTime(String startTime) {
        driver.findElement(startTimeField).sendKeys(startTime);
    }

    public void enterEndTime(String endTime) {
        driver.findElement(endTimeField).sendKeys(endTime);
    }

    public void enterLocation(String location) {
        driver.findElement(locationField).sendKeys(location);
    }

    public void clickAddButton() {
        driver.findElement(addButton).click();
    }

    public void clickFinishButton() {
        driver.findElement(finishButton).click();
    }

    public List<String> getActivities() {
        return driver.findElements(activityTitle).stream().map(WebElement::getText).toList();
    }
}
