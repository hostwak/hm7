package hw9;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Streams<T> {
    private final List<T> collection;


    public static <T> Streams<T> of(Collection<? extends T> collection) {
        return new Streams<>(new ArrayList<>(collection));
    }


    private Streams(List<T> collection) {
        this.collection = collection;
    }

    // Метод для фильтрации элементов
    public Streams<T> filter(Predicate<? super T> predicate) {
        List<T> filtered = collection.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        return new Streams<>(filtered);
    }

    // Метод для трансформации элементов
    public <R> Streams<R> transform(Function<? super T, ? extends R> mapper) {
        List<R> transformed = collection.stream()
                .map(mapper)
                .collect(Collectors.toList());
        return new Streams<>(transformed);
    }

    // Метод для преобразования в Map
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper,
                                  Function<? super T, ? extends V> valueMapper) {
        return collection.stream()
                .collect(Collectors.toMap(keyMapper, valueMapper));
    }

    // Метод для получения списка элементов
    public List<T> toList() {
        return new ArrayList<>(collection);
    }
}
