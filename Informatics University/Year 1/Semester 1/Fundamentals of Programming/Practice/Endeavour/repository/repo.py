class RepoException(Exception):
    def __init__(self, message):
        self.message = message

    def __str__(self):
        return self.message

class Repo:
    def __init__(self, validator):
        self.__validator = validator
        self.__fields = {}

    def find_all(self):
        return list(self.__fields.values())

    def find_by_coord(self, x: str, y: int):
        if (x, y) not in self.__fields.keys():
            return None
        return self.__fields[(x, y)]

    def add(self, field):
        self.__validator.validate(field)
        if self.find_by_coord(field.x, field.y) is not None:
            raise RepoException("Field already in repo")
        self.__fields[(field.x, field.y)] = field

    def update(self, field):
        self.__validator.validate(field)
        if self.find_by_coord(field.x, field.y) is None:
            raise RepoException("Field nonexistent")
        self.__fields[(field.x, field.y)] = field
