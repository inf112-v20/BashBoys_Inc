package inf112.skeleton.app.object;

import inf112.skeleton.app.Board;

public interface ICard {
    void doStuff(Robot robot, Board board);
    int getPriority();
    String getName();
    String getExtra();
}
