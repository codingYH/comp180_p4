package jrails;

import books.BookController;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class JRouterTest {

    private JRouter jRouter;

    @Before
    public void setUp() throws Exception {
        jRouter = new JRouter();
    }

    @Test
    public void addRoute() {
        jRouter.addRoute("GET", "/", BookController.class, "index");
        assertThat(jRouter.getRoute("GET", "/"), is("BookController#index"));
    }

    @Test
    public void create() {
        jRouter.addRoute("GET", "/", BookController.class, "create");
        HashMap<String, String> tMap = new HashMap<>();
        tMap.put("title", "Comp180");
        tMap.put("author", "Yang He");
        tMap.put("num_copies", "1");
        assertThat(jRouter.route("GET", "/", tMap), notNullValue());
    }
}