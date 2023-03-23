from random import randint

from domain.entities import Reservation


class Service():
    def __init__(self, room_repository, reservation_repository) -> None:
        self.__room_repository = room_repository
        self.__reservation_repository = reservation_repository

    def get_rooms_from_file(self, file_name):
        self.__room_repository.get_rooms_from_file(file_name)

    def get_rooms(self):
        return self.__room_repository.find_all()

    def add_reservation(self, family_name, type, guests, ar_date, dep_date):
        while True:
            res_num = randint(1000, 9999)
            if self.__reservation_repository.find_by_id(res_num) is None:
                self.__reservation_repository.add_entity(Reservation(res_num, family_name, type, guests, ar_date, dep_date))
                break
