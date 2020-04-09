package jrails;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.*;

public class HtmlTest {

    private Html html;

    @Before
    public void setUp() throws Exception {
        html = new Html();
    }

    @Test
    public void empty() {
        assertThat(html.empty().toString(), isEmptyString());
    }

    @Test
    public void link_to() {
        assertEquals(html.link_to("Show", "/show?id=42").toString(), "<a href=\"/show?id=42\">Show</a>");
    }
    @Test
    public void form() {
        assertEquals(html.form("/create", html).toString(), "<form action=\"/create\" accept-charset=\"UTF-8\" method=\"post\"></form>");
    }
    @Test
    public void submit() {
        assertEquals(html.submit("Save").toString(), "<input type=\"submit\" value=\"Save\"/>");
    }
}