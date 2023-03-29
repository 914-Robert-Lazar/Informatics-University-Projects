#pragma once
#include <iterator>

template <typename T>
class DynamicVector
{
private:
	T* elems;
	int size;
	int capacity;

public:
	DynamicVector(int capacity = 10);
	~DynamicVector();

	void add(const T& e);
	int getSize() const;

	T& operator[](int position);

private:
	// Resizes the current DynamicVector, multiplying its capacity by a given factor (real number).
	void resize(double factor = 2);

public:
	class iterator
	{
	private:
		T* ptr;
	public:
		// constructor with parameter T*
		iterator(T* elems)
		{
			this->ptr = elems;
		}
		// operator++ - pre-incrementing
		// iterator operator++(T)
		// {
		// 	++this->ptr;
		// 	return *this;
		// }
		// operator++ - post-incrementing
		iterator operator++(T)
		{
			iterator copy = *this;
			++this->ptr;
			return copy;
		}
		// dereferencing operator
		T operator*()
		{
			return *this->ptr;
		}
		// operator!=
		bool operator!=(const iterator &it)
		{
			return this->ptr != it.ptr;
		}
	};

	iterator begin()
	{
		iterator it = iterator(this->elems);
		return it;
	}

	iterator end()
	{
		iterator it = iterator(this->elems + sizeof(T) * this->size);
		return it;
	}
};

template <typename T>
DynamicVector<T>::DynamicVector(int capacity)
{
	this->size = 0;
	this->capacity = capacity;
	this->elems = new T[capacity];
}

template <typename T>
DynamicVector<T>::~DynamicVector()
{
	delete[] this->elems;
}

template <typename T>
void DynamicVector<T>::add(const T& e)
{
	if (this->size == this->capacity)
		this->resize();
	this->elems[this->size] = e;
	this->size++;
}

template <typename T>
void DynamicVector<T>::resize(double factor)
{
	this->capacity *= static_cast<int>(factor);

	T* els = new T[this->capacity];
	for (int i = 0; i < this->size; i++)
		els[i] = this->elems[i];

	delete[] this->elems;
	this->elems = els;
}

template <typename T>
int DynamicVector<T>::getSize() const
{
	return this->size;
}

template <typename T>
T& DynamicVector<T>::operator[](int position)
{
	return this->elems[position];
}