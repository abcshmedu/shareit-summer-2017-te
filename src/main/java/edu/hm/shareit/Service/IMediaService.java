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
	
	public MediaServiceResult addBook(Book book);	
	public MediaServiceResult addDisc(Disc disc);
	
	public Medium[] getBooks();
	public Medium[] getDiscs();
	
	public Medium getBookByISBN(String isbn);
	public Medium getDiscByBarcode(String barcode);
	
	public MediaServiceResult updateBook(Book book, String isbn);	
	public MediaServiceResult updateDisc(Disc disc, String barcode);
	
}
