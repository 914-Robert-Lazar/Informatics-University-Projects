import random
import socket
import struct


if __name__ == '__main__':
    try:
        s = socket.create_connection(('localhost', 1234))
    except socket.error as msg:
        print("Error: ",msg.strerror)
        exit(-1)

    random.seed()

    welcome_message = s.recv(1024)
    print(welcome_message.decode('ascii'))

    my_num = random.random() * random.randint(1, 2**17 - 1)
    print(my_num)
    try:
        s.sendall(struct.pack('f', my_num))
        end_message = s.recv(1024)
        print(end_message.decode('ascii'))
    except socket.error as msg:
        print("Error: ",msg.strerror)
        s.close()
        exit(-1)

    s.close()