package com.example.springboot.pages;

import com.example.springboot.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest { // BaseTest'ten driver'ı aldığını varsayıyoruz

    @Test
    void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        driver.get("http://localhost:4160/login-screen");

        loginPage.login("mina@gmail.com", "mina123");

        // YENİ: URL'in değişmesini bekle (Maksimum 10 saniye)
        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5));

        // URL "/car-list" içerene kadar kodu burada bekletir
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/car-list"));

        // Şimdi kontrol et
        assertTrue(driver.getCurrentUrl().contains("/car-list"));
    }

    @Test
    void testLoginWithWrongPassword() {
        LoginPage loginPage = new LoginPage(driver);
        driver.get("http://localhost:4160/login-screen");

        loginPage.login("mina@gmail.com","yanlişSifre123");

        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5));

        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/login-screen"));


        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();

        assertEquals("Giriş başarısız. Lütfen bilgileri kontrol edin.", alertText);
        alert.accept();

        assertTrue(driver.getCurrentUrl().contains("/login-screen"));
    }
}
