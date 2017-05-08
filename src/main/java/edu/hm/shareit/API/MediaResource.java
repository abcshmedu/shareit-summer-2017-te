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
import javax.ws.rs.core.Response.Status;

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

    /**
     * Default constructor.
     */
    public MediaResource() {
        this.service = new MediaServiceImpl();
    }

    /**
     * Lists all books.
     * 
     * @return Response object with books as json.
     */
    @Path("books")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        Response result;

        try {
            Medium[] books = this.service.getBooks();
            result = Response.ok(new Gson().toJson(books), MediaType.APPLICATION_JSON).build();
        } catch (Exception exception) {
            result = Response.serverError().build();
        }

        return result;
    }

    /**
     * Lists all discs.
     * 
     * @return Response object with discs as json.
     */
    @Path("discs")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscs() {
        Response result;

        try {
            Medium[] discs = this.service.getDiscs();
            result = Response.ok(new Gson().toJson(discs), MediaType.APPLICATION_JSON).build();
        } catch (Exception exception) {
            result = Response.serverError().build();
        }

        return result;
    }

    /**
     * Adds a Book to the database.
     * 
     * @param book
     *            Book object.
     * @return Response with status code 200 if successful. Code 400 if failed
     *         and json object with error message as "detail" property.
     */
    @Path("books")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        Response result;

        try {
            MediaServiceResult msr = this.service.addBook(book);
            if (msr.getCode() == Status.OK.getStatusCode()) {
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

    /**
     * Adds a Disc to the database.
     * 
     * @param disc
     *            Disc object.
     * @return Response with status code 200 if successful. Code 400 if failed
     *         and json object with error message as "detail" property.
     */
    @Path("discs")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDisc(Disc disc) {
        Response result;

        try {
            MediaServiceResult msr = this.service.addDisc(disc);
            if (msr.getCode() == Status.OK.getStatusCode()) {
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

    /**
     * Returns a Book object by given isbn.
     * 
     * @param isbn
     *            isbn of book.
     * @return Response with status code 200 and Book as json. Status 400 if
     *         some error occurred.
     */
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

    /**
     * Returns a Disc object by given barcode.
     * 
     * @param barcode
     *            of Disc.
     * @return Response with status code 200 and Disc as json. Status 400 if
     *         some error occurred.
     */
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

    /**
     * Updates a Book by given isbn.
     * 
     * @param isbn
     *            of a book.
     * @param book
     *            object with new data.
     * @return Status code 200 if successful. Code 400 if failed and json object
     *         with error message as "detail" property.
     */
    @Path("/books/{isbn}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("isbn") String isbn, Book book) {
        Response result;

        try {
            MediaServiceResult msr = this.service.updateBook(book, isbn);
            if (msr.getCode() == Status.OK.getStatusCode()) {
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

    /**
     * Adds a Disc to the database.
     * 
     * @param barcode
     *            of a disc.
     * @param disc
     *            object with new data.
     * @return Status code 200 if successful. Code 400 if failed and json object
     *         with error message as "detail" property.
     */
    @Path("/discs/{barcode}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDisc(@PathParam("barcode") String barcode, Disc disc) {
        Response result;

        try {
            MediaServiceResult msr = this.service.updateDisc(disc, barcode);
            if (msr.getCode() == Status.OK.getStatusCode()) {
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
