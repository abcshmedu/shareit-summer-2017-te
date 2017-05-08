package edu.hm;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.hm.shareit.API.MediaResource;
import edu.hm.shareit.DTO.Book;
import edu.hm.shareit.DTO.Disc;
import edu.hm.shareit.result.MediaServiceResult;

/**
 * Tests for media service.
 * 
 * @author Dmitry Dorodnov
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MediaResourceTest {

    private static MediaResource api;
    // private static boolean setUpIsDone = false;

    private final String author = "ADAM FREEMAN";
    private final String isbn = "978-1-4842-2307-9";
    private final String title = "Pro Angular";

    private final String barcode = "17T53HB3I5139";
    private final String director = "Jon S. Baird";
    private final String dtitle = "Filth";
    private final int fsk = 16;

    /**
     * Create media resource api for all tests.
     */
    @BeforeClass
    public static void setUpClass() {
        api = new MediaResource();
    }

    /**
     * Test create book.
     */
    @Test
    public void test1CreateBook() {
        Book b = new Book(this.author, this.isbn, this.title);
        Response r = api.createBook(b);
        assertEquals(r.getStatus(), Status.OK.getStatusCode());

        r = api.createBook(new Book(null, "978-3-12-732320-7", "title"));
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse((String) r.getEntity()).getAsJsonObject();
        assertEquals(json.get("detail").getAsString(), MediaServiceResult.BOOK_DATA_INCOMPLETE.getMessage());

        r = api.createBook(new Book("Author", "978-3-12-732320-6", "title"));
        json = parser.parse((String) r.getEntity()).getAsJsonObject();
        assertEquals(json.get("detail").getAsString(), MediaServiceResult.ISBN_INVALID.getMessage());

        r = api.createBook(b);
        json = parser.parse((String) r.getEntity()).getAsJsonObject();
        assertEquals(json.get("detail").getAsString(), MediaServiceResult.ISBN_DUPLICATED.getMessage());
    }

    /**
     * Test get books.
     */
    @Test
    public void test2GetBooks() {
        Response r = api.getBooks();

        String jsonStr = (String) r.getEntity();
        Gson gson = new Gson();
        Book[] ms = gson.fromJson(jsonStr, Book[].class);
        Book b = ms[0];

        assertEquals(b.getAuthor(), this.author);
        assertEquals(b.getIsbn(), this.isbn);
        assertEquals(b.getTitle(), this.title);
    }

    /**
     * Test get book.
     */
    @Test
    public void test3GetBook() {
        Response r = api.getBook(this.isbn);
        assertEquals(r.getStatus(), Status.OK.getStatusCode());

        String jsonStr = (String) r.getEntity();
        Gson gson = new Gson();
        Book b = gson.fromJson(jsonStr, Book.class);

        assertEquals(b.getAuthor(), this.author);
        assertEquals(b.getIsbn(), this.isbn);
        assertEquals(b.getTitle(), this.title);
    }

    /**
     * Test update Book.
     */
    @Test
    public void test4UpdateBook() {
        Book b = new Book(this.author, "923892349348", this.title);
        Response r = api.updateBook("923892349348", b);

        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse((String) r.getEntity()).getAsJsonObject();
        assertEquals(json.get("detail").getAsString(), MediaServiceResult.ISBN_NOT_FOUND.getMessage());

        r = api.updateBook("2384782347", new Book(this.author, this.isbn, this.title));
        json = parser.parse((String) r.getEntity()).getAsJsonObject();
        assertEquals(MediaServiceResult.ISBN_CONFLICT.getMessage(), json.get("detail").getAsString());

        r = api.updateBook(this.isbn, new Book("", this.isbn, ""));
        json = parser.parse((String) r.getEntity()).getAsJsonObject();
        assertEquals(json.get("detail").getAsString(), MediaServiceResult.BOOK_DATA_INCOMPLETE.getMessage());

        r = api.updateBook(this.isbn, new Book(this.author, this.isbn, this.title + " (changed)"));
        assertEquals(r.getStatus(), Status.OK.getStatusCode());

        r = api.getBook(this.isbn);

        String jsonStr = (String) r.getEntity();
        Gson gson = new Gson();
        b = gson.fromJson(jsonStr, Book.class);

        assertEquals(b.getTitle(), this.title + " (changed)");
    }

    /**
     * Test create disc.
     */
    @Test
    public void test1CreateDisc() {
        Disc d = new Disc(this.barcode, this.director, this.fsk, this.dtitle);
        Response r = api.createDisc(d);
        assertEquals(r.getStatus(), Status.OK.getStatusCode());

        r = api.createDisc(new Disc(this.barcode, "", this.fsk, this.dtitle));
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse((String) r.getEntity()).getAsJsonObject();
        assertEquals(json.get("detail").getAsString(), MediaServiceResult.DISC_DATA_INCOMPLETE.getMessage());

        r = api.createDisc(new Disc(this.barcode, this.director, this.fsk, this.dtitle));
        json = parser.parse((String) r.getEntity()).getAsJsonObject();
        assertEquals(json.get("detail").getAsString(), MediaServiceResult.BARCODE_DUPLICATED.getMessage());

        r = api.createDisc(new Disc("sdfhgh", this.director, this.fsk, this.dtitle));
        json = parser.parse((String) r.getEntity()).getAsJsonObject();
        assertEquals(json.get("detail").getAsString(), MediaServiceResult.BARCODE_INVALID.getMessage());
    }

    /**
     * Test get discs.
     */
    @Test
    public void test2GetDiscs() {
        Response r = api.getDiscs();

        String jsonStr = (String) r.getEntity();
        Gson gson = new Gson();
        Disc[] ms = gson.fromJson(jsonStr, Disc[].class);
        Disc b = ms[0];

        assertEquals(b.getBarcode(), this.barcode);
        assertEquals(b.getDirector(), this.director);
        assertEquals(b.getTitle(), this.dtitle);
        assertEquals(b.getFsk(), this.fsk);
    }

    /**
     * Test get disc.
     */
    @Test
    public void test3GetDisc() {
        Response r = api.getDisc(this.barcode);
        assertEquals(r.getStatus(), Status.OK.getStatusCode());

        String jsonStr = (String) r.getEntity();
        Gson gson = new Gson();
        Disc b = gson.fromJson(jsonStr, Disc.class);

        assertEquals(b.getBarcode(), this.barcode);
        assertEquals(b.getDirector(), this.director);
        assertEquals(b.getTitle(), this.dtitle);
        assertEquals(b.getFsk(), this.fsk);
    }

    /**
     * Test update Disc.
     */
    @Test
    public void test4UpdateDisc() {
        final int badFSK = -10;
        Disc d = new Disc("sdhfhsd", this.director, this.fsk, this.dtitle);
        Response r = api.updateDisc(this.barcode, d);

        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse((String) r.getEntity()).getAsJsonObject();
        assertEquals(MediaServiceResult.BARCODE_NOT_FOUND.getMessage(), json.get("detail").getAsString());

        r = api.updateDisc("2384782347", new Disc(this.barcode, this.director, this.fsk, this.dtitle));
        json = parser.parse((String) r.getEntity()).getAsJsonObject();
        assertEquals(MediaServiceResult.BARCODE_CONFLICT.getMessage(), json.get("detail").getAsString());

        r = api.updateDisc(this.barcode, new Disc(this.barcode, "", badFSK, ""));
        json = parser.parse((String) r.getEntity()).getAsJsonObject();
        assertEquals(MediaServiceResult.DISC_DATA_INCOMPLETE.getMessage(), json.get("detail").getAsString());

        r = api.updateDisc(this.barcode, new Disc(this.barcode, this.director, this.fsk, this.dtitle + " (changed)"));
        assertEquals(r.getStatus(), Status.OK.getStatusCode());

        r = api.getDisc(this.barcode);

        String jsonStr = (String) r.getEntity();
        Gson gson = new Gson();
        d = gson.fromJson(jsonStr, Disc.class);

        assertEquals(d.getTitle(), this.dtitle + " (changed)");
    }

    /**
     * Test DTOs.
     */
    @Test
    public void testDTO() {
        Book b = new Book(this.author, this.isbn, this.title);
        Book bb = new Book(this.author, this.isbn, this.title);
        assertEquals(b, bb);
        assertEquals(b.hashCode(), bb.hashCode());

        Disc d = new Disc(this.barcode, this.director, this.fsk, this.dtitle);
        Disc dd = new Disc(this.barcode, this.director, this.fsk, this.dtitle);
        assertEquals(d, dd);
        assertEquals(d.hashCode(), dd.hashCode());
    }
}
