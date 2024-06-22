package sokobanPuzzle.model;

/**
 * The enumeration of all the possible moves made in the Sokoban game.
 */

public enum Direction {
    /**
     * The representation of moving up
     */
    UP(-1, 0),
    /**
     * The representation of moving right
     */
    RIGHT(0, 1),
    /**
     * The representation of moving down
     */
    DOWN(1, 0),
    /**
     * The representation of moving left
     */
    LEFT(0, -1);

    private int xAxes;
    private int yAxes;

    /**
     * Basic representation of directions
     * @param x change on the x axes
     * @param y change on the y axes
     */
    Direction(int x, int y){
        this.xAxes = x;
        this.yAxes = y;
    }

    /**
     * Get change values
     * @return change on the X axes
     */
    public int getXAxes() {
        return xAxes;
    }
    /**
     * Get change values
     * @return change on the Y axes
     */
    public int getYAxes() {
        return yAxes;
    }
}
