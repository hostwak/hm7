package hw12;

import lombok.AllArgsConstructor;
import java.math.BigInteger;

@AllArgsConstructor
public class FactorialThreadV2 implements Runnable {

    private final int number; //

    private static final String OUTPUT_STRING = "%d! = %s\n"; // Используем %s для поддержки больших чисел

    @Override
    public void run() {
        try {
            BigInteger result = calculate();
            System.out.printf(OUTPUT_STRING, number, result);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage()); // Логируем ошибку в stderr
        }
    }

    public BigInteger calculate() {
        if (number < 0) {
            throw new IllegalArgumentException("Number cannot be negative");
        }

        BigInteger result = BigInteger.ONE;

        for (int i = 2; i <= number; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }

        return result;
    }
}
