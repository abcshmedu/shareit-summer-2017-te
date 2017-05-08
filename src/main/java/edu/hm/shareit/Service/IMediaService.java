package edu.hm.shareit.Service;

import edu.hm.shareit.DTO.Book;
import edu.hm.shareit.DTO.Disc;
import edu.hm.shareit.DTO.Medium;
import edu.hm.shareit.result.MediaServiceResult;

/**
 * Interface of media service business logic.
 * 
 * @author Dmitry Dorodnov
 *
 */
public interface IMediaService {

    /**
     * Adds a Book to the database.
     * 
     * @param book
     *            Book object.
     * @return Status code 200 if successful. Code 400 if failed and json object
     *         with error message as "detail" property.
     */
    MediaServiceResult addBook(Book book);

    /**
     * Adds a Disc to the database.
     * 
     * @param disc
     *            Disc object.
     * @return Status code 200 if successful. Code 400 if failed and json object
     *         with error message as "detail" property.
     */
    MediaServiceResult addDisc(Disc disc);

    /**
     * Lists all books.
     * 
     * @return Array of Books.
     */
    Medium[] getBooks();

    /**
     * Lists all discs.
     * 
     * @return Array of discs.
     */
    Medium[] getDiscs();

    /**
     * Returns a Book object by given isbn.
     * 
     * @param isbn
     *            of a Book.
     * @return Book object.
     */
    Medium getBookByISBN(String isbn);

    /**
     * Returns a Disc object by given barcode.
     * 
     * @param barcode
     *            of a disc.
     * @return Disc object.
     */
    Medium getDiscByBarcode(String barcode);

    /**
     * Updates a Book by given isbn.
     * 
     * @param book
     *            Book object with new data.
     * @param isbn
     *            of existing Book.
     * @return Status code 200 if successful. Code 400 if failed and json object
     *         with error message as "detail" property.
     */
    MediaServiceResult updateBook(Book book, String isbn);

    /**
     * Updates a Disc by given barcode.
     * 
     * @param disc
     *            Disc object with new data.
     * @param barcode
     *            of existing Disc.
     * @return Status code 200 if successful. Code 400 if failed and json object
     *         with error message as "detail" property.
     */
    MediaServiceResult updateDisc(Disc disc, String barcode);

}
