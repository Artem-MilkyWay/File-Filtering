package file.filtering;

import java.io.*;
import java.util.*;

/**
 * Обработчик входных файлов.
 */
public class FileHandler {
    private final List<String> inputFiles = new ArrayList<>();   // Пустой список для файлов
    private String outputDirectory = ".";   // Текущая директория по умолчанию
    private String prefix = "";    // Пустой префикс по умолчанию
    private boolean appendMode = false;    // Режим добавления выключен по умолчанию
    private boolean fullStatistics = false;    // Режим статистики: краткая/полная, при отсутствии флага в параметрах будет по умолчанию краткая

    // Массивы-результаты
    private final List<String> strings = new ArrayList<>();
    private final List<Integer> integers = new ArrayList<>();
    private final List<Double> floats = new ArrayList<>();

    private final Statistics statistics;    // Хранение статистики о файлах

    /**
     * Распарсивает команду с помощью метода parseArguments(...).
     *
     * @param args принимает аргументы командной строки
     * @throws IllegalArgumentException - не переданы файлы для обработки
     */
    public FileHandler(String[] args) throws IllegalArgumentException {
        parseArguments(args);
        this.statistics = new Statistics();
    }

    /**
     * Выполняет парсинг команды: устанавливает флаги, достаёт названия файлов.
     *
     * @param args аргументы командной строки
     */
    private void parseArguments(String[] args) {
        // Логика для парсинга аргументов: -o, -p, -a, -s, -f
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    // Проверяем что после флага стоит корректный аргумент (не название входного файла или другой флаг)
                    if (++i < args.length && !args[i].startsWith("-") && !args[i].endsWith(".txt")) {
                        outputDirectory = args[i];
                        break;
                    } else{
                        throw new IllegalArgumentException("After -o flag should be proper argument!");
                    }
                case "-p":
                    // Аналогично
                    if (++i < args.length && !args[i].startsWith("-") && !args[i].endsWith(".txt")) {
                        prefix = args[i];
                        break;
                    } else{
                        throw new IllegalArgumentException("After -p flag should be proper argument!");
                    }
                case "-a":
                    appendMode = true;
                    break;
                case "-s":
                    fullStatistics = false;
                    break;
                case "-f":
                    fullStatistics = true;
                    break;
                default:
                    inputFiles.add(args[i]);
            }
        }

        // Не были переданы файлы в качестве аргументов, бросаем ошибку
        if (inputFiles.isEmpty()) {
            throw new IllegalArgumentException("No input files provided.");
        }
    }

    /**
     * Обработка файлов. Читает каждый файл, вызывает метод processLine, продолжающий логику.
     */
    public void processFiles() {
        for (String fileName : inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    processLine(line);
                }
            } catch (IOException e) {
                System.err.println("Error processing file " + fileName + ": " + e.getMessage());
            }
        }
        writeToFile();
    }

    /**
     * Принимает строку файла, переданную из processFiles.
     * Добавляет элемент в соответствующий массив-результат.
     * Определяет куда добавлять элемент в Статистике.
     *
     * @param line строка файла
     */
    private void processLine(String line) {
        if (isInteger(line)) {
            int value = Integer.parseInt(line);
            integers.add(value);
            statistics.addInteger(value);
        } else if (isDouble(line)) {
            double value = Double.parseDouble(line);
            floats.add(value);
            statistics.addFloat(value);
        } else {
            // Если не целое и не вещественное, тогда строка.
            strings.add(line);
            statistics.addString(line);
        }
    }

    private boolean isInteger(String str) {
        // Проверка на целое число
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String str) {
        // Проверка на вещественное число
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Записывает полученные массивы-результаты по выходным файлам с помощью класса FileWriter.
     */
    private void writeToFile() {
        try {
            if (!integers.isEmpty()) {
                FileWriter.write(outputDirectory, prefix + "integers.txt", integers, appendMode);
            }
            if (!floats.isEmpty()) {
                FileWriter.write(outputDirectory, prefix + "floats.txt", floats, appendMode);
            }
            if (!strings.isEmpty()) {
                FileWriter.write(outputDirectory, prefix + "strings.txt", strings, appendMode);
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Выводит краткую/полную статистику в консоль.
     */
    public void printStatistics() {
        if (fullStatistics) {
            statistics.printFull();
        } else {
            statistics.printConcise();
        }
    }
}

