package sokobanPuzzle.model;

/**
 * The class used for reading from a Json file.
 * @param name The name of the map.
 * @param tiles A double-array of {@code Square} values.
 * @param rock An array of {@code Position} values, that is used for placing the rocks.
 * @param destination An array of {@code Position} values, used in isSolved() method.
 * @param spawnPoint A {@code Position} where the player begins the game
 */

public record Map(String name, Square[][] tiles, Position[] rock, Position[] destination, Position spawnPoint){

}
