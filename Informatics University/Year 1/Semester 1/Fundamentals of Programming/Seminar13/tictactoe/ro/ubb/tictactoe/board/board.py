"""

@author: radu

 
"""


from board.cell import Cell


class Board():
    def __init__(self, row: int, col: int) -> None:
        self.__row = row
        self.__col = col

        self.__cells = []
        for i in range(self.__row):
            curr_row = []
            for j in range(self.__col):
                curr_row.append(Cell(i, j, 0))
            self.__cells.append(curr_row)

    @property
    def cells(self):
        return self.__cells

    @cells.setter
    def cells(self, new_value):
        self.__cells = new_value

    @property
    def row(self):
        return self.__row

    @row.setter
    def row(self, new_value):
        self.__row = new_value

    @property
    def col(self):
        return self.__col

    @col.setter
    def col(self, new_value):
        self.__col = new_value
    

        

        