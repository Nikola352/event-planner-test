package com.team25.event.planner.tests;

import com.team25.event.planner.base.BaseTest;
import com.team25.event.planner.constants.Constants;
import com.team25.event.planner.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class BudgetPlanTest extends BaseTest {

    @BeforeClass
    public void login() {
        driver.get(Constants.LOGIN_URL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("e@e.e", "Test1234");
        waitForElementToBePresent(By.cssSelector("a[routerLink='/user/profile']"));
        driver.get(Constants.EVENT_DETAILS_URL);
        waitForElementToBePresent(By.xpath("//span[normalize-space()='Budget Plan']"));
        EventDetailsPage eventDetailsPage = new EventDetailsPage(driver);
        eventDetailsPage.openBudgetPlan();
    }

    @Test
    public void testCreateBudgetItem(){
        BudgetPlanPage budgetPlanPage = new BudgetPlanPage(driver);

        budgetPlanPage.clickAddNewBudgetItem();

        BudgetItemModalPage modal = new BudgetItemModalPage(driver);
        modal.selectOfferingCategory("Catering Services");
        modal.setBudget("500");
        modal.clickSave(true);

        List<List<String>> rows = budgetPlanPage.getTableData();
        // assert
        assertTrue(rows.stream().anyMatch(r -> r.contains("Catering Services") && r.contains("500")));
        assertEquals(2, budgetPlanPage.getRowCount());
        assertEquals("2700.00 $", budgetPlanPage.getOverallBudget());
    }

    @Test
    public void testDuplicateOfferingCategory() {
        BudgetPlanPage budgetPlanPage = new BudgetPlanPage(driver);

        budgetPlanPage.clickAddNewBudgetItem();
        BudgetItemModalPage modal = new BudgetItemModalPage(driver);

        modal.selectOfferingCategory("Catering Services");
        modal.setBudget("300");
        modal.clickSave(false);

        assertEquals("Offering category has already been added to budget", modal.getErrorMessage());

        modal.clickCancel();

        List<List<String>> rows = budgetPlanPage.getTableData();

        assertEquals(2, budgetPlanPage.getRowCount());
        assertEquals("2700.00 $", budgetPlanPage.getOverallBudget());

    }

    @Test
    public void testEmptyOfferingCategorySelection() {
        BudgetPlanPage budgetPlanPage = new BudgetPlanPage(driver);
        int initialRowCount = budgetPlanPage.getRowCount();

        budgetPlanPage.clickAddNewBudgetItem();
        BudgetItemModalPage modal = new BudgetItemModalPage(driver);

        modal.setBudget("400");
        modal.clickSave(false);

        assertEquals("You must choose offering category", modal.getErrorMessage());

        modal.clickCancel();

        assertEquals(initialRowCount, budgetPlanPage.getRowCount());
        assertEquals("2700.00 $", budgetPlanPage.getOverallBudget());
    }

    @Test
    public void testValidBudgetUpdate() {
        BudgetPlanPage budgetPlanPage = new BudgetPlanPage(driver);

        int initialRowCount = budgetPlanPage.getRowCount();
        assertEquals(2, initialRowCount);

        budgetPlanPage.clickEditButtonInRow(1);
        BudgetItemModalPage modal = new BudgetItemModalPage(driver);

        assertTrue(modal.isOfferingCategoryDisabled());

        modal.setBudget("800");
        modal.clickSave(true);

        assertEquals(initialRowCount, budgetPlanPage.getRowCount());
        String updatedBudget = budgetPlanPage.getTableData().get(1).get(1);

        assertEquals("800", updatedBudget);
        assertEquals("3000.00 $", budgetPlanPage.getOverallBudget());
    }

    @Test
    public void testUpdateBudgetWithInvalidValue() {
        BudgetPlanPage budgetPlanPage = new BudgetPlanPage(driver);

        int initialRowCount = budgetPlanPage.getRowCount();
        assertEquals(2, initialRowCount);
        String originalBudget = budgetPlanPage.getTableData().get(1).get(1);
        String originalOverall = budgetPlanPage.getOverallBudget();

        budgetPlanPage.clickEditButtonInRow(1);
        BudgetItemModalPage modal = new BudgetItemModalPage(driver);

        assertTrue(modal.isOfferingCategoryDisabled());

        modal.setBudget("-1");
        modal.clickSave(false);

        assertEquals("Budget must be at least €1.", modal.getErrorMessage());

        modal.clickCancel();

        assertEquals(initialRowCount, budgetPlanPage.getRowCount());
        assertEquals(originalBudget, budgetPlanPage.getTableData().get(1).get(1));
        assertEquals(originalOverall, budgetPlanPage.getOverallBudget());
    }

    @Test
    public void testUpdateBudgetWithLowBudgetForPurchasedCategory(){
        BudgetPlanPage budgetPlanPage = new BudgetPlanPage(driver);

        int initialRowCount = budgetPlanPage.getRowCount();
        assertEquals(2, initialRowCount);

        budgetPlanPage.clickEditButtonInRow(0);
        BudgetItemModalPage modal = new BudgetItemModalPage(driver);

        assertTrue(modal.isOfferingCategoryDisabled());

        modal.setBudget("800");
        modal.clickSave(false);

        ErrorDialogPage errorDialog = new ErrorDialogPage(driver);
        assertEquals("Your budget is not enough for purchased offering", errorDialog.getErrorMessage());
        errorDialog.closeDialog();

        assertEquals("2700.00 $", budgetPlanPage.getOverallBudget());
        assertEquals(initialRowCount, budgetPlanPage.getRowCount());

    }
    @Test
    public void testDeleteCancel() {
        BudgetPlanPage budgetPlanPage = new BudgetPlanPage(driver);
        int initialCount = budgetPlanPage.getRowCount();

        budgetPlanPage.clickDeleteButtonInRow(0);

        ConfirmationDialogPage deleteDialog = new ConfirmationDialogPage(driver);
        assertEquals("Do you really want to delete Entertainment Services", deleteDialog.getMessage());

        deleteDialog.clickCancel();

        List<List<String>> rows = budgetPlanPage.getTableData();

        assertEquals(initialCount, budgetPlanPage.getRowCount());
        assertEquals("2700.00 $", budgetPlanPage.getOverallBudget());
        assertTrue(rows.stream().anyMatch(r -> r.contains("Entertainment Services") && r.contains("2200")));
    }

    @Test
    public void testDeleteConfirm() {
        BudgetPlanPage budgetPlanPage = new BudgetPlanPage(driver);
        budgetPlanPage.clickAddNewBudgetItem();

        BudgetItemModalPage modal = new BudgetItemModalPage(driver);
        modal.selectOfferingCategory("Technical Services");
        modal.setBudget("500");
        modal.clickSave(true);

        List<List<String>> rows = budgetPlanPage.getTableData();
        // assert
        assertTrue(rows.stream().anyMatch(r -> r.contains("Technical Services") && r.contains("500")));
        assertEquals(3, budgetPlanPage.getRowCount());
        assertEquals("3200.00 $", budgetPlanPage.getOverallBudget());
        int initialCount = budgetPlanPage.getRowCount();

        budgetPlanPage.clickDeleteButtonInRow(2);

        ConfirmationDialogPage deleteDialog = new ConfirmationDialogPage(driver);
        assertEquals("Do you really want to delete Technical Services", deleteDialog.getMessage());

        deleteDialog.clickDelete();
        budgetPlanPage.waitForRowCountToBe(initialCount - 1);
        rows = budgetPlanPage.getTableData();

        assertEquals(initialCount - 1, budgetPlanPage.getRowCount());
        assertTrue(rows.stream().noneMatch(r -> r.contains("Technical Services") && r.contains("500")));
        assertEquals("2700.00 $", budgetPlanPage.getOverallBudget());
    }

    @Test
    public void testDeleteFailWithErrorDialog() {
        BudgetPlanPage budgetPlanPage = new BudgetPlanPage(driver);
        int initialCount = budgetPlanPage.getRowCount();

        budgetPlanPage.clickDeleteButtonInRow(0);

        ConfirmationDialogPage deleteDialog = new ConfirmationDialogPage(driver);
        assertEquals("Do you really want to delete Entertainment Services", deleteDialog.getMessage());

        deleteDialog.clickDelete();

        ErrorDialogPage errorDialog = new ErrorDialogPage(driver);
        assertEquals("Offering category belongs to purchased offering", errorDialog.getErrorMessage());
        errorDialog.closeDialog();

        List<List<String>> rows = budgetPlanPage.getTableData();

        assertEquals(initialCount, budgetPlanPage.getRowCount());
        assertEquals("2700.00 $", budgetPlanPage.getOverallBudget());
        assertTrue(rows.stream().anyMatch(r -> r.contains("Entertainment Services") && r.contains("2200")));
    }

    @Test
    public void testFinishBudgetPlan(){
        BudgetPlanPage budgetPlanPage = new BudgetPlanPage(driver);
        EventDetailsPage eventDetailsPage = budgetPlanPage.clickFinishButton();

        assertEquals("Pop Concert", eventDetailsPage.getEventName());
        eventDetailsPage.openBudgetPlan();
    }

    @Test
    public void testListBudgetItems(){
        BudgetPlanPage budgetPlanPage = new BudgetPlanPage(driver);
        List<List<String>> expectedRows = List.of(
                List.of("Entertainment Services", "2200"),
                List.of("Catering Services", "500")
        );

        assertTrue(budgetPlanPage.areRowsPresent(expectedRows));
        assertEquals(2, budgetPlanPage.getRowCount());
        assertEquals("2700.00 $", budgetPlanPage.getOverallBudget());
    }
}
