from domain.entity import FieldValidator
from repository.repo import Repo
from service.service import FieldService
from ui.console import Console

if __name__ == "__main__":
    repo = Repo(FieldValidator())
    service = FieldService(repo)
    console = Console(service)
    console.run_console()