package edu.hm.shareit.DTO;

/**
 * Data transfer object of Book.
 * 
 * @author Dmitry Dorodnov
 *
 */
public class Book extends Medium {

	private String author;
	private String isbn;

	public Book() {
		super("no title");
	}
	public Book (String author, String isbn, String title) {
		super(title);
		this.author = author;
		this.isbn = isbn;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (getAuthor() == null) {
			if (other.getAuthor() != null)
				return false;
		} else if (!getAuthor().equals(other.getAuthor()))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		return true;
	}
	
	public String getAuthor(){
		return this.author;
	}
	
	public String getIsbn(){
		return this.isbn;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getAuthor() == null) ? 0 : getAuthor().hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return "Book [author = " + this.getAuthor()
				+ ", isbn = " + this.isbn
				+ ", title = " + super.getTitle() + "]";
	}
	
}
