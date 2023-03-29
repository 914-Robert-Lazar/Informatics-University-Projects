#pragma once
#include "Repository.h"

class Service
{
private:
	Repository& repo;

public:
	Service(Repository& _repo);
	void addSong(const Song& s);
	int getSize() const;
};

