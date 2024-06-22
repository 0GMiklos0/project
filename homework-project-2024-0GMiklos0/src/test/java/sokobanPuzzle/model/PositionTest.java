package sokobanPuzzle.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    Position position = new Position(0,0);

    @Test
    void moveTest(){
        assertEquals(new Position(0,1), position.move(Direction.RIGHT));
        assertEquals(new Position(1,0), position.move(Direction.DOWN));
        assertEquals(new Position(0,-1), position.move(Direction.LEFT));
        assertEquals(new Position(-1,0), position.move(Direction.UP));
    }

    @Test
    void moveOppositeTest(){
        assertEquals(new Position(0,-1), position.moveOpposite(Direction.RIGHT));
        assertEquals(new Position(-1,0), position.moveOpposite(Direction.DOWN));
        assertEquals(new Position(0,1), position.moveOpposite(Direction.LEFT));
        assertEquals(new Position(1,0), position.moveOpposite(Direction.UP));
    }
}