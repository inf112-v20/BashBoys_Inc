package inf112.skeleton.app;

public interface MapObject {

    /**
     * Get X coordinate
     */
    public int getX();

    /**
     * Get Y coordinate
     */
    public int getY();

    /**
     * Set X coordinate
     */
    public void setX(int x);

    /**
     * Set Y coordinate
     */
    public void setY(int y);

    /**
     * Remove object from map
     */
    public void remove();
}
