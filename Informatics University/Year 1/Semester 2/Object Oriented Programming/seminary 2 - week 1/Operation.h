#pragma once
#include "repository.h"
typedef struct {
	Repository* repo;
	void* data;
	char* operationType;
}Operation;

// create, destroy...