
from cmath import inf

def gcd(list):
    if (len(list) == 0):
        return inf
    if (len(list) == 1):
        return list[0]
    mid = len(list) // 2
    left_gcd = gcd(list[:mid]); right_gcd = gcd(list[mid:])
    while (right_gcd):
        left_gcd, right_gcd = right_gcd, left_gcd % right_gcd
    return left_gcd

if __name__ == '__main__':
    list = []
    print(gcd(list))