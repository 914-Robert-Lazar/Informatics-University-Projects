
def sum_of_even_on_even(array):
    print(array)
    if len(array) == 1:
        if array[0] % 2 == 0:
            return array[0]
        return 0

    return sum_of_even_on_even(array[:len(array) // 2]) + sum_of_even_on_even(array[len(array) // 2:])

array = [2, 4, 5, 6, 4, 13, 4]
array = array[1::2]
print(sum_of_even_on_even(array))

        
    