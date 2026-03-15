package com.example.springboot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setup() {
        // Her testten önce tertemiz bir tarayıcı açar
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }


    @AfterEach
    public void tearDown() {
        // Test bittiğinde tarayıcıyı kapatır (Bellek temizliği)
        if (driver != null) {
            driver.quit();
        }
    }
}
