class SparseMatrix():
    def __init__(self, row, col) -> None:
        self.__row = row
        self.__col = col
        self.__values = {}

    def set(self, x, y, value):
        if x < 0 or x >= self.__row or y < 0 or y >= self.__col:
            raise ValueError("Not valid xd")
        if x not in self.__values:
            self.__values[x] = {}
        self.__values[x][y] = value

    def get(self, x, y):
        if x < 0 or x >= self.__row or y < 0 or y >= self.__col:
            raise ValueError("Not valid xd")
        
        if x not in self.__values or y not in self.__values[x]:
            return 0

        return self.__values[x][y]

    def __str__(self) -> str:
        multiline_string = """"""
        for i in range(self.__row):
            for j in range(self.__col):
                multiline_string += str(self.get(i, j)) + " "
            multiline_string += "\n"
        return multiline_string
        
        
m1 = SparseMatrix(3, 3)
m1.set(1,1,2)
m1.set(2,2,4)
print(m1)

try:
    m1.set(3,3,99)
except Exception as e:
    print(type(e))

m1.set(1,1,m1.get(1,1) + 1)
print(m1)