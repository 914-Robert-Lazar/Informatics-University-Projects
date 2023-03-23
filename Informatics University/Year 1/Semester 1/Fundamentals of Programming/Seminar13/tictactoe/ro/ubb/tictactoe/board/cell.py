"""

@author: radu

 
"""


class Cell():
    def __init__(self, line, coloumn, value) -> None:
        self.__line = line
        self.__coloumn = coloumn
        self.__value = value

    @property
    def line(self):
        return self.__line

    @line.setter
    def line(self, new_value):
        self.__line = new_value

    @property
    def coloumn(self):
        return self.__coloumn

    @coloumn.setter
    def coloumn(self, new_value):
        self.__coloumn = new_value

    @property
    def value(self):
        return self.__value

    @value.setter
    def value(self, new_value):
        self.__value = new_value

    def __str__(self) -> str:
        return f"line: {self.line}, coloumn: {self.coloumn}, value: {self.value}"