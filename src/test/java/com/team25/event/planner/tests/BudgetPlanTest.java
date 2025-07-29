package com.team25.event.planner.tests;

import com.team25.event.planner.base.BaseTest;
import com.team25.event.planner.constants.Constants;
import com.team25.event.planner.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BudgetPlanTest extends BaseTest {
    @BeforeClass
    public void login() {
        driver.get(Constants.LOGIN_URL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("organizer@gmail.com", "password1");
        waitForElementToBePresent(By.cssSelector("a[routerLink='/user/profile']"));
        driver.get(Constants.EVENT_DETAILS_URL);
        WebElement budgetButton = driver.findElement(By.xpath("//button[contains(text(), 'Budget Plan')]"));
        budgetButton.click();
    }

    @Test
    public void testCreateBudgetItem(){

    }
}
