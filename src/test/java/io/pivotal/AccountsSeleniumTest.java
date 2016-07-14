package io.pivotal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FlowDemoApplication.class)
@WebIntegrationTest(randomPort = true)
public class AccountsSeleniumTest {

    @Rule
    public WebDriverRule webDriverRule = new WebDriverRule();

    @Rule
    public MethodRule dumpSourceOnFailureRule = new DumpSourceOnFailureRule(webDriverRule);

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private AccountRepository accountRepository;

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = webDriverRule.getDriver();
    }

    @Test
    public void displaysAccountDetails() throws Exception {
        accountRepository.save(new Account(12345678, 113344, "Alice", AccountType.PREMIER, 5000));

        driver.navigate().to("http://localhost:" + port + "/accounts");

        WebElement accountRow = driver.findElement(byQa("account-Alice"));
        assertThat(accountRow.findElement(byQa("sortCode")).getText(), equalTo("113344"));
        assertThat(accountRow.findElement(byQa("number")).getText(), equalTo("12345678"));
        assertThat(accountRow.findElement(byQa("owner")).getText(), equalTo("Alice"));
        assertThat(accountRow.findElement(byQa("type")).getText(), equalTo("premier"));
        assertThat(accountRow.findElement(byQa("overdraft")).getText(), equalTo("5000"));
    }

    private By byQa(String qa) {
        return By.xpath("//*[@data-qa='" + qa + "']");
    }

}
