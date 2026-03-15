package com.example.springboot;

import com.example.springboot.pages.CarListPage;
import com.example.springboot.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarWorkflowTest extends BaseTest{

    private Long createdCarId;

    @Test
    @DisplayName("API ile eklenen araç UI listesinde doğru görünmeli")
    void testE2E_ApiCreateAndUiVerify(){
        //objectleri oluştur
        LoginPage loginpage = new LoginPage(driver);
        CarListPage carListPage = new CarListPage(driver);

        //api ile aracı oluştur
        createdCarId = ApiUtils.createTestCar();
        System.out.println("DEBUG: Araç API üzerinden başarıyla kaydedildi, ID: " + createdCarId);

        //ui dan siteye giriş yap
        driver.get("http://localhost:4160/login-screen");
        loginpage.login("mina@gmail.com", "mina123");

        //doğrulama aşaması
        int count = carListPage.getVisibleCarCount();
        System.out.println("DEBUG: Tablodaki toplam araç sayısı:" + count);

        boolean isCarVisible = driver.getPageSource().contains("Peugeot");

        assertTrue(isCarVisible, "API ile eklenen araç UI tablosunda bulunamadı");
    }

    @AfterEach
    public void cleanupTestCar() {
        if (createdCarId != null) {
            System.out.println("DEBUG: Test sonrası oluşturulan test aracı siliniyor, ID: " + createdCarId);
            try {
                ApiUtils.deleteTestCar(createdCarId);
            } catch (Exception e) {
                System.err.println("WARN: Test aracı silinirken hata oluştu: " + e.getMessage());
            }
        }
    }
}
