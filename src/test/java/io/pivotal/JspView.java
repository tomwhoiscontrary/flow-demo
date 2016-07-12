package io.pivotal;

import org.springframework.test.web.servlet.MvcResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.fail;

public class JspView {

    private static final Pattern VIEW_PATTERN = Pattern.compile("^/WEB-INF/jsp/(.*).jsp$");

    public static String renderedFor(MvcResult page) {
        String forwardedUrl = page.getResponse().getForwardedUrl();
        if (forwardedUrl == null) fail("no view was rendered");
        Matcher matcher = VIEW_PATTERN.matcher(forwardedUrl);
        if (!matcher.matches()) fail("unrecognized view was rendered: " + forwardedUrl);
        return matcher.group(1);
    }

}
