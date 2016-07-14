package io.pivotal;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

class WebDriverRule implements MethodRule, DriverProvider {

    private WebDriver driver;

    @Override
    public Statement apply(final Statement base, FrameworkMethod method, Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                driver = new FirefoxDriver();
                try {
                    base.evaluate();
                } finally {
                    driver.quit();
                    driver = null;
                }
            }
        };
    }

    @Override
    public WebDriver getDriver() {
        if (driver == null) throw new IllegalStateException("driver is not initialized");
        return driver;
    }

}
