package streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Problem 2: Given the following collection
 * List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15,16);
 * Using Java functional style (Java streams), please write a program that is doing the following operations in the following order:
 * a)eliminates all the numbers which are not multiple of 4;
 * b)transform each remaining number into its succesor (eg. 4 is transformed into 5);
 * c)compute the sum modulo 2 of the remaining numbers (eg. (9 +5) mod 2=0)
 */

public class P2 {
    public static void main(String[] v){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16);

        //a. eliminates all the numbers which are not multiple of 4;
        numbers = numbers.stream().filter(n-> n%4==0).collect(Collectors.toList());
        numbers.forEach(System.out::println);

        //b. transform each remaining number into its succesor (eg. 4 is transformed into 5);
        numbers = numbers.stream().map(n-> n+1).collect(Collectors.toList());
        numbers.forEach(System.out::println);

        //c. compute the sum modulo 2 of the remaining numbers (eg. (9 +5) mod 2=0)
        System.out.println("Sum % 2 = "+numbers.stream().reduce(0, (partial, s)-> partial+s, Integer::sum)%2);
    }
}


