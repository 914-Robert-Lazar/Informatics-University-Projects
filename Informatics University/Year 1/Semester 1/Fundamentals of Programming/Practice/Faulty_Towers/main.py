from service.service import Service
from ui.console import Console
from repository.repository import Repository

if __name__ == "__main__":
    room_repo = Repository()
    res_Repo = Repository()
    service = Service(room_repo,res_Repo)
    console = Console(service)