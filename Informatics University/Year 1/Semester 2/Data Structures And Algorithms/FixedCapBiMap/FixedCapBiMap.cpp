#include "FixedCapBiMap.h"
#include "FixedCapBiMapIterator.h"
#include <exception>

using namespace std;

FixedCapBiMap::FixedCapBiMap(int capacity) {
	if (capacity <= 0)
	{
		throw exception();
	}
	this->capacity = capacity;
	this->nrPairs = 0;
	this->elements = new TElem[capacity];
}
//Theta(1)

FixedCapBiMap::~FixedCapBiMap() {
	delete[] this->elements;
}
//Theta(1)

bool FixedCapBiMap::add(TKey c, TValue v){
	if (this->nrPairs == this->capacity)
	{
		throw exception();
	}

	int count = 0;
	for (int i = 0; i < this->nrPairs && count < 2; ++i)
	{
		if (this->elements[i].first == c)
		{
			count++;
		}
	}
	if (count == 2)
	{
		return false;
	}

	this->elements[this->nrPairs] = make_pair(c, v);
	this->nrPairs++;
	return true;
}
//Best Case:Theta(1), Worst case: Theta(nrPairs) => Total complexity: O(nrPairs)

ValuePair FixedCapBiMap::search(TKey c) const{
	ValuePair returnedValue = make_pair(NULL_TVALUE, NULL_TVALUE);

	int count = 0;
	for (int i = 0; i < this->nrPairs && count < 2; ++i)
	{
		if (this->elements[i].first == c)
		{
			if (count == 0)
			{
				returnedValue.first = this->elements[i].second;
			}
			else
			{
				returnedValue.second = this->elements[i].second;
			}
			count++;
		}
	}

	return returnedValue;
}
//Best Case:Theta(1), Worst case: Theta(nrPairs) => Total complexity: O(nrPairs)

bool FixedCapBiMap::remove(TKey c, TValue v){
	int i = 0;
	for (; i < this->nrPairs; ++i)
	{
		if (this->elements[i].first == c && this->elements[i].second == v)
		{
			break;
		}
	}
	if (i < this->nrPairs)
	{
		this->elements[i] = this->elements[this->nrPairs - 1];
		this->nrPairs--;
		return true;
	}
	return false;
}
//Best Case:Theta(1), Worst case: Theta(nrPairs) => Total complexity: O(nrPairs)

int FixedCapBiMap::size() const {
	return this->nrPairs;
}
//Theta(1)

bool FixedCapBiMap::isEmpty() const{
	if (this->nrPairs == 0)
	{
		return true;
	}
	return false;
}
//Theta(1)

bool FixedCapBiMap::isFull() const {
	if (this->nrPairs == this->capacity)
	{
		return true;
	}
	return false;
}
//Theta(1)

FixedCapBiMapIterator FixedCapBiMap::iterator() const {
	return FixedCapBiMapIterator(*this);
}
//Theta(1)


