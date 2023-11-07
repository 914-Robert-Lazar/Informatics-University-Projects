#include <sys/types.h>

#include<WinSock2.h>
#include <stdint.h>
#include <stdio.h>

#pragma comment(lib,"Ws2_32.lib")

int main() {

       int c;

       struct sockaddr_in server;

       uint16_t a, b, suma;

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

 

       if (connect(c, (struct sockaddr *) &server, sizeof(server)) < 0) {

              printf("Eroare la conectarea la server\n");

              return 1;

       }

 

       printf("a = ");

       scanf("%hu", &a);

       printf("b = ");

       scanf("%hu", &b);

       a = htons(a);

       b = htons(b);

       send(c, (char*)&a, sizeof(a), 0);

       send(c, (char*)&b, sizeof(b), 0);

       recv(c, (char*)&suma, sizeof(suma), 0);

       suma = ntohs(suma);

       printf("Suma este %hu\n", suma);

       closesocket(c);
#ifdef _WIN32

       WSACleanup();

#endif    
}