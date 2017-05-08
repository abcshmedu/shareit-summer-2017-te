package edu.hm.shareit.Service;

import java.util.HashMap;
import java.util.Map;

import edu.hm.shareit.DTO.Book;
import edu.hm.shareit.DTO.Disc;
import edu.hm.shareit.DTO.Medium;
import edu.hm.shareit.result.MediaServiceResult;

/**
 * Business logic of media service.
 * 
 * @author Dmitry Dorodnov
 *
 */
public class MediaServiceImpl implements IMediaService {

	private Map<String, Book> books;
	private Map<String, Disc> discs;

	public MediaServiceImpl() {
		this.books = new HashMap<>();
		this.discs = new HashMap<>();
	}

	@Override
	public MediaServiceResult addBook(Book book) {
		if (book == null || book.getAuthor() == null || book.getAuthor().isEmpty() || book.getTitle() == null
				|| book.getTitle().isEmpty()) {
			return MediaServiceResult.BOOK_DATA_INCOMPLETE;
		} else if (book.getIsbn() == null || book.getIsbn().isEmpty() || !this.checkISBN(book.getIsbn())) {
			return MediaServiceResult.ISBN_INVALID;
		} else if (this.books.get(book.getIsbn()) != null) {
			return MediaServiceResult.ISBN_DUPLICATED;
		}

		this.books.put(book.getIsbn(), book);

		return MediaServiceResult.OK;
	}

	@Override
	public MediaServiceResult addDisc(Disc disc) {
		if (disc == null || disc.getDirector().isEmpty() || disc.getTitle().isEmpty() || disc.getFsk() < 0) {
			return MediaServiceResult.DISC_DATA_INCOMPLETE;
		} else if (this.discs.get(disc.getBarcode()) != null) {
			return MediaServiceResult.BARCODE_DUPLICATED;
		} else if (disc.getBarcode() == null || disc.getBarcode().length() != 13) {
			return MediaServiceResult.BARCODE_INVALID;
		}

		this.discs.put(disc.getBarcode(), disc);

		return MediaServiceResult.OK;
	}

	@Override
	public Medium[] getBooks() {
		return this.books.values().toArray(new Medium[0]);
	}

	@Override
	public Medium[] getDiscs() {
		return this.discs.values().toArray(new Medium[0]);
	}

	@Override
	public Medium getBookByISBN(String isbn) {
		return this.books.get(isbn);
	}

	@Override
	public Medium getDiscByBarcode(String barcode) {
		return this.discs.get(barcode);
	}

	@Override
	public MediaServiceResult updateBook(Book book, String isbn) {
		if (this.books.get(book.getIsbn()) == null) {
			return MediaServiceResult.ISBN_NOT_FOUND;
		} else if (!book.getIsbn().replaceAll("[^0-9]", "").equals(isbn.replaceAll("[^0-9]", ""))) {
			return MediaServiceResult.ISBN_CONFLICT;
		} else if (book.getAuthor().isEmpty() || book.getTitle().isEmpty()) {
			return MediaServiceResult.BOOK_DATA_INCOMPLETE;
		} else {
			this.books.put(isbn, book);

			return MediaServiceResult.OK;
		}
	}

	@Override
	public MediaServiceResult updateDisc(Disc disc, String barcode) {
		if (this.discs.get(disc.getBarcode()) == null) {
			return MediaServiceResult.BARCODE_NOT_FOUND;
		} else if (!disc.getBarcode().replaceAll("[^0-9]", "").equals(barcode.replaceAll("[^0-9]", ""))) {
			return MediaServiceResult.BARCODE_CONFLICT;
		} else if (disc.getDirector().isEmpty() || disc.getTitle().isEmpty() || disc.getFsk() < 0) {
			return MediaServiceResult.DISC_DATA_INCOMPLETE;
		} else {
			this.discs.put(barcode, disc);

			return MediaServiceResult.OK;
		}
	}

	private Boolean checkISBN(String isbn) {
		Boolean result = false;
		// isbn = isbn.replace("-", "").replace(" ", "");
		isbn = isbn.replaceAll("[^0-9]", "");
		long num;

		try {
			num = Long.parseLong(isbn);
			// num = Integer.valueOf(isbn);
		} catch (NumberFormatException e) {
			return result;
		}

		if (isbn.isEmpty() || isbn.length() != 13) {
			return result;
		}

		char[] numbers = isbn.toCharArray();
		int checkSum = 0;
		for (int i = 0; i < numbers.length - 1; i++) {
			if (i % 2 == 0) {
				checkSum += Character.getNumericValue(numbers[i]);
			} else {
				checkSum += Character.getNumericValue(numbers[i]) * 3;
			}
		}
		if ((10 - (checkSum % 10)) == Character.getNumericValue(numbers[numbers.length - 1])) {
			result = true;
		}

		return result;
	}
}
