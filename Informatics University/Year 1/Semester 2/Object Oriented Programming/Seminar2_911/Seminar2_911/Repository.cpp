#include "Repository.h"

void Repository::addSong(const Song& s)
{
    this->songs.add(s);
}

DynamicVector Repository::getAll() const
{
    return this->songs;
}

int Repository::getSize() const
{
    return this->songs.getSize();
}
