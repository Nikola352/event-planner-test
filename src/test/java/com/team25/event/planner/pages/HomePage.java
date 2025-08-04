package com.team25.event.planner.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class HomePage {
    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By searchButton = By.xpath("//button[.//mat-icon[text()='search']]");
    private final By filterButton = By.xpath("//button[.//mat-icon[text()='filter_list']]");
    private final By refreshButton = By.xpath("//button[.//mat-icon[text()='refresh']]");
    private final By nextButton = By.xpath("//button[.//mat-icon[text()='navigate_next']]");
    private final By previousButton = By.xpath("//button[.//mat-icon[text()='navigate_before']]");
    private final By pageSize = By.xpath("//app-home-all-events//div[contains(@class, 'pagination')]/p");
    private final By logoutButton = By.xpath("//div//mat-icon[text()='logout']");

    private final By eventName = By.cssSelector("input[placeholder='Name']");
    private final By eventDescription = By.xpath("//mat-label[contains(text(), 'Description')]/ancestor::mat-form-field//input");
    private final By eventMaxParticipants = By.xpath("//mat-label[contains(text(), 'Max participants')]/ancestor::mat-form-field//input");
    private final By eventCountry = By.xpath("//mat-label[contains(text(), 'Country')]/ancestor::mat-form-field//input");
    private final By eventCity= By.xpath("//mat-label[contains(text(), 'City')]/ancestor::mat-form-field//input");
    private final By eventType = By.xpath("//mat-form-field[.//mat-label[text()='Event type']]");
    private final By eventStartDate =  By.xpath("//mat-form-field[.//mat-label[text()='Start date']]//input");
    private final By eventEndDate = By.xpath("//mat-form-field[.//mat-label[text()='End date']]//input");
    private final By eventStartTime =  By.xpath("//mat-form-field[.//mat-label[text()='Start time']]//input");
    private final By eventEndTime =  By.xpath("//mat-form-field[.//mat-label[text()='End time']]//input");

    private final By homeEventsContainer = By.cssSelector("div.home-events-container");

    public void search(){
        driver.findElement(searchButton).click();
    }

    public void openFilterList(){
        driver.findElement(filterButton).click();
    }

    public void refreshFilter(){
        driver.findElement(refreshButton).click();
    }

    public void nextPage(){
        driver.findElement(nextButton).click();
    }

    public void previousPage(){
        driver.findElement(previousButton).click();
    }

    public void enterName(String name) {
        driver.findElement(eventName).sendKeys(name);
    }

    public void enterDescription(String description) {
        driver.findElement(eventDescription).sendKeys(description);
    }

    public void enterMaxParticipantsNumber(Integer maxParticipants) {
        driver.findElement(eventMaxParticipants).sendKeys(maxParticipants.toString());
    }

    public void enterCountry(String country) {
        driver.findElement(eventCountry).sendKeys(country);
    }

    public void enterCity(String city) {
        driver.findElement(eventCity).sendKeys(city);
    }

    public void enterEventType(String eventTypeName) {
        driver.findElement(eventType).click();

        By option = By.xpath("//mat-option[.//span[contains(text(),'"+ eventTypeName +"')]]");

        driver.findElement(option).click();
    }

    public void enterStartDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = sdf.format(date);

        driver.findElement(eventStartDate).sendKeys(dateString);
    }

    public void enterEndDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = sdf.format(date);

        driver.findElement(eventEndDate).sendKeys(dateString);
    }


    public void enterStartTime(LocalTime startTime) {
        driver.findElement(eventStartTime).sendKeys(startTime.toString());
    }

    public void enterEndTime(LocalTime endTime) {
        driver.findElement(eventEndTime).sendKeys(endTime.toString());
    }

    public List<WebElement> getCurrentPageEvents(){
        WebElement container = driver.findElement(homeEventsContainer);
        return container.findElements(By.cssSelector("app-home-event-card"));
    }
    public int getCurrentPage(){
        String page = driver.findElement(pageSize).getText();
        return Integer.parseInt(page.split("/")[0]);
    }
    public int getTotalPages(){
        String page = driver.findElement(pageSize).getText();
        return Integer.parseInt(page.split("/")[1]);
    }

    public void logout() {
        driver.findElement(logoutButton).click();
    }

    public String getNoResultsMessage() {
        return driver.findElement(pageSize).getText();
    }

}
