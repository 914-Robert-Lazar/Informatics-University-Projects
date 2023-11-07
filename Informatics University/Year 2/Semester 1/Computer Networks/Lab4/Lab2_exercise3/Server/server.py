
import random
import socket
import struct
import threading


def stopThread():
    global best_estimation, threads, my_number, mutex, event, rdv_socket, winner_id
    
    mutex.acquire()
    event.set()
    mutex.release()
    for thread in threads:
        thread.join()

    print("All threads finished")
    if winner_id != -1:
        print(f"The winner is thread {winner_id} with an error of {abs(my_number - best_estimation)}")
    else:
        print(f"No clients connected unfortunately.")
    rdv_socket.close()

def worker(client_socket):
    global best_estimation, my_number, mutex, client_count, event, winner_id, winner_thread
    my_id = client_count
    print(f"Client {my_id}")

    message = f"Hello client {my_id}! You are entering the number guessing game.\nPlease enter your number: "
    client_socket.sendall(bytes(message, 'ascii'))

    guess = client_socket.recv(100)
    guess = struct.unpack('f', guess)[0]
    mutex.acquire()
    if (winner_thread == -1 or abs(best_estimation - my_number) > abs(guess - my_number)):
        best_estimation = guess
        winner_thread = threading.get_ident()
    mutex.release()
    event.wait()

    if winner_thread == threading.get_ident():
        win_message = f"You: {my_id} have the best guess to {my_number} with an error {abs(best_estimation - my_number)}"
        client_socket.sendall(bytes(win_message, 'ascii'))
        winner_id = my_id
    else:
        lose_message = f"You lost!"
        client_socket.sendall(bytes(lose_message, 'ascii'))
    
    client_socket.close()
    print(f"Worker thread {my_id} ends\n", end="")


if __name__ == "__main__":
    random.seed()
    my_number = random.random() * random.randint(1, 2**17 - 1)
    print(f"Server number {my_number}")
    best_estimation = float()
    threads = []
    mutex = threading.Lock()
    client_count = 0
    event = threading.Event()
    event.clear()
    done = False
    winner_id = -1
    winner_thread = -1

    try:

        rdv_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        rdv_socket.bind(('127.0.0.1', 1234))
        rdv_socket.listen(5)
    except socket.error as msg:
        print(msg.strerror)
        exit(-1)

    while True:
        timer = threading.Timer(10.0, stopThread)
        timer.start()
        try:
            client_socket, client_address = rdv_socket.accept()
        except socket.error:
            exit(0)
        timer.cancel()
        thread = threading.Thread(target=worker, args=(client_socket, ))
        threads.append(thread)
        client_count += 1
        thread.start()
        
    