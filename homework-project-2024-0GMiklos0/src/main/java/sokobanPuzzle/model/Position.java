package sokobanPuzzle.model;

import java.util.Comparator;

/**
 * Represents a position on the board
 * @param x values of vertical position
 * @param y values of horizontal position
 */

public record Position(int x, int y) {
    /**
     * Makes a new Position based on a move
     * @param direction direction of a move
     * @return Position with new coordinates
     */
    public Position move(Direction direction){
        return new Position(this.x() + direction.getXAxes(), this.y() + direction.getYAxes());
    }

    public Position moveOpposite(Direction direction){
        return new Position(this.x() - direction.getXAxes(), this.y() - direction.getYAxes());
    }
}
