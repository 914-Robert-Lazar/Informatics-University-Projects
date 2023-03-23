#include <exception>
#include "BagIterator.h"
#include "Bag.h"

using namespace std;


BagIterator::BagIterator(const Bag& c): bag(c)
{
	this->currentPos = 0;
	this->currentNumberOfCurrentElement = 0;
}
//Theta(1)

void BagIterator::first() {
	this->currentPos = 0;
	this->currentNumberOfCurrentElement = 0;
}
//Theta(1)

void BagIterator::next() {
	if (this->currentPos == this->bag.currentLength)
	{
		throw exception();
	}
	if (this->currentNumberOfCurrentElement == this->bag.frequencies[this->currentPos] - 1)
	{
		if (this->currentPos < this->bag.currentLength)
		{
			do
			{
				this->currentPos++;
			} while (this->currentPos < this->bag.currentLength && this->bag.frequencies[this->currentPos] == 0);
		}
		this->currentNumberOfCurrentElement = 0;
	}
	else
	{
		this->currentNumberOfCurrentElement++;
	}
}
//O(max(Si - S(i - 1))), where S is the sorted unique elements


bool BagIterator::valid() const {
	if (this->currentPos < this->bag.currentLength)
	{
		return true;
	}
	return false;
}
//Theta(1)

TElem BagIterator::getCurrent() const
{
	if (this->currentPos == this->bag.currentLength)
	{
		throw exception();
	}
	return this->currentPos + this->bag.minimumNumber;
}
//Theta(1)