package com.team25.event.planner.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BudgetPlanPage {

    private final WebDriver driver;
    private WebDriverWait wait;

    public BudgetPlanPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    private final By tableLocator = By.cssSelector("mat-table");
    private final By tableRowsLocator = By.cssSelector("mat-table mat-row");
    private final By tableCellsLocator = By.cssSelector("mat-table mat-cell");
    private final By budgetLabelLocator = By.cssSelector("div.price-display");
    private final By addBudgetButtonLocator = By.xpath("//span[contains(@class,'mdc-button__label') and normalize-space()='Add new budget item']/ancestor::button");

    public void waitForTableToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tableLocator));
    }

    public List<List<String>> getTableData() {
        waitForTableToLoad();
        List<List<String>> tableData = new ArrayList<>();

        List<WebElement> rows = driver.findElements(tableRowsLocator);
        for (WebElement row : rows) {
            List<String> cellTexts = new ArrayList<>();
            List<WebElement> cells = row.findElements(By.cssSelector("mat-cell"));
            for (WebElement cell : cells) {
                cellTexts.add(cell.getText().trim());
            }
            tableData.add(cellTexts);
        }
        return tableData;
    }

    public String getOverallBudget() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(budgetLabelLocator)).getText();
    }

    public void clickAddNewBudgetItem() {
        wait.until(ExpectedConditions.elementToBeClickable(addBudgetButtonLocator)).click();
    }


    public int getRowCount() {
        waitForTableToLoad();
        return driver.findElements(tableRowsLocator).size();
    }


    public void clickEditButtonInRow(int rowIndex) {
        waitForTableToLoad();
        WebElement row = driver.findElements(tableRowsLocator).get(rowIndex);
        WebElement editButton = row.findElement(By.cssSelector("button mat-icon[ng-reflect-svg-icon='edit'], button mat-icon:contains('edit')"));
        editButton.click();
    }


    public void clickDeleteButtonInRow(int rowIndex) {
        waitForTableToLoad();
        WebElement row = driver.findElements(tableRowsLocator).get(rowIndex);
        WebElement deleteButton = row.findElement(By.cssSelector("button mat-icon:contains('delete')"));
        deleteButton.click();
    }


}
