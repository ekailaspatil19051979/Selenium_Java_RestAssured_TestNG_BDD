package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVReaderUtil {

    public static List<String[]> readCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            LoggerUtil.error("Error reading CSV file: " + e.getMessage());
            return null;
        }
    }
}
