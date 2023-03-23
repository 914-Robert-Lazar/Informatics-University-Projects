#include "Planet.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

Planet* createPlanet(char* name, char* type, double distance)
{
    Planet* p = malloc(sizeof(Planet));
    p->name = malloc(sizeof(char*) * (strlen(name) + 1));
    strcpy(p->name, name);

    p->type = malloc(sizeof(char*) * (strlen(type) + 1));
    strcpy(p->type, type);

    p->distance = distance;
    return p;
}

void destroyPlanet(Planet* p)
{
    free(p->name);
    free(p->type);
    free(p);
}

char* getName(Planet* p)
{
    return p->name;
}

char* getType(Planet* p)
{
    return p->type;
}

double getDistance(Planet* p)
{
    return p->distance;
}

char* toString(Planet* p)
{
    char str[30];
    sprintf(str, "Planet %s of type %s with distance %lf", p->name, p->type, p->distance);
    return str;
}
