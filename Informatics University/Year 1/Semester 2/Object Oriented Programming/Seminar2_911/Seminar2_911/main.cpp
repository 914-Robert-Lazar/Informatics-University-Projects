#include "Header.h"
#include <iostream>
#include "DynamicVector.h"
#include <crtdbg.h>
#include "Service.h"
#include <assert.h>

using namespace std;

int main()
{
	testDynamicVector();

	{
		Song s1{ "A1", "T1", 100 };
		Song s2{ "A2", "T2", 200 };

		Repository repo{};
		repo.addSong(s1);
		repo.addSong(s2);

		Service serv{ repo };
		assert(serv.getSize() == 2);

		Song s3{ "A3", "T3", 300 };
		repo.addSong(s3);
		assert(serv.getSize() == 3);
	}

	_CrtDumpMemoryLeaks();

	return 0;
}