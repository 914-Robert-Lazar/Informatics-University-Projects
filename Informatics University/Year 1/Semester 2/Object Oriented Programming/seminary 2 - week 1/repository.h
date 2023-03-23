#pragma once
#include "DynamicArray.h"

typedef struct {
	DynamicArray* arr;
} Repository;

Repository* createRepository();
void destroyRepository(Repository*);
int addRepo(Repository*, Planet*);
Planet* getByPositionRepo(Repository*, int);
