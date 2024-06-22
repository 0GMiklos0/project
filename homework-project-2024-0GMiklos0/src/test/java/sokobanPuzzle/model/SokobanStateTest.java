package sokobanPuzzle.model;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SokobanStateTest {
    SokobanState finishedState = new SokobanState(Arrays.stream(JsonHandler.readMaps("test.json"))
            .filter(map -> map.name().equals("finished"))
            .findFirst()
            .get());

    SokobanState startingState = new SokobanState(Arrays.stream(JsonHandler.readMaps("test.json"))
            .filter(map -> map.name().equals("open_field"))
            .findFirst()
            .get());

    SokobanStateTest() throws IOException {
    }

    @Test
    void isSolved() {
        assertTrue(finishedState.isSolved());
    }

    @Test
    void isLegalMove() {
        assertTrue(startingState.isLegalMove(Direction.RIGHT));
        assertFalse(startingState.isLegalMove(Direction.LEFT));
    }

    @Test
    void makeMove() {
        SokobanState movedState = (SokobanState) startingState.clone();
        movedState.makeMove(Direction.RIGHT);
        assertNotEquals(movedState, startingState);
        assertEquals(startingState.getPlayer().move(Direction.RIGHT),movedState.getPlayer());
        assertEquals(startingState.getRocks()[0].move(Direction.RIGHT),movedState.getRocks()[0]);
    }

    @Test
    void getLegalMoves() {
        Set<Direction> testLegalMoves = new HashSet<>();
        testLegalMoves.add(Direction.RIGHT);
        assertEquals(startingState.getLegalMoves(), testLegalMoves);
        testLegalMoves.clear();
        testLegalMoves.add(Direction.LEFT);
        testLegalMoves.add(Direction.UP);
        testLegalMoves.add(Direction.DOWN);
        assertEquals(finishedState.getLegalMoves(), testLegalMoves);
    }

    @Test
    void testClone() {
        assertEquals(startingState, startingState.clone());
        assertNotSame(startingState, startingState.clone());
    }
}