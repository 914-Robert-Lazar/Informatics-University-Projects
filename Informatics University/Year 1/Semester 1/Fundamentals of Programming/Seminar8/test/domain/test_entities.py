from unittest import TestCase

from productstore.domain.entities import Product


class TestProduct(TestCase):
    def setUp(self):
        self.product = Product(1, "p1", 10)

    def test_id(self):
        self.assertEqual(self.product_id, 1, "id should be 1")

    def tearDown(self):
        pass
