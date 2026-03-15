package com.example.springboot.pages;

import com.example.springboot.BaseTest;
import org.junit.jupiter.api.Test;
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
}
