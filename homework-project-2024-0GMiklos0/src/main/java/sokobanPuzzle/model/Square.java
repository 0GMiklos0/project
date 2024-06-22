package sokobanPuzzle.model;

/**
 * An enumeration for each tile in a Map, 0 is UNREACHABLE and 1 represents REACHABLE.
 * Used for {@code JsonHandler}
 */

public enum Square {
    UNREACHABLE(0),
    REACHABLE(1);

    private int value;
    Square(int value){
        this.value = value;
    }

    public Integer get() {
        return value;
    }
}