import sys


def x_to_power_of_n(x, n):
    if (n == 0):
        return 1
    x_power = x_to_power_of_n(x, n // 2)
    if (n % 2 == 1):
        return x_power * x_power * x
    return x_power * x_power

if __name__ == '__main__':
    sys.set_int_max_str_digits(100000)
    print(x_to_power_of_n(2, 100000))