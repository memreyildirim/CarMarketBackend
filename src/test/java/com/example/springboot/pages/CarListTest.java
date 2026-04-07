package com.example.springboot.pages;

import com.example.springboot.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class CarListTest extends BaseTest {

    private static final String BASE_URL = "http://localhost:4160";
    private static final String LOGIN_URL = BASE_URL + "/login-screen";
    private static final String TEST_EMAIL = "mina@gmail.com"; //test için verilmiş default mail
    private static final String TEST_PASSWORD = "mina123"; //test için verilmiş default password

    private LoginPage loginPage;
    private CarListPage carListPage;

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage(driver);
        carListPage = new CarListPage(driver);
        navigateAndLogin();
    }

    private void navigateAndLogin() {
        driver.get(LOGIN_URL);
        loginPage.login(TEST_EMAIL, TEST_PASSWORD);
        carListPage.waitForPageLoad();
    }

    @Test
    void testCarFilteringWithHighPriceRange() {
        int initialCount = carListPage.getVisibleCarCount();
        System.out.println("Initial car count: " + initialCount);
        
        assertTrue(initialCount > 0, 
            "Car list should not be empty on initial load");

        carListPage.applyFilters("10000000", "20000000");

        int filteredCount = carListPage.getVisibleCarCount();
        System.out.println("Filtered car count: " + filteredCount);
        
        assertTrue(filteredCount < initialCount, 
            String.format("Filtered count (%d) should be less than initial count (%d)", 
                filteredCount, initialCount));
    }

    @Test
    void testCarListDisplayed() {
        assertTrue(carListPage.isCarListDisplayed(), 
            "Car list table should be displayed after login");
        
        int carCount = carListPage.getVisibleCarCount();
        assertTrue(carCount > 0, 
            "At least one car should be visible in the list");
    }

    @Test
    void testMinPriceFilterOnly() {
        int initialCount = carListPage.getVisibleCarCount();
        
        carListPage.filterByMinPrice("5000000");
        carListPage.waitForTableUpdate();
        
        int filteredCount = carListPage.getVisibleCarCount();
        
        assertTrue(filteredCount <= initialCount, 
            "Applying min price filter should reduce or maintain car count");
    }

    @Test
    void testMaxPriceFilterOnly() {
        int initialCount = carListPage.getVisibleCarCount();
        
        carListPage.filterByMaxPrice("1000000");
        carListPage.waitForTableUpdate();
        
        int filteredCount = carListPage.getVisibleCarCount();
        
        assertTrue(filteredCount <= initialCount, 
            "Applying max price filter should reduce or maintain car count");
    }

    @Test
    void goToAddCar() throws InterruptedException {
        carListPage.clickAddCar();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains(BASE_URL+"/car-form"));

        assertTrue(driver.getCurrentUrl().contains(BASE_URL+"/car-form"));
    }

//    @Test
//    void isFirstCarModel(){
//        carListPage.isFirstCarModel("Porsche");
//    }
}
