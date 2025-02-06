package file.filtering;

import java.util.*;

/**
 * Искомая статистика, полученная при обработке файлов.
 */
public class Statistics {
    private int stringCount = 0;
    private int integerCount = 0;
    private int floatCount = 0;

    private int minInteger = Integer.MAX_VALUE;
    private int maxInteger = Integer.MIN_VALUE;
    private double sumInteger = 0;

    private double minFloat = Double.MAX_VALUE;
    private double maxFloat = Double.MIN_VALUE;
    private double sumFloat = 0;

    private int minLengthString = Integer.MAX_VALUE;
    private int maxLengthString = 0;

    public void addString(String str) {
        // Добавляем информацию о строке в статистику
        stringCount++;
        int length = str.length();
        if (length < minLengthString) minLengthString = length;
        if (length > maxLengthString) maxLengthString = length;
    }

    public void addInteger(int value) {
        // Добавляем информацию о целом числе в статистику
        integerCount++;
        sumInteger += value;
        if (value < minInteger) minInteger = value;
        if (value > maxInteger) maxInteger = value;
    }

    public void addFloat(double value) {
        // Добавляем информацию о вещественном числе в статистику
        floatCount++;
        sumFloat += value;
        if (value < minFloat) minFloat = value;
        if (value > maxFloat) maxFloat = value;
    }

    public void printConcise() {
        // Вывод краткой статистики
        System.out.println("Number of Strings: " + stringCount);
        System.out.println("Number of Integers: " + integerCount);
        System.out.println("Number of Floats: " + floatCount);
    }

    public void printFull() {
        // Вывод полной статистики = краткая + полная
        printConcise();
        if (integerCount > 0) {
            System.out.println("Integer stats: min=" + minInteger + ", max=" + maxInteger + ", sum=" + sumInteger + ", avg=" + (sumInteger / integerCount));
        }
        if (floatCount > 0) {
            System.out.println("Float stats: min=" + minFloat + ", max=" + maxFloat + ", sum=" + sumFloat + ", avg=" + (sumFloat / floatCount));
        }
        if (stringCount > 0) {
            System.out.println("String stats: shortest=" + minLengthString + ", longest=" + maxLengthString);
        }
    }
}

