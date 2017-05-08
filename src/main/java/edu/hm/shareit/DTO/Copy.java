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

    /**
     * Default constructor.
     * @param owner owner.
     * @param medium medium.
     */
    public Copy(String owner, Medium medium) {
        this.medium = medium;
        this.owner = owner;
    }

    /**
     * Returns medium.
     * @return medium.
     */
    public Medium getMedium() {
        return this.medium;
    }

    /**
     * Returns owner.
     * @return owner.
     */
    public String getOwner() {
        return this.owner;
    }
}
