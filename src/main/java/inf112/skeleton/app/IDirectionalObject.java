package inf112.skeleton.app;

public interface IDirectionalObject extends IMapObject {

    /**
     * Get direction
     */
    public Direction getDir();

    /**
     * Set direction
     */
    public void setDir(Direction dir);
}
