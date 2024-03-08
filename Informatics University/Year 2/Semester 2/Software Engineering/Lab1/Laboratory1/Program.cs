using System.Collections;
using System;

namespace Laboratory1
{
    internal class Program
    {
        static void Main(string[] args)
        {
            int MAX_NUMBER = 1000000;
            bool[] isPrimeNumber = new bool[MAX_NUMBER];
            for (int i = 0; i < MAX_NUMBER; ++i)
            {
                isPrimeNumber[i] = true;
            }
            for (int i = 2; i * i < MAX_NUMBER; ++i)
            {
                if (isPrimeNumber[i])
                {
                    for (int j = i * i; j < MAX_NUMBER; j += i)
                    {
                        isPrimeNumber[j] = false;
                    }
                }
            }

            ArrayList primeNumbers = new ArrayList();
            for (int i = 2; i < MAX_NUMBER; ++i)
            {
                if (isPrimeNumber[i]) {
                    primeNumbers.Add(i);
                }
            }

            Console.WriteLine("10001th prime number is: " + primeNumbers[10000]);
        }
    }
}
