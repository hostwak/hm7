package hw12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    private static final String PATH_NUMBERS = "numbers.txt";
    // Кэш для хранения уже вычисленных факториалов
    private static final ConcurrentHashMap<Integer, Long> factorialCache = new ConcurrentHashMap<>();

    /**
     * Генерация файла со случайными числами.
     *
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
     *
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
     * Вычисление факториала числа с использованием кэша.
     *
     * @param number число, для которого нужно посчитать факториал
     */
    private static long calculateFactorial(int number) {
        if (!factorialCache.containsKey(number)) { // Если результат еще не закеширован
            long factorialResult = 1L; // Инициализируем результат как 1

            for (int i = 2; i <= number; i++) { // Вычисляем факториал циклом
                factorialResult *= i;

                if (factorialResult > Integer.MAX_VALUE || factorialResult < Integer.MIN_VALUE) {
                    throw new ArithmeticException("Переполнение при вычислении факториала");
                }
            }

            factorialCache.put(number, factorialResult); // Закешируем результат после его расчета


        }

        return factorialCache.get(number); // Возвращаем закешированный или только что посчитанный результат

    }

    /**
     * Потоковый класс для расчета и вывода факториала числа с учетом кэша.
     */
    private static class FactorialThread extends Thread {

        private final int number;


        private FactorialThread(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            long fact = calculateFactorial(this.number);
            System.out.printf("Факториал %d равен %d%n", this.number, fact);
        }

        // Класс-строитель для поэтапного создания объекта FactorialThread
        public static class FactorialThreadBuilder {

            private int number;

            // Метод для установки значения числа в потоке
            public FactorialThreadBuilder withNumber(int number) {
                this.number = number;
                return this; // Возврат самого себя для цепочки вызовов методов
            }

            // Метод сборки и запуска потока с заданным числом
            public void buildAndStart() {
                Thread thread = new FactorialThread(number);
                thread.start();
            }


        }
    }

    public static void main(String[] args) {
        calculateFact(50);

    }

    public static void calculateFact(final int countNum) {

        generateFile(countNum);

        List<Integer> list = readNumbersFromFile(PATH_NUMBERS);

        System.out.print("Найти факториалы чисел:");
        System.out.println(list);

        for (Integer number : list) {
            new FactorialThread.FactorialThreadBuilder()
                    .withNumber(number)
                    .buildAndStart();
        }
    }
}
