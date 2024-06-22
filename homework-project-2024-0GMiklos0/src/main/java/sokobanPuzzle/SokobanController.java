package sokobanPuzzle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;
import sokobanPuzzle.model.Direction;
import sokobanPuzzle.model.SokobanState;
import puzzle.solver.BreadthFirstSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * A Controller Class made for using the JavaFX API, and keeps track of the current State
 */
public class SokobanController {
    @Getter
    @Setter
    private SokobanState state;

    private final int entitySize = 40;

    private ArrayList<ArrayList<StackPane>> nodes;

    private int steps;

    @FXML
    private BorderPane container;

    @FXML
    private GridPane board;

    @FXML
    private Label stepCounter;

    /**
     * Initializes the game board
     */
    @FXML
    private void initialize(){
        Platform.runLater(() -> board.getScene().setOnKeyPressed(this::onKeyPressed));
        state = MenuController.getBeginnerState();
        drawBoard();
    }

    /**
     * Draws the board using the nodes class attribute, which is an ArrayList of ArrayLists containing StackPane.
     * The nodes are
     */
    public void drawBoard(){
        board.getChildren().clear();
        nodes = new ArrayList<>();
        for(int i = 0; i < state.getTiles().length; i++){
            nodes.add(new ArrayList<>());
            for(int j = 0;j < state.getTiles()[i].length; j++){
                nodes.get(i).add(createSquare(i, j));
            }
        }
        var player = drawPlayer();
        nodes.get(state.getPlayer().x()).get(state.getPlayer().y()).getChildren().add(player);
        for(var rock : state.getRocks()){
            var drawnRock = drawRock();
            nodes.get(rock.x()).get(rock.y()).getChildren().add(drawnRock);
        }
        for(var destination : state.getDestination()){
            var drawnDestination = drawDestination();
            nodes.get(destination.x()).get(destination.y()).getChildren().add(drawnDestination);
        }
        for (int i = 0; i < nodes.size();i++){
            for (int j = 0; j < nodes.get(i).size();j++){
                board.add(nodes.get(i).get(j), j,i);
            }
        }
    }

    /**
     * Returns a StackPane based on the value of a Square in tiles
     * @param i the value of x axes
     * @param j the value of y axes
     * @return StackPane
     */
    private StackPane createSquare(int i, int j){
        StackPane square = new StackPane();
        BackgroundFill bf;
        switch(state.getTiles()[i][j]){
            case UNREACHABLE:
                bf = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null);break;
            case REACHABLE:
                bf = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, null);break;
            default:
                bf = new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, null);
        }
        Background bg = new Background(bf);
        square.setBackground(bg);
        return square;
    }

    /**
     * Creates the player entity, the entity is an instance of {@link Circle}
     * @return player
     */
    public Circle drawPlayer(){
        Circle player = new Circle(entitySize);
        player.setFill(Color.RED);
        return player;
    }

    /**
     * Creates the destination entity, the entity is an instance of {@link Circle}
     * @return destination
     */
    public Circle drawDestination(){
        Circle destination = new Circle(entitySize);
        destination.setStroke(Color.BLACK);
        destination.setFill(Color.TRANSPARENT);
        return destination;
    }
    /**
     * Creates the rock entity, the entity is an instance of {@link Circle}
     * @return rock
     */
    public Circle drawRock(){
        Circle rock = new Circle(entitySize);
        rock.setFill(Color.GRAY);
        return rock;
    }

    /**
     * Handles the keyboard inputs
     * @param event Keyboard input
     */
    @FXML
    private void onKeyPressed(KeyEvent event){
        steps++;
        KeyCode key = event.getCode();
        switch(key){
            case W:
                    state.makeMove(Direction.UP);
                break;
            case A:
                    state.makeMove(Direction.LEFT);
                break;
            case S:
                    state.makeMove(Direction.DOWN);
                break;
            case D:
                    state.makeMove(Direction.RIGHT);
                break;
            case R:{
                restart();
                steps = 0;
                break;
            }
        }
        Logger.info(state.getPlayer());
        stepCounter.setText(String.format("Steps: %d", steps));
        drawBoard();
        if (state.isSolved()){
            finish();
        }
    }

    /**
     * Sets the state to the beginning state
     */
    public void restart(){
        try{
            state = MenuController.getBeginnerState();
        }
        catch(Exception e){
            System.err.print(e);
        }
    }

    /**
     * The Alert message for successfully completing the game
     */
    public void finish(){
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Congratulations! You solved the puzzle!");
        alert.showAndWait();
        Platform.exit();
    }
    @FXML
    public void solvePuzzle(){
        BreadthFirstSearch<Direction> solved = new BreadthFirstSearch<>();
        solved.solveAndPrintSolution(state);
        drawBoard();
    }

    @FXML
    public void enterMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
