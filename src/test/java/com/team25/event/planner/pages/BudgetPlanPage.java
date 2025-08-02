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
    private final By finishButton = By.xpath("//button[.//span[normalize-space()='Finish Budget Plan']]");


    public void waitForRowCountToBe(int expectedCount) {
        wait.until(ExpectedConditions.numberOfElementsToBe(tableRowsLocator, expectedCount));
    }
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

    public EventDetailsPage clickFinishButton() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
        return new EventDetailsPage(driver);
    }

    public boolean areRowsPresent(List<List<String>> expectedRows) {
        List<List<String>> actualRows = getTableData();

        for (List<String> expectedRow : expectedRows) {
            boolean found = actualRows.stream().anyMatch(row -> row.containsAll(expectedRow));
            if (!found) {
                return false;
            }
        }
        return true;
    }


    public int getRowCount() {
        waitForTableToLoad();
        return driver.findElements(tableRowsLocator).size();
    }


    public void clickEditButtonInRow(int rowIndex) {
        waitForTableToLoad();
        WebElement row = driver.findElements(tableRowsLocator).get(rowIndex);
        WebElement editButton = row.findElement(By.xpath(".//mat-icon[normalize-space()='edit']/ancestor::button\n"));
        editButton.click();
    }


    public void clickDeleteButtonInRow(int rowIndex) {
        waitForTableToLoad();
        WebElement row = driver.findElements(tableRowsLocator).get(rowIndex);
        WebElement deleteButton = row.findElement(By.xpath(".//mat-icon[normalize-space()='delete']/ancestor::button"));
        deleteButton.click();
    }


}
