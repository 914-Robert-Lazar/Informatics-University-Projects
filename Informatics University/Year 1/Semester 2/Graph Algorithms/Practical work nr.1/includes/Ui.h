#pragma once
#include "Graph.h"

using namespace std;

class GraphUi
{
private:
	DirectedGraph graph;
public:

	GraphUi(DirectedGraph);

	void readCommand();

	void printOptions();

	void addNode();

	void addEdge();

	void removeNode();

	void removeEdge();

	void saveToFile();

	void printNodes();

	void printOutboundEdges();

	void printInboundEdges();

	void degreeOfNode();

	void getCostOfEdge();

	void setCostOfEdge();
};

