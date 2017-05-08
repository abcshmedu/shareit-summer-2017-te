package edu.hm.shareit.DTO;

/**
 * Data transfer object of Copy.
 * 
 * @author Dmitry Dorodnov
 *
 */
public class Copy {

	private Medium medium;
	private String owner;

	public Copy(String owner, Medium medium) {
		this.medium = medium;
		this.owner = owner;
	}

	public Medium getMedium() {
		return this.medium;
	}

	public String getOwner() {
		return this.owner;
	}
}
