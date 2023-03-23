
from cmath import inf


def max_element(list):
    if (len(list) == 0):
        return -inf
    if (len(list) == 1):
        return list[0]
    mid = len(list) // 2
    return max(max_element(list[:mid]), max_element(list[mid:]))

if __name__ == '__main__':
    list = [56, 34, 23, 87, 67, 4, 23, 63, 81, 72, 21]
    list = []
    print(max_element(list))