"""

@author: radu

 
"""
from random import randint
from strategy.strategy import Strategy


class NoStrategy(Strategy):
    def __init__(self, board) -> None:
        super().__init__()
        self.__board = board

    def move(self, value):
        while True:
            line = randint(0, self.__board.row - 1)
            coloumn = randint(0, self.__board.col - 1)
            if self.__board.cells[line][coloumn].value == 0:
                return line, coloumn