"""

@author: radu

 
"""

from player.player import Player
from board.cell import Cell


class Human(Player):
    def __init__(self, name, board):
        super().__init__(name, board)

    def move(self, line, coloumn, value):
        self._board.cells[line][coloumn].value = value
        return self._board.cells[line][coloumn]