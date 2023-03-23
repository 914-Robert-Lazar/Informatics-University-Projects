
from domain.entities import Room


class Repository():
    def __init__(self) -> None:
        self.__all_entities = {}

    def get_rooms_from_file(self, file_name):
        self.__all_entities = self.read_text_file(file_name)

    def read_text_file(self, file_name):
        result = {}

        try:
            f = open(file_name, "r")
            line = f.readline().strip()
            while len(line) > 0:
                line = line.split(";")
                result[int(line[0])] = Room(line[0], line[1])
                line = f.readline().strip()
            f.close()
        except IOError as e:
            print(f"An error occured {e}")

        return result

    def __write_text_file(self, file_name):
        try:
            f = open(file_name, "w")
            for room in self.__all_entities:
                room_str = f"{room.number};{room.type}\n"
                f.write(room_str)
            f.close()
        except Exception as e:
            print(f"An error occured -{e}")

    def find_all(self):
        return list(self.__all_entities.values())

    def add_entity(self, entity) -> bool:
        if self.find_by_id(entity.number) is not None:
            return False
        
        self.__all_entities[entity.number] = entity
        return True

    def find_by_id(self, entity_id):
        if entity_id not in self.__all_entities:
            return None
        return self.__all_entities[entity_id]