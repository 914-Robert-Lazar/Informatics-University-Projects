#pragma once

#include "Planet.h"

typedef void* TElem;

typedef void (*destroyer)(TElem);

typedef struct
{
	TElem* elements;
	int capacity;
	int length;
	destroyer destroyFunction;
} DynamicArray;


DynamicArray* createDynamicArray(int capacity, destroyer destroyFunction);
void destroyDynamicArray(DynamicArray* arr);
int getLength(DynamicArray* arr);
TElem* get(DynamicArray* arr, int pos);
void add(DynamicArray* arr, TElem e);
void removeElement(DynamicArray* arr, int poz);

void testDynamicArray();