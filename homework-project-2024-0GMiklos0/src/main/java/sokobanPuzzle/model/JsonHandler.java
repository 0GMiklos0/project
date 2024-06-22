package sokobanPuzzle.model;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * A class made for reading the Json file
 */

public class JsonHandler {
    /**
     * Read Json formatted Maps from a file
     * @param fileName the file to read from
     * @return Map[] the array of maps
     * @throws IOException if file doesn't exist
     */
    public static Map[] readMaps(String fileName) throws IOException{
        ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        return om.readValue(JsonHandler.class.getResource("/" + fileName), Map[].class);
    }
}
