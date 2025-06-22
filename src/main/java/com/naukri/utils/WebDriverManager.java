// src/main/java/com/naukri/utils/WebDriverManager.java
package com.naukri.utils;

import org.openqa.selenium.WebDriver;

public class WebDriverManager {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}