package inf112.skeleton.app.enums;

public enum LeftRight {
    LEFT, RIGHT;
    
    public String toString(LeftRight lr){
        if (lr == LEFT) return "LEFT";
        else return "RIGHT";
    }
}
