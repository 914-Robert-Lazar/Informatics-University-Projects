#include <stdio.h>
#include <stdbool.h>

typedef struct Vector
{
    int arr[50], len;
} Vector;

Vector readVector()
{
    int x, arr[50];
    Vector array;

    do
    {
        printf("Element: ");
        scanf("%d", &x);
        array.arr[array.len++] = x;

    } while (x != 0);
    
    array.len -= 1;

    return array;
}

int sum(Vector array)
{
    int sol = 0;
    for (int i = 0; i < array.len; ++i)
    {
        sol += array.arr[i];
    }
    return sol;
}

Vector readVectorFor2()
{
    Vector array;
    printf("The size: ");
    scanf("%d", &array.len);

    for (int i = 0; i < array.len; ++i)
    {
        printf("The element: ");
        scanf("%d", &array.arr[i]);
    }

    return array;
}

void longestSubsequence(Vector array, int* size, int* end)
{
    int maxSize = 0, currSize = 1;
    for (int i = 0; i < array.len - 1; ++i)
    {
        if (array.arr[i] != array.arr[i + 1])
        {
            if (currSize > maxSize)
            {
                maxSize = currSize;
                *size = maxSize;
                *end = i;

            }
            currSize = 1;
        }
        else
        {
            ++currSize;
        }
    }
}

void printSol(Vector array, int size, int end)
{
    for (int i = end - size; i <= end; ++i)
    {
        printf("%d ", array.arr[i]);
    }
}

int main()
{
    int option;
    bool done = false;
    while (true)
    {   
        printf("Option 1: Sum of number\n");
        printf("Option 2: Longest subsequence\n");
        printf("Option 3: Exit\n");

        printf("Select option: ");
        scanf("%d", &option);
        if (option == 0)
        {
            break;
        }
        switch (option)
        {
            case 1:
                Vector arr = readVector();
                int s = sum(arr);
                printf("%d", s);
                break;
            case 2:
                Vector arr2 = readVectorFor2();
                int solSize, solEnd;
                longestSubsequence(arr2, &solSize, &solEnd);
                printSol(arr2, solSize, solEnd);
                break;
            case 3:
                done = true;
            default:
                break;
        }
        
    }
    
    
    return 0;
}