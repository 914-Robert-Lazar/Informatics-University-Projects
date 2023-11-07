
import random
import socket
import struct


def run_game(s: socket):
    start=1; stop=2**17-1
    my_num = random.randint(start,stop)
    client_guessed = False
    winner_client = 0
    client_count = 0

    print(f"Number guessing game. Server's number: {my_num}")
    clients = {}
    while not client_guessed:
        try:
            data, address = s.recvfrom(1024)
            if address not in clients:
                clients[address] = client_count
                client_count += 1
                message = f"Welcome to number guessing game client {clients[address]}!"    
                s.sendto(message.encode('ascii'), address)

            guessed_number = struct.unpack('!I', data)[0]
            if guessed_number > my_num:
                s.sendto(b'S', address)
                print(f"Received from client {clients[address]} number {guessed_number}, sending S")
            elif guessed_number < my_num:
                s.sendto(b'H', address)
                print(f"Received from client {clients[address]} number {guessed_number}, sending H")
            elif guessed_number== my_num:
                client_guessed = True
                winner_client = clients[address]
                print(f"Received from client {clients[address]} number {guessed_number}, he/she won")
        except socket.error as msg:
            print('Error:', msg.strerror)
            break

    for client in clients.keys():
        if clients[client] == winner_client:
            s.sendto(b'G', client)
        else:
            s.sendto(b'L', client)

    print(f"The winner is {winner_client}")


if __name__ == '__main__':
    random.seed()
    start=1; stop=2**17-1
    my_num= random.randint(start,stop)
    client_guessed=False
    winner_thread=0

    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        s.bind(('0.0.0.0', 1234))
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)

    run_game(s)
