package io.pivotal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FlowDemoApplication.class)
@WebAppConfiguration
public class CreateAccountMockMvcTest {

    @Autowired
    private StatefulMockMvc mvc;

    @Test
    public void shouldAllowACustomerToCreateAnAccount() throws Exception {
        MockHttpServletResponse page;

        page = mvc.perform(mvc.get("/createAccount"));

        assertThat(mvc.getPath(), equalTo("/createAccount"));
        assertThat(page.getStatus(), equalTo(HttpServletResponse.SC_OK));
        assertThat(page.getContentAsString(), containsString("<title>Create Account: Customer Details</title>"));

        page = mvc.perform(mvc.postback()
                .param("owner", "Alice")
                .param("_eventId_submit", ""));

        assertThat(mvc.getPath(), equalTo("/createAccount"));
        assertThat(page.getStatus(), equalTo(HttpServletResponse.SC_OK));
        assertThat(page.getContentAsString(), containsString("<title>Create Account: Choose Account Type</title>"));

        page = mvc.perform(mvc.postback()
                .param("type", AccountType.PREMIER.name())
                .param("_eventId_submit", ""));

        assertThat(mvc.getPath(), equalTo("/createAccount"));
        assertThat(page.getStatus(), equalTo(HttpServletResponse.SC_OK));
        assertThat(page.getContentAsString(), containsString("<title>Create Account: Overdraft</title>"));

        page = mvc.perform(mvc.postback()
                .param("overdraft", "5000")
                .param("_eventId_submit", ""));

        assertThat(mvc.getPath(), equalTo("/accounts"));
        assertThat(page.getStatus(), equalTo(HttpServletResponse.SC_OK));
        assertThat(page.getContentAsString(), allOf(
                containsString("<title>Accounts</title>"),
                containsString("<td>Alice</td>")
        ));
    }

}
