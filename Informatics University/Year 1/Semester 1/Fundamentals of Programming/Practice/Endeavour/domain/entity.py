class ValidatorException(Exception):
    def __init__(self, message):
        self.__message = message

    def __str__(self):
        return self.__message


class FieldValidator:
    @staticmethod
    def validate(field):
        if field.x not in ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'] and field.y not in range(0,9) and field.value not in ['B', 'E', '*', ' ']:
            raise ValidatorException("Invalid field")


class Field:
    def __init__(self, x: str, y: int, value: str):
        self.__x = x
        self.__y = y
        self.__value = value  # 0 - nothing, 1 - start, 2 - bingon, 3- endevorarur

    @property
    def x(self):
        return self.__x

    @property
    def y(self):
        return self.__y

    @property
    def value(self):
        return self.__value

    @value.setter
    def value(self, other_value):
        self.__value = other_value

    def __repr__(self):
        return f"{self.x}:{self.y} -> {self.value} \n"

    def __str__(self):
        return f"{self.x}:{self.y} -> {self.value} \n"

