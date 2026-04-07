package com.example.springboot.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CarListPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final int DEFAULT_TIMEOUT = 10;

    private final By tableRows = By.cssSelector("tr.mat-mdc-row");
    private final By minPriceInput = By.cssSelector("input[data-testid='min-price-input']");
    private final By maxPriceInput = By.cssSelector("input[data-testid='max-price-input']");
    private final By brandOptions = By.cssSelector(".brand");
    private final By addButton = By.cssSelector(".fab-add-car");
    private final By firstCarBrandName = By.xpath("//tr[1]/td[contains(@class, 'mat-column-brand')]");

    public CarListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    public int getVisibleCarCount() {
        wait.until(ExpectedConditions.presenceOfElementLocated(tableRows));
        return driver.findElements(tableRows).size();
    }

    public void filterByMinPrice(String price) {
        WebElement minPriceElement = wait.until(ExpectedConditions.elementToBeClickable(minPriceInput));
        minPriceElement.clear();
        minPriceElement.sendKeys(price);
    }

    public void filterByMaxPrice(String price) {
        WebElement maxPriceElement = wait.until(ExpectedConditions.elementToBeClickable(maxPriceInput));
        maxPriceElement.clear();
        maxPriceElement.sendKeys(price);
    }

    public void applyFilters(String minPrice, String maxPrice) {
        filterByMinPrice(minPrice);
        filterByMaxPrice(maxPrice);
        waitForTableUpdate();
    }

    public void waitForTableUpdate() {
        try {
            Thread.sleep(500);
            wait.until(ExpectedConditions.presenceOfElementLocated(tableRows));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Table update wait interrupted", e);
        }
    }

    public boolean isCarListDisplayed() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(tableRows)) != null;
    }

    public void clickAddCar() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.urlContains("/car-list"));
        wait.until(ExpectedConditions.presenceOfElementLocated(tableRows));
    }

//    public void isFirstCarModel(String brand) {
//        wait.until(ExpectedConditions.presenceOfElementLocated(brand.equals(firstCarBrandName));
//    }
    //test yazmaya devam brand kontolr
}
