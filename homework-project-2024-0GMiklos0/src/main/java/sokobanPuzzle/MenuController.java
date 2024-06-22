package sokobanPuzzle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;
import  sokobanPuzzle.model.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Controller class for the menu
 *
 */
public class MenuController {

    @FXML
    private Label mapName;

    @FXML
    private GridPane drawnMap;

    @Getter @Setter
    private SokobanState state;

    private static List<Map> maps;

    @Getter @Setter
    private static int current = 0;

    private final int entitySize = 20;

    /**
     * Initializes with loading the Maps
     */
    @FXML
    public void initialize(){
        try{
            maps = Arrays.stream(JsonHandler.readMaps("maps.json")).toList();
        }
        catch (Exception e){
            System.err.print(e);
        }
        menu();
    }

    /**
     * Handles the menu of the MenuController
     */
    public void menu(){
        mapName.setText(maps.get(current).name());
        makeMap(maps.get(current));
    }
    /**
     * Draws a smaller version of a {@code Map}
     */
    public void makeMap(Map map){
        drawnMap.getChildren().clear();
        for(int i = 0;i < map.tiles().length; i++){
            for (int j = 0; j < map.tiles()[i].length; j++){
                var square = createSmallSquare(map.tiles()[i][j]);
                if(new Position(i,j).equals(map.spawnPoint())){
                    var player = drawPlayer();
                    square.getChildren().add(player);
                }
                else if (Arrays.stream(map.rock()).toList().contains(new Position(i, j))){
                    var rock = drawRock();
                    square.getChildren().add(rock);
                }
                else if (Arrays.stream(map.destination()).toList().contains(new Position(i, j))){
                    var destination = drawDestination();
                    square.getChildren().add(destination);
                }
                drawnMap.add(square ,j, i);
            }
        }
    }

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
     * Creates a StackPane with a background based on the tile
     * @param tile {@code Square}
     * @return a {@code StackPane} based on the specific {@code Square} value
     */
    public StackPane createSmallSquare(Square tile){
        StackPane square = new StackPane();
        BackgroundFill bf;
        if (Objects.requireNonNull(tile) == Square.UNREACHABLE) {
            bf = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null);
        } else {
            bf = new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, null);
        }
        Background bg = new Background(bf);
        square.setBackground(bg);
        return square;
    }

    /**
     * Get the state of the beginning state of the picked Map
     * @return SokobanState
     */
    public static SokobanState getBeginnerState(){
        try {
            return new SokobanState(Arrays.stream(JsonHandler.readMaps("maps.json"))
                    .toList().get(current));
        }
        catch (Exception e){
            Logger.error(e);
        }
        return null;
    }

    /**
     * Method for the left button
     */
    @FXML
    public void switchMapLeft(){
        if(current == 0) current = maps.size() - 1;
        else current--;
        menu();
    }

    /**
     * Method for the right button
     */
    @FXML
    public void switchMapRight(){
        if(current == maps.size() - 1) current = 0;
        else current++;
        menu();
    }

    /**
     * Method, that helps to switch to a SokobanState, begins the game
     * @param event The button's event
     * @throws IOException If the file is not found
     */
    @FXML
    public void switchScene(ActionEvent event) throws IOException {
        state = getBeginnerState();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/ui.fxml"));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
