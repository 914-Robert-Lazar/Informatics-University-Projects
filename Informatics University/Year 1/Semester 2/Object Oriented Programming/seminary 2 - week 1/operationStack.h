#pragma once
#include "DynamicArray.h"
#include "Operation.h"

typedef struct {
	DynamicArray* arr; //array of structures Operation
} OperationStack;

// create, destroy

void push(OperationStack*, Operation*);
Operation* pop(OperationStack*);
int isEmpty(OperationStack*);