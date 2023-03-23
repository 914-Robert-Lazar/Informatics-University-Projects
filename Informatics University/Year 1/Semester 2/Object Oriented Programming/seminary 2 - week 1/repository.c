#include "repository.h"
#include <stdlib.h>

Repository* createRepository() {
	Repository* repo = malloc(sizeof(Repository));
	repo->arr = createDynamicArray(10, destroyPlanet);
	return repo;
}

void destroyRepository(Repository* repo) {
	destroyDynamicArray(repo->arr);
	free(repo);
}

Planet* getByPositionRepo(Repository* repo, int index) {
	return get(repo->arr, index);
}

int findPlanetRepo(Repository* repo, Planet* planet) {
	for (int i = 0; i < repo->arr->length; i++) {
		Planet* here = repo->arr->elements[i];
		if (strcmp(here->name, planet->name) == 0)
			return i;
	}
	return -1;
}

int addRepo(Repository* repo, Planet* planet) {
	if (findPlanetRepo(repo, planet) != -1)
		return 1;
	add(repo->arr, planet);
	return 0;
}
