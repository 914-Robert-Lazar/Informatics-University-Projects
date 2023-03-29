#pragma once

#include "DynamicVector.h"

class Repository
{
private:
	DynamicVector songs;

public:
	void addSong(const Song& s);
	DynamicVector getAll() const;
	int getSize() const;
};

