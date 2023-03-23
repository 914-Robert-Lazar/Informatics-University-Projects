import random

from domain.entity import Field



class FieldService:
    def __init__(self, field_repo):
        self.__field_repo = field_repo

    def get_all(self):
        return self.__field_repo.find_all()

    def get_by_coord(self, x, y):
        return self.__field_repo.find_by_coord(x, y)

    def add(self, x, y, value):
        field = Field(x, y, value)
        self.__field_repo.add(field)

    def update(self, x, y, value):
        field = Field(x, y, value)
        self.__field_repo.update(field)

    def generate_fields(self):
        for x in ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H']:
            for y in range(0, 9):
                field = Field(x, y, ' ')
                self.__field_repo.add(field)

    def generate_stars(self):
        letters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H']
        for i in range(0, 10):
            good = False
            while not good:
                x = random.choice(letters)
                y = random.randint(1, 8)
                value = '*'
                good = True
                for xi in range(ord(x) - 1, ord(x) + 2):
                    for yi in range(y - 1, y + 2):
                        field = self.__field_repo.find_by_coord(chr(xi), yi)
                        if field is not None and field.value != ' ':
                            good = False
                            break
                if good:
                    self.__field_repo.update(Field(x, y, value))

    def generate_E(self):
        letters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H']
        good = False
        while not good:
            x = random.choice(letters)
            y = random.randint(1, 8)
            value = 'E'
            good = True
            field = self.__field_repo.find_by_coord(x, y)
            if field.value != ' ':
                good = False
            if good:
                self.__field_repo.update(Field(x, y, value))
                return x, y

    def generate_bingons(self, n):
        letters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H']
        for i in range(0, n):
            good = False
            while not good:
                x = random.choice(letters)
                y = random.randint(1, 8)
                value = 'B'
                good = True
                field = self.__field_repo.find_by_coord(x, y)
                if field.value != ' ':
                    good = False
                if good:
                    self.__field_repo.update(Field(x, y, value))

    def warp(self, x, y):
        field = Field(x, y, "E")
        self.__field_repo.update(field)