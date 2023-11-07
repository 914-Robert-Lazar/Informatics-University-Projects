import socket, struct

if __name__ == "__main__":
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind(('0.0.0.0', 1234))
    s.listen(5)
    
    print("Listening for incoming connections")
    while (True):
        c, a = s.accept()
        print(f"Connected by {a}")
        d = c.recv(2)
        d = struct.unpack('!H', d)
        d = d[0]
        print(d)
        divisors = []
        for i in range(1, d):
            if d % i == 0:
                divisors.append(i)

        c.send(struct.pack('!H', len(divisors)))
        for divisor in divisors:
            c.send(struct.pack('!H', divisor))
        if d == 0:
            break
        c.close()


    s.close()

        