#include <stdio.h>

int compute(int a, int b, int c);

int main()
{
    int a, b, c;

    printf("a = ");
    scanf("%d", &a);

    printf("b = ");
    scanf("%d", &b);

    printf("c = ");
    scanf("%d", &c);

    int sol = compute(a, b, c);
    printf("The result of a + b - c = %d", sol);

    return 0;
}