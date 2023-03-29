#include "DynamicVector.h"
#include <assert.h>

DynamicVector::DynamicVector(int cap): capacity{cap}, size{0}
{
	this->elems = new TElem[this->capacity];
}

DynamicVector::~DynamicVector()
{
	delete[] this->elems;
}

DynamicVector::DynamicVector(const DynamicVector& v)
{
	this->size = v.size;
	this->capacity = v.capacity;

	this->elems = new TElem[this->capacity];
	for (int i = 0; i < this->size; i++)
		this->elems[i] = v.elems[i];
}

DynamicVector& DynamicVector::operator=(const DynamicVector& v)
{
	if (this == &v)
		return *this;

	this->size = v.size;
	this->capacity = v.capacity;

	delete[] this->elems;
	this->elems = new TElem[this->capacity];
	for (int i = 0; i < this->size; i++)
		this->elems[i] = v.elems[i];

	return *this;
}

void DynamicVector::resize()
{
	this->capacity *= 2;

	TElem* newData = new TElem[this->capacity];

	for (int i = 0; i < this->size; i++)
		newData[i] = this->elems[i];

	delete[] this->elems;

	this->elems = newData;
}

int DynamicVector::getSize() const
{
	return this->size;
}

void testDynamicVector()
{
	DynamicVector vector{ 1 };

	Song song("ABC", "SONG NAME", 100);

	assert(vector.getSize() == 0);

	vector.add(song);

	assert(vector.getSize() == 1);

	DynamicVector v2 = vector;
	//DynamicVector v2{ vector };
	assert(v2.getSize() == 1);

	DynamicVector v3{};
	assert(v3.getSize() == 0);
	v3 = v2;
	assert(v3.getSize() == 1);
	Song song1{ "artist2", "song2", 10 };
	v3.add(song1);
	assert(v3.getSize() == 2);

}


void DynamicVector::add(TElem element)
{

	if (this->size == this->capacity)
		this->resize();

	this->elems[this->size] = element;
	this->size++;

}
