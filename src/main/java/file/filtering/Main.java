package file.filtering;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Обработка аргументов командной строки и передача их в FileProcessor
        try {
            FileHandler processor = new FileHandler(args);
            processor.processFiles();
            processor.printStatistics();
        }
        catch (IllegalArgumentException e) {
            System.err.println("Error with arguments: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
