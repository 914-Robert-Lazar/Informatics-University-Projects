from unittest import TestCase

from productstore.domain.entities import Product
from productstore.domain.validators import ProductValidator


class TestProductValidator(TestCase):
    def test_validate(self):
        product = Product(1, "", 10)
        validator = ProductValidator()
        self.assertRaises(ValueError, validator.validate, product)