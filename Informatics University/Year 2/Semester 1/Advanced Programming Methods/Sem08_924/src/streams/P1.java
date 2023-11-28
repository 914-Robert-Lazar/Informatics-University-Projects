package streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Problem 1: Given the following collection
 * List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15);
 * Using Java functional style (Java streams), please write a program that is doing the following operations
 * in the following order:
 * a)keep only the numbers which are multiple of 3 or multiple of 2;
 * b)transform each remaining number into a string, that consists of a prefix "A", the successor of the number
 * and the suffix "B"
 * (eg. 6 is transformed into "A7B")
 * c)concatenate all the strings
 */

public class P1 {
    public static void main(String[] v){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15);

        //a. keep only the numbers which are multiple of 3 or multiple of 2;
        numbers = numbers.stream().filter(n-> n%2==0 || n%3==0).collect(Collectors.toList());
        numbers.forEach(System.out::println);

        //b. transform each remaining number into a string, that consists of a prefix "A", the successor of the number
        // and the suffix "B" (eg. 6 is transformed into "A7B")
        List<String> strings = numbers.stream().map(n-> "A"+(n+1)+"B").collect(Collectors.toList());
        strings.forEach(System.out::println);

        //c. concatenate all the strings
        System.out.println(strings.stream().reduce((partial, s)-> partial+s).get());
    }
}

