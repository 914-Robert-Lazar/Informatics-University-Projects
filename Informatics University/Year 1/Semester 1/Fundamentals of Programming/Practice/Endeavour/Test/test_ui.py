import unittest

from domain.entity import FieldValidator
from repository.repo import Repo, RepoException
from service.service import FieldService
from ui.console import Console, GameException


class TestUi(unittest.TestCase):
    def setUp(self) -> None:
        self.repo = Repo(FieldValidator())
        self.service = FieldService(self.repo)
        self.console = Console(self.service)

    def test_warp(self):
        beforex = self.console.shipx
        beforey = self.console.shipy
        field = self.service.get_by_coord(str(ord(self.console.shipx)-1), self.console.shipy-1)
        if field is None:
            self.assertRaises(RepoException, self.service.warp, str(ord(self.console.shipx)-1), self.console.shipy-1)
        else:
            self.assertEqual(self.console.shipx, str(ord(beforex) - 1))
            self.assertEqual(self.console.shipy, beforey - 1)