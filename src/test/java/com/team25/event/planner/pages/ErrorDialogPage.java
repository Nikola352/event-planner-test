package com.team25.event.planner.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ErrorDialogPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By dialogContainer = By.cssSelector("mat-dialog-container.mdc-dialog--open");
    private final By errorMessage = By.cssSelector("mat-dialog-container .error-dialog p");
    private final By closeButton = By.cssSelector("button.mdc-button.mat-mdc-raised-button.mat-warn");


    public ErrorDialogPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(dialogContainer));
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    public void closeDialog() {
        System.out.println("Clicking close button...");
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
        System.out.println("Clicked. Waiting for dialog to disappear...");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(dialogContainer));
        System.out.println("Dialog closed.");
    }
}
