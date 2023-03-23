
from domain.entities import Product
from domain.entities import Order
from repository.product_repository import GenericRepository
from service.order_service import OrderService

if __name__ == '__main__':
    product = Product(1, "Laptop", 1500)
    product2 = Product(2, "Keyboard", 400)

    order = Order(1, 2, 5)
    order2 = Order(2, 1, 2)

    order_repository = GenericRepository()
    product_repository = GenericRepository()
    product_repository.save(product)
    product_repository.save(product2)
    order_repository.save(order)
    order_repository.save(order2)

    order_service = OrderService(order_repository, product_repository)
    print(order_service.create_order_dtos())
    print(order_service.cost_of_orders())

