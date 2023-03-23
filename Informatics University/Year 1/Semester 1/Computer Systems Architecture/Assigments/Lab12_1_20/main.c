#include <stdio.h>

void doubleInReverse(int buf[], int n, int sol[]);

int main()
{
    FILE *ptr = fopen("numbers.txt", "r");
    if (ptr == NULL)
    {
        printf("NO such file.");
        return 0;
    }
    int buf[100];
    int n;
    for (n = 0; fscanf(ptr, "%d", &buf[n]) == 1; ++n)
    {
        //printf("%d ", buf[n]);
    }
    fclose(ptr);

    int sol[100];
    doubleInReverse(buf, n, sol);
    for (int i = 0; i < n; ++i)
    {
        printf("%d ", sol[i]);
    }

    return 0;
}