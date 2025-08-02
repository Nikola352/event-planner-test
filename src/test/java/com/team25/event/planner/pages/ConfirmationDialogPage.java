package com.team25.event.planner.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConfirmationDialogPage {
    private final WebDriver driver;
    private WebDriverWait wait;

    private final By dialogContainer = By.cssSelector("mat-dialog-container.mdc-dialog--open");
    private final By message = By.cssSelector("mat-dialog-container mat-dialog-content p");
    private final By cancelButton = By.xpath("//button[.//span[text()='Cancel']]");
    private final By deleteButton = By.xpath("//button[.//span[text()='Delete']]");

    public ConfirmationDialogPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(message)).getText();
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(dialogContainer));
    }

    public void clickDelete() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(dialogContainer));
    }
}
