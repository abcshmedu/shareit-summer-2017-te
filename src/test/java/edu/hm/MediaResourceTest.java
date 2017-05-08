package edu.hm;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import edu.hm.shareit.API.MediaResource;
import edu.hm.shareit.DTO.Book;

/**
 * Tests for media service.
 * 
 * @author Dmitry Dorodnov
 *
 */
public class MediaResourceTest {

	 private MediaResource API;
	// private Renderer renderer;

	@Before
	public void setUp() {
		 API = new MediaResource();
		// renderer = new Renderer(toRender);
	}

	@Test
	public void testCreateBook() {
		Book b = new Book("Author", "978-3-12-732320-7", "title");
		Response r = API.createBook(b);
		assertEquals(r.getStatus(), 200);
	}
	
	@Test
	public void testGetBooks() {
		Book b = new Book("Author", "978-3-12-732320-7", "title");
		Response r = API.createBook(b);
		Response rr = API.getBooks();
		assertEquals(rr.getStatus(), 200);
	}
}
