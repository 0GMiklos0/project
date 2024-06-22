package sokobanPuzzle.model;

import lombok.Getter;
import puzzle.State;
import org.tinylog.Logger;
import sokobanPuzzle.MenuController;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class SokobanState implements puzzle.State<Direction>{
    private Position player;
    private Position[] rocks;
    private Square[][] tiles;
    private Position[] destination;

    public SokobanState() {
        SokobanState state = MenuController.getBeginnerState();
        this.player = state.player;
        this.rocks = state.rocks;
        this.tiles = state.tiles;
        this.destination = state.destination;
        Arrays.sort(destination, Comparator.comparingInt(o -> o.x() * 8 + o.y()));
    }

    public SokobanState(Map map){
        this.player = map.spawnPoint();
        this.rocks = map.rock();
        this.tiles = map.tiles();
        this.destination = map.destination();
        Arrays.sort(destination, Comparator.comparingInt(o -> o.x() * 8 + o.y()));
    }

    @Override
    public boolean isSolved() {
        //return player.equals(destination[0]);
        Arrays.sort(rocks, Comparator.comparingInt(o -> o.x() * 8 + o.y()));
        return Arrays.equals(rocks, destination);
    }

    @Override
    public boolean isLegalMove(Direction move) {
        if(Arrays.stream(rocks).anyMatch(r -> r.equals(player.move(move))) &&
        Arrays.stream(rocks).noneMatch(r -> r.equals(player.move(move).move(move)))){
            Position rock = Arrays.stream(rocks).filter(r -> r.equals(player.move(move))).findFirst().get();
            return rock.move(move).x() >= 0 && rock.move(move).y() >= 0 &&
                    rock.move(move).x() < tiles.length && rock.move(move).y() < tiles[0].length &&
                    tiles[rock.move(move).x()][rock.move(move).y()].equals(Square.REACHABLE);
        } else if (Arrays.stream(rocks).noneMatch(r -> r.equals(player.move(move)))) {
            return player.move(move).x() >= 0&& player.move(move).y() >= 0 &&
                    player.move(move).x() <= tiles.length && player.move(move).y() < tiles[0].length &&
                    tiles[player.move(move).x()][player.move(move).y()].equals(Square.REACHABLE);
        }
        return false;
    }

    @Override
    public void makeMove(Direction move) {
        if(isLegalMove(move)){
            for (int i = 0; i < rocks.length; i++){
                if(player.equals(rocks[i].moveOpposite(move))){
                    rocks[i] = rocks[i].move(move);
                }
            }
            player = player.move(move);
        }
    }

    @Override
    public Set<Direction> getLegalMoves() {
        return Arrays.stream(Direction.values()).filter(this::isLegalMove).collect(Collectors.toSet());
    }

    @Override
    public State<Direction> clone() {
        SokobanState copy;
        try {
            copy = (SokobanState) super.clone();

        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        this.rocks = rocks.clone();
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return (o instanceof SokobanState that) && Arrays.equals(rocks, that.rocks) && player.equals(that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(destination), Arrays.hashCode(rocks), player, tiles);
    }
}
