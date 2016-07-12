package io.pivotal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FlowDemoApplication.class)
@WebAppConfiguration
public class CreateAccountMockMvcTest {

    @Autowired
    private StatefulMockMvc mvc;

    @Test
    public void shouldAllowACustomerToCreateAnAccount() throws Exception {
        MvcResult page;

        page = mvc.perform(mvc.get("/createAccount"));

        assertThat(mvc.getPath(), equalTo("/createAccount"));
        assertThat(page.getResponse().getStatus(), equalTo(HttpServletResponse.SC_OK));
        assertThat(JspView.renderedFor(page), equalTo("enterCustomerDetails"));

        page = mvc.perform(mvc.postback()
                .param("owner", "Alice")
                .param("_eventId_submit", ""));

        assertThat(mvc.getPath(), equalTo("/createAccount"));
        assertThat(page.getResponse().getStatus(), equalTo(HttpServletResponse.SC_OK));
        assertThat(JspView.renderedFor(page), equalTo("chooseAccountType"));

        page = mvc.perform(mvc.postback()
                .param("type", AccountType.PREMIER.name())
                .param("_eventId_submit", ""));

        assertThat(mvc.getPath(), equalTo("/createAccount"));
        assertThat(page.getResponse().getStatus(), equalTo(HttpServletResponse.SC_OK));
        assertThat(JspView.renderedFor(page), equalTo("requestOverdraft"));

        page = mvc.perform(mvc.postback()
                .param("overdraft", "5000")
                .param("_eventId_submit", ""));

        assertThat(mvc.getPath(), equalTo("/accounts"));
        assertThat(page.getResponse().getStatus(), equalTo(HttpServletResponse.SC_OK));
        assertThat(JspView.renderedFor(page), equalTo("listAccounts"));
        @SuppressWarnings("unchecked")
        Collection<Account> accounts = (Collection<Account>) page.getModelAndView().getModel().get("accounts");
        assertThat(accounts, hasSize(1));
        assertThat(accounts.iterator().next().getOwner(), equalTo("Alice"));
    }

}
