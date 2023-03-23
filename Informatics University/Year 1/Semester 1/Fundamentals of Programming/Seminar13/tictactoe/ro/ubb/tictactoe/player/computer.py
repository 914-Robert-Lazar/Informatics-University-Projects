"""

@author: radu

 
"""

from player.player import Player


class Computer(Player):
    def __init__(self, name, board, strategy):
        super().__init__(name, board)
        self.__strategy = strategy

    def move(self, line, column, value):
        line, column = self.__strategy.move(value)
        self._board.cells[line][column].value = value
        return self._board.cells[line][column]
        