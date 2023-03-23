#include <stdio.h>

void SumAndProduct(int a, int b, int *p, int *s)
{
    *s = a + b;
    *p = a * b;
}

int main()
{
    int a, b;
    scanf("%d  %d", &a ,&b);

    int s = 0, p = 1;
    SumAndProduct(a, b, &p, &s);
    printf("The sum of the 2 numbers: %d, the product: %d", s, p);
}