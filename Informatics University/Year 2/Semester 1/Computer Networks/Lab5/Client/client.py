import random
import socket
import struct
import time


if __name__ == '__main__':
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)

    finished=False
    sr = 1; er=2**17-1
    random.seed()
    address = ('localhost', 1234)
    step_count = 0
    while not finished:
        my_guess = random.randint(sr, er)
        try:
            s.sendto(struct.pack('!I', my_guess), address)
            if step_count == 0:
                data, address = s.recvfrom(1024)
                print(data.decode('ascii'))
            
            answer, address = s.recvfrom(1024)
            print('Sent ',my_guess,' Answer ',answer.decode('ascii'))
            step_count += 1
            if answer==b'H':
                sr = my_guess
            if answer==b'S':
                er = my_guess
            if answer==b'G' or answer==b'L':
                finished=True
        except socket.error as msg:
            print('Error: ',msg.strerror)
            s.close()
            exit(-2)
        time.sleep(1)

    s.close()
    if answer==b'G':
        print("I am the winner with",my_guess,"in", step_count,"steps")
    else:
        print(f"I lost in {step_count} steps!!!")