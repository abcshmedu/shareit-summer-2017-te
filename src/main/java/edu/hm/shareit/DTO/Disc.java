package edu.hm.shareit.DTO;

/**
 * Data transfer object of Disc.
 * 
 * @author Dmitry Dorodnov
 *
 */
public class Disc extends Medium {

	private String barcode;
	private String director;
	private int fsk;

	public Disc() {
		super("no title");
	}

	public Disc(String barcode, String director, int fsk, String title) {
		super(title);
		this.barcode = barcode;
		this.director = director;
		this.fsk = fsk;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public String getDirector() {
		return this.director;
	}

	public int getFsk() {
		return this.fsk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
		result = prime * result + ((director == null) ? 0 : director.hashCode());
		result = prime * result + fsk;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disc other = (Disc) obj;
		if (barcode == null) {
			if (other.barcode != null)
				return false;
		} else if (!barcode.equals(other.barcode))
			return false;
		if (director == null) {
			if (other.director != null)
				return false;
		} else if (!director.equals(other.director))
			return false;
		if (fsk != other.fsk)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Disc [barcode = " + barcode + ", director = " + director + ", fsk = " + fsk + ", title = "
				+ super.getTitle() + "]";
	}

}
