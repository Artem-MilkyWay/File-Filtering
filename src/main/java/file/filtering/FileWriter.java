package file.filtering;

import java.io.*;
import java.util.List;

/**
 * Запись массива-результата в выходной файл.
 */
public class FileWriter {
    /**
     * Производит запись в файл в соответствии с заданными параметрами.
     *
     * @param directory текущая/заданная директория
     * @param fileName имя файла включая префикс
     * @param data массив-результат
     * @param append перезаписываем/добавляем в конец
     * @throws IOException ошибка при попытке записи в файл
     */
    public static void write(String directory, String fileName, List<?> data, boolean append) throws IOException {
        File file = new File(directory, fileName);
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file, append))) {
            for (Object line : data) {
                writer.write(line.toString());
                writer.newLine();
            }
        }
    }
}

