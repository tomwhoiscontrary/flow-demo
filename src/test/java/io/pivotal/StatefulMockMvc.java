package io.pivotal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class StatefulMockMvc {

    private final MockMvc mockMvc;
    private final MockHttpSession session;
    private String location;

    @Autowired
    public StatefulMockMvc(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        session = new MockHttpSession();
    }

    /**
     * @return the location, which is the path plus the query string, if there is one.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return the path, without any query string.
     */
    public String getPath() {
        return URI.create(location).getPath();
    }

    public MockHttpServletRequestBuilder get(String path) {
        location = path;
        return MockMvcRequestBuilders.get(path);
    }

    public MockHttpServletRequestBuilder post(String path) {
        location = path;
        return MockMvcRequestBuilders.post(path);
    }

    /**
     * Create a {@link MockHttpServletRequestBuilder} for a POST request back to the current page.
     */
    public MockHttpServletRequestBuilder postback() {
        return post(location);
    }

    /**
     * Perform a request, using the current session, follow any redirects, and return the result.
     *
     * @see MockMvc#perform(RequestBuilder)
     */
    public MvcResult perform(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        MvcResult result = mockMvc.perform(requestBuilder.session(session)).andReturn();
        MockHttpServletResponse response = result.getResponse();
        if (isRedirect(response.getStatus())) {
            return perform(get(response.getRedirectedUrl()));
        } else {
            return result;
        }
    }

    private boolean isRedirect(int status) {
        return status == HttpServletResponse.SC_MOVED_PERMANENTLY
                || status == HttpServletResponse.SC_FOUND
                || status == HttpServletResponse.SC_SEE_OTHER
                || status == HttpServletResponse.SC_TEMPORARY_REDIRECT;
    }

}
