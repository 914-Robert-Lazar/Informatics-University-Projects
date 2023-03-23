
from dataclasses import dataclass


@dataclass
class OrderDto:
    product_name: str
    quantity: int
    cost: int

class DtoCreator:

    @staticmethod
    def create_dto(product, order):
        return OrderDto(product.name, order.quantity, product.price * order.quantity)
