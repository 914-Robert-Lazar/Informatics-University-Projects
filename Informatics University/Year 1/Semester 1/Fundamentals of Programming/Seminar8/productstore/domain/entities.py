from dataclasses import dataclass

# class Product:
#     def __init__(self, product_id, name, price):
#         self.__product_id = product_id
#         self.__name = name
#         self.__price = price

#     def get_product_id(self):
#         return self.__product_id

#     def get_name(self):
#         return self.__name

#     def get_price(self):
#         return self.__price

#     def set_product_id(self, product_id):
#         self.__product_id = product_id

#     def set_name(self, name):
#         self.__name = name

#     def set_price(self, price):
#         self.__price = price

#     def __str__(self):
#         return f"Product ID: {self.__product_id} \nProduct name: {self.__name} \nProduct price: {self.__price}"

# class Order:
#     def __init__(self, order_id, product_id, quantity):
#         self.__order_id = order_id
#         self.__product_id = product_id
#         self.__quantity = quantity

#     @property
#     def order_id(self):
#         return self.__order_id

#     @order_id.setter
#     def order_id(self, order_id):
#         self.__order_id = order_id

#     @property
#     def product_id(self):
#         return self.__product_id

#     @product_id.setter
#     def product_id(self, product_id):
#         self.__product_id = product_id

#     @property
#     def quantity(self):
#         return self.__quantity

#     @quantity.setter
#     def quantity(self, quantity):
#         self.__quantity = quantity
@dataclass
class Product:
    __id: int
    name: str
    price: int

    @property
    def id(self):
        return self.__id

product = Product(15, "dsads", 34)

@dataclass
class Order:
    __order_id: int
    product_id: int
    quantity: int

    @property
    def id(self):
        return self.__order_id
