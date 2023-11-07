import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main (String[] args)
    {
        try (Scanner cin = new Scanner(System.in)) {
            int sum = 0, numberOfNumbers = 0;
            while (cin.hasNextInt())
            {
                sum += cin.nextInt();
                numberOfNumbers++;
            }

            System.out.println((double) sum / (double) numberOfNumbers);
        }
    }
}