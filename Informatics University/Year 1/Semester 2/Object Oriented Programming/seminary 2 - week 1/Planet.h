#pragma once

typedef struct
{ 
	char* name;
	char* type;
	double distance;
} Planet;

Planet* createPlanet(char* name, char* type, double distance);
void destroyPlanet(Planet* p);
char* getName(Planet* p);
char* getType(Planet* p);
double getDistance(Planet* p);
char* toString(Planet* p);