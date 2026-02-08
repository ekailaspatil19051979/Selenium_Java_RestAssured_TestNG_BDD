package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JSONReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode readJsonFile(String filePath) {
        try {
            return objectMapper.readTree(new File(filePath));
        } catch (IOException e) {
            LoggerUtil.error("Error reading JSON file: " + e.getMessage());
            return null;
        }
    }
}
