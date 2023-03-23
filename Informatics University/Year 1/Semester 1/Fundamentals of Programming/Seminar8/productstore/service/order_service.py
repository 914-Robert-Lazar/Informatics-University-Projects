
from domain.dto import OrderDto
from domain.dto import DtoCreator


class OrderService:
    def __init__(self, order_repository, product_repository) -> None:
        self.__order_repository = order_repository
        self.__product_repository = product_repository

    def filter_orders(self, cost):
        order_dtos = self.__create_order_dtos()
        result = []
        # for dto in order_dtos:
        #     if dto.cost > cost:
        #         result.append(dto)
        result = list(filter(lambda dto: dto.cost > cost, order_dtos))
        #result = [dto for dto in order_dtos if dto.cost > cost]

        return result

    def create_order_dtos(self):
        order_dtos = []
        for order in self.__order_repository.find_all():
            product = self.__product_repository.find_by_id(order.product_id)
            order_dtos.append(DtoCreator.create_dto(product, order))

        return order_dtos

    def cost_of_orders(self):
        order_dtos = self.create_order_dtos()

        sum = 0
        for dto in order_dtos:
            sum += dto.cost

        return sum

        