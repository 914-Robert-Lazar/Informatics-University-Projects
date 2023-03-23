
from dataclasses import dataclass
from datetime import datetime


class Room():
    def __init__(self, number, type) -> None:
        self.__number = number
        self.__type = type

    @property
    def number(self):
        return self.__number

    @number.setter
    def number(self, new_value):
        self.__number = new_value

    @property
    def type(self):
        return self.__type

    @type.setter
    def type(self, new_value):
        self.__type = new_value

    def __str__(self) -> str:
        return f"Room number: {self.__number}, type: {self.__type}"

@dataclass
class Reservation():
    number: int
    family_name: str
    room_type: str
    number_of_guests: int
    arrival_date: datetime
    departure_date: datetime

