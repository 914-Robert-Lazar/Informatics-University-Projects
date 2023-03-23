"""

@author: radu

 
"""

from abc import abstractmethod

from board.cell import Cell


class Player:
    def __init__(self, name, board):
        self.__name = name
        self._board = board

    @abstractmethod
    def move(self, *args) -> Cell:
        """
        :param args:
        :return: the new cell or None
        """
        pass

    @property
    def name(self):
        return self.__name

    @name.setter
    def name(self, new_value):
        self.__name = new_value
