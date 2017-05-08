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

    private final int barcodeLength = 13;
    private final int isbnLength = 13;

    /**
     * Default constructor.
     */
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
        } else if (disc.getBarcode() == null || disc.getBarcode().length() != this.barcodeLength) {
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
        if (isbn != null) {
            return this.books.get(isbn);
        } else {
            return null;
        }
    }

    @Override
    public Medium getDiscByBarcode(String barcode) {
        if (barcode != null) {
            return this.discs.get(barcode);
        } else {
            return null;
        }
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

    /**
     * Check whether isbn is correct or not.
     * 
     * @param isbn
     *            to check.
     * @return true if isbn was correct.
     */
    private Boolean checkISBN(String isbn) {
        Boolean result = false;
        final int mult = 3;
        final int mod = 10;
        isbn = isbn.replaceAll("[^0-9]", "");

        try {
            Long.parseLong(isbn);
        } catch (NumberFormatException e) {
            return result;
        }

        if (isbn.isEmpty() || isbn.length() != this.isbnLength) {
            return result;
        }

        char[] numbers = isbn.toCharArray();
        int checkSum = 0;
        for (int i = 0; i < numbers.length - 1; i++) {
            if (i % 2 == 0) {
                checkSum += Character.getNumericValue(numbers[i]);
            } else {
                checkSum += Character.getNumericValue(numbers[i]) * mult;
            }
        }
        if ((mod - (checkSum % mod)) == Character.getNumericValue(numbers[numbers.length - 1])) {
            result = true;
        }

        return result;
    }
}
