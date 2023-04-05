#include "Edge.h"

Edge::Edge()
{
    this->edgeId = 0;
    this->destinationNode = 0;
    this->sourceNode = 0;
}

Edge::Edge(int _edgeId, int _sourceNode, int _destinationNode)
{
    this->edgeId = _edgeId;
    this->sourceNode = _sourceNode;
    this->destinationNode = _destinationNode;
}

int Edge::getSourceNode()
{
    return this->sourceNode;
}

int Edge::getDestinationNode()
{
    return this->destinationNode;
}

void Edge::setSourceNode(int newValue)
{
    this->sourceNode = newValue;
}

void Edge::setDestinationNode(int newValue)
{
    this->destinationNode = newValue;
}

