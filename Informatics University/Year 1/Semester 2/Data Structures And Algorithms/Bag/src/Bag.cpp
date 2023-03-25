#include "Bag.h"
#include "BagIterator.h"
#include <exception>
#include <iostream>
using namespace std;


Bag::Bag() {
	this->capacity = 1;
	this->currentLength = 0;
	this->minimumNumber = INT_MAX;
	this->frequencies = new int[this->capacity];
	this->frequencies[0] = 0;
}
//Theta(1)

void Bag::add(TElem elem) {
	if (this->currentLength == 0)
	{
		this->currentLength = 1;
		this->frequencies[currentLength - 1]++;
		this->minimumNumber = elem;
	}
	else
	{
		//Check for resizing
		int differenceFromOldToNewSize = -1;
		if (elem < this->minimumNumber)
		{
			differenceFromOldToNewSize = this->minimumNumber - elem;
		}
		else if (elem > this->minimumNumber + this->currentLength - 1)
		{
			differenceFromOldToNewSize = elem - (this->minimumNumber + this->currentLength - 1);
		}
		if (differenceFromOldToNewSize != -1)
		{
			int newCapacity = this->capacity;
			while (this->currentLength + differenceFromOldToNewSize > newCapacity)
			{
				newCapacity *= 2;
			}
			//Resizing
			if (newCapacity > this->capacity)
			{
				this->capacity = newCapacity;
				int *newFrequencies = new int[this->capacity];
				for (int i = 0; i < this->currentLength; ++i)
				{
					newFrequencies[i] = this->frequencies[i];
				}
				// this->capacity *= 2;
				delete[] this->frequencies;
				this->frequencies = newFrequencies;
			}
			//Shift to right by difference
			if (elem < this->minimumNumber)
			{
				for (int i = this->currentLength; i >= 0; --i)
				{
					this->frequencies[i + differenceFromOldToNewSize] = this->frequencies[i];
				}
				for (int i = 0; i < differenceFromOldToNewSize; ++i)
				{
					this->frequencies[i] = 0;
				}
				this->minimumNumber = elem;
			}
			//Add new zeros to end
			else
			{
				for (int i = 0; i < differenceFromOldToNewSize; ++i)
				{
					this->frequencies[i + this->currentLength] = 0;
				}
			}
			this->currentLength += differenceFromOldToNewSize;
		}
		//Putting in the new TElem
		this->frequencies[elem - this->minimumNumber]++;
	}
	this->nrTElems++;
	// cout << "Add: ";
	// for (int i = 0; i < this->currentLength; ++i)
	// {
	// 	cout << this->frequencies[i] << " ";
	// }
	// cout << endl;
}
// O(max(elem - maximum element in bag, minimum element in bag - elem))


bool Bag::remove(TElem elem) {
	if (elem >= this->minimumNumber && elem <= this->minimumNumber + this->currentLength - 1 &&
	this->frequencies[elem - this->minimumNumber] > 0)
	{
		this->frequencies[elem - this->minimumNumber]--;
		this->nrTElems--;

		//Cut the unnecesarry 0s at left
		if (elem == this->minimumNumber)
		{
			int shiftCounter;
			for (shiftCounter = 0; shiftCounter < this->currentLength; ++shiftCounter)
			{
				if (this->frequencies[shiftCounter] != 0)
				{
					break;
				}
			}
			if (shiftCounter > 0)
			{
				for (int i = 0; i < this->currentLength; ++i)
				{
					if (i < this->currentLength - shiftCounter)
					{
						this->frequencies[i] = this->frequencies[i + shiftCounter];
					}
					else
					{
						this->frequencies[i] = 0;
					}
				}
				this->currentLength -= shiftCounter;
				this->minimumNumber += shiftCounter;
			}
		}
		
		//Cut the unnecesarry 0s at right
		if (elem == this->minimumNumber + this->currentLength - 1)
		{
			int shiftCounter = 0;
			for (int i = this->currentLength - 1; i >= 0; --i)
			{
				if (this->frequencies[i] != 0)
				{
					break;
				}
				shiftCounter++;
			}
			this->currentLength -= shiftCounter;
		}
		return true;
	}
	return false; 
}
//O(Maximum element - minimum element in bag)

bool Bag::search(TElem elem) const {
	return elem >= this->minimumNumber && elem <= this->minimumNumber + this->currentLength - 1 &&
	 this->frequencies[elem - this->minimumNumber] > 0; 
}
//Theta(1)

int Bag::nrOccurrences(TElem elem) const {
	if (elem < this->minimumNumber || elem > this->minimumNumber + this->currentLength - 1)
	{
		return 0;
	}
	return this->frequencies[elem - this->minimumNumber]; 
}
//Theta(1)

int Bag::size() const {
	return this->nrTElems;
}
//Theta(1)

bool Bag::isEmpty() const {
	return this->nrTElems == 0;
}
//Theta(1)

BagIterator Bag::iterator() const {
	return BagIterator(*this);
}
//Theta(1)

Bag::~Bag() {
	delete[] this->frequencies;
}

