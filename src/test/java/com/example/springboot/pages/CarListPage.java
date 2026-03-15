package com.example.springboot.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CarListPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By tableRows = By.cssSelector("tr.mat-mdc-row"); // Tablodaki her bir araç satırı
    By minPriceInput = By.cssSelector("input[placeholder='Min Price']"); // Veya mat-label üzerinden
    By brandOptions = By.cssSelector(".brand"); // Marka listesindeki seçenekler
    By addButton = By.cssSelector(".fab-add-car"); // Sağ alttaki araç ekleme butonu
    By firstCarBrandName = By.xpath("//tr[1]/td[contains(@class, 'mat-column-brand')]");

    public CarListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public int getVisibleCarCount() {
        // Tablo satırlarının yüklenmesini bekle
        wait.until(ExpectedConditions.presenceOfElementLocated(tableRows));
        return driver.findElements(tableRows).size();
    }

    public void filterByMinPrice(String price) {
        WebElement minPrice = driver.findElement(By.cssSelector("input[type='number'][step='10000']")); // Min Price inputu
        minPrice.clear();
        minPrice.sendKeys(price);
    }

    public void clickAddCar() {
        driver.findElement(addButton).click();
    }
}
