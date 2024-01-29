import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14,15);
        System.out.println(numbers.stream().filter(number -> number % 3 == 0 || number % 7 == 0)
            .map(number -> (number - 1) * 10).reduce(0, (a, b) -> (a + b) % 500));

        System.out.println(numbers.stream().filter(number -> number % 5 == 0 || number % 2 == 0)
            .map(number -> "N" + number.toString() + "R").collect(Collectors.joining()));

        System.out.println(numbers.stream().filter(number -> number % 3 == 0 || number % 4 == 0)
            .map(number -> (number - 1) % 3).reduce(0, (a, b) -> (a + b) % 2));

        System.out.println(numbers.stream().filter(n -> n % 3 == 0 || n % 7 == 0)
            .map(n -> (n - 1) * 10).reduce(0, (a, b) -> (a + b) % 5));

    }
}