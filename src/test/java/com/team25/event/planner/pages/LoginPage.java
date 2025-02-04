package com.team25.event.planner.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By emailField = By.cssSelector("input[formControlName='email']");
    private final By passwordField = By.cssSelector("input[formControlName='password']");
    private final By submitButton = By.cssSelector("button[type='submit']");

    public void login(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(submitButton).click();
    }
}
