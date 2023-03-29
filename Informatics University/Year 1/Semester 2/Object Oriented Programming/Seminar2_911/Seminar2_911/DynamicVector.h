#pragma once
#include "Song.h"

typedef Song TElem;

class DynamicVector
{
private:
	TElem* elems;
	int capacity, size;

	void resize();

public:
	DynamicVector(int cap = 10);
	~DynamicVector();

	DynamicVector(const DynamicVector& v);
	DynamicVector& operator=(const DynamicVector& v);

	void add(TElem);

	int getSize() const;
};

void testDynamicVector();

