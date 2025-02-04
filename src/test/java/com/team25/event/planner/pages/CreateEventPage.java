package com.team25.event.planner.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreateEventPage {
    private final WebDriver driver;

    public CreateEventPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By nameField = By.cssSelector("input[formControlName='name']");
    private final By eventTypeDropdown = By.cssSelector("mat-select[formControlName='eventTypeId']");
    private final By descriptionField = By.cssSelector("textarea[formControlName='description']");
    private final By privacyTypeDropdown = By.cssSelector("mat-select[formControlName='privacyType']");
    private final By startDateField = By.cssSelector("input[formControlName='startDate']");
    private final By endDateField = By.cssSelector("input[formControlName='endDate']");
    private final By startTimeField = By.cssSelector("input[formControlName='startTime']");
    private final By endTimeField = By.cssSelector("input[formControlName='endTime']");
    private final By countryField = By.cssSelector("input[formControlName='country']");
    private final By cityField = By.cssSelector("input[formControlName='city']");
    private final By addressField = By.cssSelector("input[formControlName='address']");
    private final By maxParticipantsField = By.cssSelector("input[formControlName='maxParticipants']");
    private final By submitButton = By.cssSelector("button[type='submit']");

    public void enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void selectEventType(String eventType) {
        driver.findElement(eventTypeDropdown).click();
        driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + eventType + "')]/..")).click();
    }

    public void enterDescription(String description) {
        driver.findElement(descriptionField).sendKeys(description);
    }

    public void selectPrivacyType(String privacyType) {
        driver.findElement(privacyTypeDropdown).click();
        driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + privacyType + "')]/..")).click();
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

    public void enterCountry(String country) {
        driver.findElement(countryField).sendKeys(country);
    }

    public void enterCity(String city) {
        driver.findElement(cityField).sendKeys(city);
    }

    public void enterAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    public void enterMaxParticipants(int maxParticipants) {
        driver.findElement(maxParticipantsField).sendKeys(String.valueOf(maxParticipants));
    }

    public void submitForm() {
        driver.findElement(submitButton).click();
    }
}