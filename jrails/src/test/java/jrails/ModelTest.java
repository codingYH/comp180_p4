package jrails;

import books.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class ModelTest {

    private Model model;

    @Before
    public void setUp() throws Exception {
        model = new Model(){};
        Model.reset();
    }

    @Test
    public void id() {
        assertThat(model.id(), notNullValue());
    }

    @Test
    public void save() {
        Book book1 = new Book();
        book1.author = "";
        book1.title ="title1";
        book1.save();
        Book book2 = new Book();
        book2.title ="title2";
        book2.num_copies = 3;
        book2.save();
        book1.author = "author1_changed";
        book1.title ="title1_changed";
        book1.save();
        Book book3 = new Book();
        book3.author = "author3";
        book3.title ="title3";
        book3.num_copies = 10;
        book3.save();
        book1 = Model.find(Book.class, book1.id);
        book2 = Model.find(Book.class, book2.id);
        Book nonFind = Model.find(Book.class, 123);
        System.out.println(book1.title+ "," + book1.author+ ","+ book1.num_copies + "," +book1.id);
        System.out.println(book2.title+ "," + book2.author+ "," + book2.num_copies+ "," +book2.id);
        book2.destroy();
        List<Book> l = Model.all(Book.class);
        book3.destroy();
//        Model.reset();
    }

    @After
    public void tearDown() throws Exception {
    }
}