package com.example.springboot.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    // 1. Sayfadaki elemanların adreslerini (Locators) burada tanımlıyoruz
    By emailField = By.cssSelector("input[formControlName='email']");
    By passwordField = By.cssSelector("input[formControlName='password']");
    By loginBtn = By.cssSelector("button[type='submit']");

    // Constructor: Sayfa her açıldığında driver'ı buna tanıtıyoruz
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // 2. Sayfada yapılabilecek aksiyonları (Methods) burada tanımlıyoruz
    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }
}
