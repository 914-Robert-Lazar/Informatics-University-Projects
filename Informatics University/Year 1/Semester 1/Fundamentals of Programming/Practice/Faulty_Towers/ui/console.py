from datetime import datetime


class Console():
    def __init__(self, service) -> None:
        self.__service = service
        self.__run_program()

    def __run_program(self):
        self.__service.get_rooms_from_file("rooms.txt")
        rooms = self.__service.get_rooms()
        for room in rooms:
            print(room)

        options = {1: self.add_reservation}
        while True:
            opt = self.print_menu()
            options[opt]()


    def print_menu(self):
        print("1. Create a reservation.")
        try:
            return int(input("Please choose the operation: "))
        except ValueError as e:
            print("Invalid input", e)

    def add_reservation(self):
        try:
            family_name = input("Family name: ")
            if family_name == "":
                print("Invalid input")
                return
            room_type = input("Room type: ")
            n_o_guests = int(input("Number of guests: "))
            if n_o_guests < 1 or n_o_guests > 4:
                print("Invalid input")
                return

            date_components = input('Enter arrival date formatted as YYYY-MM-DD: ').split('-')
            year, month, day = [int(item) for item in date_components]
            ar_date = datetime(year, month, day)

            date_components = input('Enter departure date formatted as YYYY-MM-DD: ').split('-')
            year, month, day = [int(item) for item in date_components]
            dep_date = datetime(year, month, day)

            self.__service.add_reservation(family_name, room_type, n_o_guests, ar_date, dep_date)
        except Exception as e:
            print("Invalid input")
            return
