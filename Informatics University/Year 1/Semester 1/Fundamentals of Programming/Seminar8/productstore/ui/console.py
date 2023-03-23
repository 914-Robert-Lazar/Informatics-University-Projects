
class Console:
    def __init__(self, product_service):
        self.__product_service = product_service

    def run_console(self):
        # todo: implement menu UI
        self.__add_products()
        self.__print_all_products()

    def __add_products(self):
        pass

    def __print_all_products(self):
        print(self.__product_service.get_all_products)

