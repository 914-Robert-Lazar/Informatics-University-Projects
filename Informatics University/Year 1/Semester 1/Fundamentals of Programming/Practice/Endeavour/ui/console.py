from texttable import Texttable

from domain.entity import ValidatorException
from repository.repo import RepoException


class GameException(Exception):
    def __init__(self, message):
        self.message = message

    def __str__(self):
        return self.message

class Console:
    def __init__(self, field_service):
        self.__field_service = field_service
        self.__row = 8
        self.__column = 8
        self.__field_service.generate_fields()
        self.__field_service.generate_stars()
        x, y = self.__field_service.generate_E()
        self.__shipx = x
        self.__shipy = y
        self.__nm_of_bingons = 3
        self.__field_service.generate_bingons(self.__nm_of_bingons)
        self.__commands_with_0_arg = {"cheat": self.cheat}
        self.__commands_with_1_arg = {"warp": self.warp, "shoot": self.shoot}
        self.display_board()
        self.game_over = False

    @property
    def shipx(self):
        return self.__shipx

    @property
    def shipy(self):
        return self.__shipy

    def read_command(self):
        line = input(">")
        blank = line.find(" ")
        if blank == -1:
            return line, []
        cmd = line[:blank]
        args = line[blank+1:]
        args = args.split(' ')
        args = [arg.strip() for arg in args]
        return cmd, args

    def run_console(self):
        while not self.game_over:
            cmd, args = self.read_command()
            if cmd == "exit" and len(args) == 0:
                break
            try:
                if len(args) == 0:
                    self.__commands_with_0_arg[cmd]()
                elif len(args) == 1:
                    self.__commands_with_1_arg[cmd](*args)
            except RepoException as re:
                print(re)
            except ValidatorException as ve:
                print(ve)
            except GameException as ge:
                print(ge)
            except KeyError:
                print("Invalid command")
            else:
                if not self.game_over and cmd != "cheat":
                    self.display_board()


    def cheat(self):
        letters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H']
        t = Texttable()
        t.header([0, 1, 2, 3, 4, 5, 6, 7, 8, 9])
        fields = self.__field_service.get_all()
        index = 0
        for i in letters:
            row = [i]
            for j in range(0, 9):
                row.append(fields[index].value)
                index += 1
            t.add_row(row)
        print(t.draw())


    def display_board(self):
        letters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H']
        t = Texttable()
        t.header([0, 1, 2, 3, 4, 5, 6, 7, 8, 9])
        fields = self.__field_service.get_all()
        index = 0
        for i in letters:
            row = [i]
            for j in range(0,9):
                if ord(i) in range(ord(self.__shipx)-1, ord(self.__shipx) + 2) and j in range(self.__shipy-1, self.__shipy+2):
                    row.append(fields[index].value)
                else:
                    space = ''
                    row.append(space)
                index += 1
            t.add_row(row)
        print(t.draw())

    def shoot(self, xy:str):
        x = xy[0]
        y = int(xy[1]) - 1
        if abs(ord(x) - ord(self.__shipx)) > 1 or abs(y - self.__shipy) > 1:
            raise GameException("Too far")

        field_to_destroy = self.__field_service.get_by_coord(x, y)
        if field_to_destroy is None:
            raise GameException("Invalid position")
        if field_to_destroy.value == 'B':
            self.__nm_of_bingons -= 1
            for field in self.__field_service.get_all():
                if field.value == 'B':
                    field.value = ' '
                    self.__field_service.update(field.x, field.y, ' ')
            self.__field_service.generate_bingons(self.__nm_of_bingons)
            self.__field_service.update(x, y, ' ')
        if self.__nm_of_bingons == 0:
            print("You Won")
            self.game_over = True

    def warp(self, xy: str):
        '''
        Updates the ship coordinates to the given one an tells us if we ran into something -> if so game over
        :param xy: string which contains the coordinates we want to move at -> first letter is the row [A,B,C,D,E,F,G,H] second letter is the column [1, 2, 3, 4, 5, 6, 7, 8]
        exception: GameException is raised if the coordinates are invalid
        '''
        x = xy[0]
        y = int(xy[1:]) - 1
        if x != self.__shipx and y != self.__shipy and abs(ord(x) - ord(self.__shipx)) != abs(y - self.__shipy):
            raise GameException("Invalid position")
        field_to_occupy = self.__field_service.get_by_coord(x, y)
        if field_to_occupy is None:
            raise GameException("Invalid position")
        if field_to_occupy.value != ' ':
            print("GameOver! You run into something!")
            self.game_over = True
        self.__field_service.update(self.__shipx, self.__shipy, ' ')
        self.__shipx = x
        self.__shipy = y
        self.__field_service.warp(x, y)
