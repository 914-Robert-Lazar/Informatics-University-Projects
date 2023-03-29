#include "Song.h"

Song::Song() : artist{""}, title{""}, duration{0}
{
}

Song::Song(const std::string& _artist,
	const std::string& _title, 
	int _duration) : artist{_artist},
					 title{_title},
					 duration{_duration}
{
	/*this->artist = _artist;
	this->title = _title;
	this->duration = _duration;*/
}

std::string Song::getArtist() const
{
	return this->artist;
}

void Song::setArtist(const std::string& artist)
{
	this->artist = artist;
}
