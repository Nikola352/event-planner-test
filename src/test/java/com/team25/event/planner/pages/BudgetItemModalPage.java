package com.team25.event.planner.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BudgetItemModalPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By modalContainer = By.xpath("//div[contains(@class,'mat-mdc-dialog-surface')]");
    private By offeringCategoryDropdown = By.xpath("//span[contains(@class,'mat-mdc-select-placeholder') and normalize-space()='Choose an offering category']/ancestor::div[contains(@class,'mat-mdc-select-trigger')]");
    private By budgetInput = By.cssSelector("input[placeholder='Enter category budget']");
    private By saveButton = By.xpath("//span[@class='mdc-button__label' and text()='Approve']/ancestor::button");
    private By cancelButton = By.xpath("//span[@class='mdc-button__label' and text()='Cancel']/ancestor::button");
    private By error = By.cssSelector("mat-error");


    public BudgetItemModalPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalContainer));
    }

    public boolean isOfferingCategoryDisabled() {
        WebElement dropdown = driver.findElement(offeringCategoryDropdown);
        return dropdown.getAttribute("class").contains("mat-select-disabled");
    }

    public void selectOfferingCategory(String categoryName) {
        if (isOfferingCategoryDisabled()) {
            return;
        }
        wait.until(ExpectedConditions.elementToBeClickable(offeringCategoryDropdown)).click();
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-option//span[contains(text(),'" + categoryName + "')]")
        ));
        option.click();
    }

    public void setBudget(String budgetValue) {

        WebElement budgetField = wait.until(ExpectedConditions.elementToBeClickable(budgetInput));
        budgetField.clear();
        budgetField.sendKeys(budgetValue);
    }

    public void clickSave(boolean expectModalClose) {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        if (expectModalClose) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modalContainer));
        }
    }
    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modalContainer));
    }
    public String getErrorMessage() {

        return wait.until(ExpectedConditions.visibilityOfElementLocated(error)).getText();
    }
}
