# def f(l):
#     """Checks whether in the iterable l there is an odd number

#     Args:
#         l (iterable): the iterable data structure

#     Raises:
#         ValueError: if l is None

#     Returns:
#         bool: Whether it contains an odd number
#     """
#     if (l is None):
#         raise ValueError()
#     for e in l:
#         if e%2==1:
#             return True

#     return False

def f(n):
    s = 0
    m = n
    while(m != 0):
        print(m)
        s = s + m / 10
        m = m / 10
    return s

print(f(15))