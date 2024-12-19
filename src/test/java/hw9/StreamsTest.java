package hw9;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StreamsTest {

    @Test
    public void testFilter() {
        List<Person> people = Arrays.asList(
                new Person("Alice", 25),
                new Person("Bob", 30),
                new Person("Charlie", 20)
        );

        List<Person> filtered = Streams.of(people)
                .filter(p -> p.getAge() > 20)
                .toList();

        assertEquals(2, filtered.size());
        assertTrue(filtered.stream().allMatch(p -> p.getAge() > 20));
    }

    @Test
    public void testTransform() {
        List<Person> people = Arrays.asList(
                new Person("Alice", 25),
                new Person("Bob", 30)
        );

        // Увеличиваем возраст на 10
        List<Person> transformed = Streams.of(people)
                .transform(p -> new Person(p.getName(), p.getAge() + 10))
                .toList();

        assertEquals(2, transformed.size());

        // Проверяем возраст Alice после трансформации (25 + 10)
        assertEquals(35, transformed.get(0).getAge());

        // Проверяем возраст Bob после трансформации (30 + 10)
        assertEquals(40, transformed.get(1).getAge());
    }

    @Test
    public void testToMap() {
        List<Person> people = Arrays.asList(
                new Person("Alice", 25),
                new Person("Bob", 30)
        );

        Map<String, Person> map = Streams.of(people)
                .toMap(Person::getName, p -> p);

        assertEquals(2, map.size());
        assertTrue(map.containsKey("Alice"));
        assertTrue(map.containsKey("Bob"));

        // Проверяем возраст Bob в мапе
        assertEquals(30, map.get("Bob").getAge());
    }
}
