
def exponential_search(list, v):
    i = 1
    while list[i - 1] <= v and i <= len(list):
        i = i * 2
    if i > len(list):
        i = len(list)
    
    return binary_search(list, i // 2 - 1, i - 1, v)

def binary_search(list, l, r, v):
    while l <= r:
        mid = (l + r) // 2
        if v == list[mid]:
            return mid
        elif v < list[mid]:
            l = mid + 1
        else:
            r = mid - 1 
    return -1

if __name__ == '__main__':
    list = [1, 2, 3, 4, 5, 6, 7, 8, 9, 11]
    v = 4
    res = exponential_search(list, v)
    print(res)
