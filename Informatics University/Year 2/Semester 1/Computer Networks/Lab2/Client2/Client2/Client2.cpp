#define _WINSOCK_DEPRECATED_NO_WARNINGS 1

#include <stdio.h>
#include <WinSock2.h>
#include <cstdint>
#pragma comment(lib,"Ws2_32.lib")

int main() {

    int c;

    struct sockaddr_in server;

    uint16_t a;

#ifdef _WIN32

    WSADATA wsaData;

    if (WSAStartup(MAKEWORD(2, 2), &wsaData) < 0) {

        printf("Error initializing the Windows Sockets LIbrary");

        return -1;

    }

#endif

    c = socket(AF_INET, SOCK_STREAM, 0);

    if (c < 0) {

        printf("Eroare la crearea socketului client\n");

        return 1;

    }



    memset(&server, 0, sizeof(server));

    server.sin_port = htons(1234);

    server.sin_family = AF_INET;

    server.sin_addr.s_addr = inet_addr("127.0.0.1");



    if (connect(c, (struct sockaddr*)&server, sizeof(server)) < 0) {

        printf("Eroare la conectarea la server\n");

        return 1;

    }



    printf("a = ");

    scanf_s("%hu", &a);


    a = htons(a);

    send(c, (char*)&a, sizeof(a), 0);

    uint16_t sizeOfDivisors, divisor;

    recv(c, (char*)&sizeOfDivisors, sizeof(sizeOfDivisors), 0);

    sizeOfDivisors = ntohs(sizeOfDivisors);
    printf("Number of divisors: %hu\n", sizeOfDivisors);

    for (int i = 0; i < sizeOfDivisors; ++i)
    {
        recv(c, (char*)&divisor, sizeof(divisor), 0);
        divisor = ntohs(divisor);
        printf("Divisor: %hu \n", divisor);
    }

    closesocket(c);

#ifdef _WIN32

    WSACleanup();

#endif

}