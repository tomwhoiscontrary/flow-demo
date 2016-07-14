package io.pivotal;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.WebDriver;

class DumpSourceOnFailureRule implements MethodRule {

    private final DriverProvider driverProvider;

    public DumpSourceOnFailureRule(DriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    @Override
    public Statement apply(final Statement base, FrameworkMethod method, Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                boolean success = false;
                try {
                    base.evaluate();
                    success = true;
                } finally {
                    if (!success) {
                        WebDriver driver = driverProvider.getDriver();
                        System.err.println("========== " + driver.getCurrentUrl());
                        System.err.println(driver.getPageSource());
                        System.err.println("==========");
                    }
                }
            }
        };
    }

}
