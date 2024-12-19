package hw10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final String PATH_NUMBERS = "numbers.txt";

    /**
     * Генерация файла со случайными числами.
     * @param n количество случайных чисел в файле.
     */
    private static void generateFile(int n) {
        Random randomNumber = new Random();
        try (FileWriter fileWriter = new FileWriter(PATH_NUMBERS)) {
            for (int i = 0; i < n; i++) {
                int number = randomNumber.nextInt(50);
                fileWriter.write(number + "\n"); // Используем конкатенацию для записи строки
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    /**
     * Чтение из файла чисел в список.
     * @param path путь до файла.
     * @return список чисел из файла.
     */
    private static List<Integer> readNumbersFromFile(String path) {
        List<Integer> result = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении из файла: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Ошибка преобразования строки в число: " + e.getMessage());
        }

        return result;
    }

    /**
     * Вычисление факториалов для заданного количества чисел.
     * @param countNum количество чисел для подсчета факториала.
     */
    public static void calculateFact(final int countNum) {
        generateFile(countNum);
        List<Integer> list = readNumbersFromFile(PATH_NUMBERS);

        System.out.print("Найти факториалы чисел: ");
        System.out.println(list);

        for (Integer number : list) { // Используем foreach для большей ясности
            Thread thread = new Thread(new FactorialThread(number));
            thread.start();
        }
    }

    public static void main(String[] args) {
        calculateFact(50);
    }
}
