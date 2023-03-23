#include "DynamicArray.h"
#include <stdlib.h>
#include <assert.h>

DynamicArray* createDynamicArray(int capacity, destroyer destroyFunction)
{
    DynamicArray* array = malloc(sizeof(DynamicArray));
    array->length = 0;
    array->capacity = capacity;
    array->elements = malloc(sizeof(TElem) * array->capacity);
    array->destroyFunction = destroyFunction;
    return array;
}

void destroyDynamicArray(DynamicArray* arr)
{
    int i;
    for (i = 0; i < arr->length; i++) {
        arr->destroyFunction(arr->elements[i]);
    }
    free(arr->elements);
    free(arr);
}

int getLength(DynamicArray* arr)
{
    return arr->length;
}

TElem* get(DynamicArray* arr, int pos)
{
    return arr->elements[pos];
}

void resize(DynamicArray* arr) {
    arr->capacity *= 2;
    TElem* aux = malloc(arr->capacity * sizeof(TElem));
    for (int i = 0; i < arr->length; i++) {
        aux[i] = arr->elements[i];
    }
    free(arr->elements);
    arr->elements = aux;
}
void add(DynamicArray* arr, TElem e)
{
    if (arr->capacity == arr->length) {
        resize(arr);
    }
    arr->elements[arr->length++] = e;
}

void removeElement(DynamicArray* arr, int poz) {
    int i;
    free(arr->elements[poz]);
    for (i = poz; i < arr->length-1; i++)
        arr->elements[i] = arr->elements[i + 1];
    arr->length--;
    
}

void testDynamicArray() {
    DynamicArray* arr = createDynamicArray(2, destroyPlanet);
    assert(arr->length == 0);
    assert(arr->capacity == 2);

    Planet* p1 = createPlanet("mars", "red", 23.4);
    Planet* p2 = createPlanet("venus", "blue", 223.4);
    add(arr, p1);
    add(arr, p2);
    assert(arr->length == 2);
    assert(arr->capacity == 2);

    Planet* p3 = createPlanet("earth", "green", 123.7);
    add(arr, p3);
    assert(arr->capacity == 4);

    assert(strcmp(getName(get(arr, 0)), "mars") == 0);

    destroyDynamicArray(arr);
}
