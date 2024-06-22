package sokobanPuzzle.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    @Test
    void get() {
        assertEquals(Square.UNREACHABLE.get(), 0);
        assertEquals(Square.REACHABLE.get(), 1);
    }
}