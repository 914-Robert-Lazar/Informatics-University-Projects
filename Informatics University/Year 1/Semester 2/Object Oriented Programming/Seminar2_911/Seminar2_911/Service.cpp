#include "Service.h"

Service::Service(Repository& _repo): repo {_repo}
{
}

void Service::addSong(const Song& s)
{
	this->repo.addSong(s);
}

int Service::getSize() const
{
	return this->repo.getSize();
}
