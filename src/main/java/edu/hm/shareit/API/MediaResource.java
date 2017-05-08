package edu.hm.shareit.API;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.google.gson.Gson;

import edu.hm.shareit.DTO.Book;
import edu.hm.shareit.DTO.Disc;
import edu.hm.shareit.DTO.Medium;
import edu.hm.shareit.Service.IMediaService;
import edu.hm.shareit.Service.MediaServiceImpl;
import edu.hm.shareit.result.MediaServiceResult;

/**
 * API for mediaservice.
 * 
 * @author Dmitry Dorodnov
 *
 */
@Singleton
@Path("media")
public class MediaResource {

	private IMediaService service;
	// private MediaServiceImpl service;

	public MediaResource() {
		this.service = new MediaServiceImpl();
	}

	@Path("books")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooks() {
		try {
			Medium[] books = this.service.getBooks();
			return Response.ok(new Gson().toJson(books), MediaType.APPLICATION_JSON).build();
		} catch (Exception exception) {
			return Response.serverError().build();
		}
	}
	
	@Path("discs")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiscs() {
		try {
			Medium[] discs = this.service.getDiscs();
			return Response.ok(new Gson().toJson(discs), MediaType.APPLICATION_JSON).build();
		} catch (Exception exception) {
			return Response.serverError().build();
		}
	}

	@Path("books")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createBook(Book book) {
		Response result;
		try {
			MediaServiceResult msr = this.service.addBook(book);
			if (msr.getCode() == 200) {
				result = Response.ok().build();
			} else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("detail", msr.getMessage());
				result = Response.status(msr.getStatus()).entity(jsonObject.toString()).build();
			}
		} catch (Exception e) {
			result = Response.serverError().build();
		}

		return result;
	}
	
	@Path("discs")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDisc(Disc disc) {
		Response result;
		try {
			MediaServiceResult msr = this.service.addDisc(disc);
			if (msr.getCode() == 200) {
				result = Response.ok().build();
			} else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("detail", msr.getMessage());
				result = Response.status(msr.getStatus()).entity(jsonObject.toString()).build();
			}
		} catch (Exception e) {
			result = Response.serverError().build();
		}

		return result;
	}

	@Path("books/{isbn}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getBook(@PathParam("isbn") String isbn) {
		Response result;

		try {
			Medium book = this.service.getBookByISBN(isbn);
			result = Response.ok(new Gson().toJson(book)).build();

		} catch (Exception exception) {
			result = Response.serverError().build();
		}

		return result;

	}
	
	@Path("discs/{barcode}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getDisc(@PathParam("barcode") String barcode) {
		Response result;

		try {
			Medium disc = this.service.getDiscByBarcode(barcode);
			result = Response.ok(new Gson().toJson(disc)).build();

		} catch (Exception exception) {
			result = Response.serverError().build();
		}

		return result;

	}

	@Path("/books/{isbn}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateBook(@PathParam("isbn") String isbn, Book book) {
		Response result;

		try {
			MediaServiceResult msr = this.service.updateBook(book, isbn);
			if (msr.getCode() == 200) {
				result = Response.ok().build();
			} else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("detail", msr.getMessage());
				result = Response.status(msr.getStatus()).entity(jsonObject.toString()).build();
			}
		} catch (Exception exception) {
			result = Response.serverError().build();
		}

		return result;
	}
	
	@Path("/discs/{barcode}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDisc(@PathParam("barcode") String barcode, Disc disc) {
		Response result;

		try {
			MediaServiceResult msr = this.service.updateDisc(disc, barcode);
			if (msr.getCode() == 200) {
				result = Response.ok().build();
			} else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("detail", msr.getMessage());
				result = Response.status(msr.getStatus()).entity(jsonObject.toString()).build();
			}
		} catch (Exception exception) {
			result = Response.serverError().build();
		}

		return result;
	}
}
