#pragma once
#include <string>

class Song
{
private:
	std::string artist;
	std::string title;
	int duration;

public:
	Song();
	Song(const std::string& artist, const std::string& title, int duration);

	std::string getArtist() const;
	void setArtist(const std::string& artist);
};

