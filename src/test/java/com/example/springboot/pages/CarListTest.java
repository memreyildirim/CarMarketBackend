package com.example.springboot.pages;

import com.example.springboot.BaseTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarListTest extends BaseTest {

    @Test
    void testCarFilteringAndListing() {
        LoginPage loginPage = new LoginPage(driver);
        CarListPage carListPage = new CarListPage(driver);

        // 1. Giriş Yap
        driver.get("http://localhost:4160/login-screen");
        loginPage.login("mina@gmail.com", "mina123");

        // 2. Sayfanın yüklendiğini doğrula,
        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/car-list"));

        // 3. Başlangıçtaki araç sayısını al
        int initialCount = carListPage.getVisibleCarCount();
        System.out.println("Başlangıçt healthiest araç sayısı: " + initialCount);
        assertTrue(initialCount > 0, "Tablo boş gelmemeli!");

        // 4. Filtreleme Testi (Negatif/Sınır Testi)
        // Çok yüksek bir fiyat girerek listenin daraldığını görelim
        carListPage.filterByMinPrice("9999999");

        // Tablonun güncellenmesi için kısa bir bekleme (Angular asenkron olduğu için)
        // Normalde burada 'spinner' beklemek daha profesyoneldir
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        int filteredCount = carListPage.getVisibleCarCount();
        System.out.println("Filtre sonrası araç sayısı: " + filteredCount);
        assertTrue(filteredCount < initialCount, "Filtreleme sonrası araç sayısı azalmalıydı!");
    }
}
