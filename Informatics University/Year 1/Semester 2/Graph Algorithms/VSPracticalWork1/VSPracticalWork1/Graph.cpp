#include "Graph.h"
#include <vector>
#include <iostream>

using namespace std;

DirectedGraph::DirectedGraph(int _numberOfNodes)
{
    this->numberOfNodes = _numberOfNodes;
    this->numberOfEdges = 0;
}

int DirectedGraph::getNumberOfNodes()
{
    return this->numberOfNodes;
}

int DirectedGraph::getNumberOfEdges()
{
    return this->numberOfEdges;
}

vector<Node> DirectedGraph::parseNodes()
{
    vector<int> nodes;
    for (int i = 0; i < this->numberOfNodes; ++i)
    {
        nodes.push_back(i);
    }

    return nodes;
}

EdgeId DirectedGraph::isEdgeBetweenTwoNodes(int sourceNode, int destinationNode)
{
    for (auto vertex : this->outBoundNodes[sourceNode])
    {
        if (vertex.first == destinationNode)
        {
            return vertex.second;
        }
    }
    return -1;
}

int DirectedGraph::getInDegree(int node)
{
    return this->inBoundNodes[node].size();
}

int DirectedGraph::getOutDegree(int node)
{
    return this->outBoundNodes[node].size();
}

Cost DirectedGraph::getCost(int sourceNode, int destinationNode)
{
    return this->edgeInformation[this->isEdgeBetweenTwoNodes(sourceNode, destinationNode)].second;
}

void DirectedGraph::setCost(int sourceNode, int destinationNode, Cost newCost)
{
    this->edgeInformation[this->isEdgeBetweenTwoNodes(sourceNode, destinationNode)].second = newCost;
}

vector<pair<Node, EdgeId>> DirectedGraph::parseOutBoundNodes(int node)
{
    return this->outBoundNodes[node];
}

vector<pair<Node, EdgeId>> DirectedGraph::parseInBoundNodes(int node)
{
    return this->inBoundNodes[node];
}

pair<Node, Node> DirectedGraph::getEndPoints(int edgeId)
{
    return make_pair(this->edgeInformation[edgeId].first.getSourceNode(), this->edgeInformation[edgeId].first.getDestinationNode());
}

bool DirectedGraph::addNodes(int numberOfVertexesToBeAdded)
{
    this->numberOfNodes += numberOfVertexesToBeAdded;
    return true;
}

bool DirectedGraph::removeNode(int nodeId)
{
    for (auto outNodeAndEdge : this->outBoundNodes[nodeId])
    {
        this->edgeInformation.erase(outNodeAndEdge.second);
    }
    this->outBoundNodes.erase(nodeId);

    for (auto inNodeAndEdge : this->inBoundNodes[nodeId])
    {
        this->edgeInformation.erase(inNodeAndEdge.second);
    }
    this->inBoundNodes.erase(nodeId);
    return true;
}

bool DirectedGraph::addEdge(int sourceNode, int destinationNode, int cost)
{
    if (this->isEdgeBetweenTwoNodes(sourceNode, destinationNode))
    {
        return false;
    }
    this->outBoundNodes[sourceNode].push_back(make_pair(destinationNode, this->numberOfEdges));
    this->inBoundNodes[destinationNode].push_back(make_pair(sourceNode, this->numberOfEdges));

    Edge currentEdge = Edge(this->numberOfEdges, sourceNode, destinationNode);
    this->edgeInformation[this->numberOfEdges++] = make_pair(currentEdge, cost);
    return true;
}

bool DirectedGraph::removeEdge(int sourceNode, int destinationNode)
{
    if (!this->isEdgeBetweenTwoNodes(sourceNode, destinationNode))
    {
        cout << "The edge doesn't exist.";
        return false;
    }

    for (size_t i = 0; i < this->outBoundNodes[sourceNode].size(); ++i)
    {
        if (this->outBoundNodes[sourceNode][i].first == destinationNode)
        {
            this->edgeInformation.erase(this->outBoundNodes[sourceNode][i].second);
            this->outBoundNodes[sourceNode].erase(this->outBoundNodes[sourceNode].begin() + i);
            break;
        }
    }

    for (size_t i = 0; i < this->inBoundNodes[destinationNode].size(); ++i)
    {
        if (this->inBoundNodes[destinationNode][i].first == sourceNode)
        {
            this->inBoundNodes[destinationNode].erase(this->inBoundNodes[destinationNode].begin() + i);
            break;
        }
    }

    return true;
}

DirectedGraph readGraphFromFile(string fileName)
{
    ifstream inputFile(fileName);
    int n, m; cin >> n >> m;
    DirectedGraph graph = DirectedGraph(n);
    for (int i = 0; i < m; ++i)
    {
        int u, v, w; cin >> u >> v >> w;
        graph.addEdge(u, v, w);
    }
    inputFile.close();
    return graph;
}

void writeGraphToFile(string fileName, DirectedGraph& graph)
{
    ofstream outputFile(fileName);
    cout << "Number of nodes: " << graph.getNumberOfNodes() << ", number of edges: " << graph.getNumberOfEdges() << endl;
    vector<Node> nodes = graph.parseNodes();
    for (Node& node : nodes)
    {
        vector<pair<Node, EdgeId>> outBoundNodes = graph.parseOutBoundNodes(node);
        for (auto& adjacent : outBoundNodes)
        {
            cout << "Edge ID: " << adjacent.second << ", source node: " << node << ", destination node: " << adjacent.first << ", cost: "
                << graph.getCost(node, adjacent.first) << endl;
        }
    }
    outputFile.close();
}

DirectedGraph createRandomGraph(int numberOfNodes, int numberOfEdges)
{
    DirectedGraph graph = DirectedGraph(numberOfNodes);
    while (numberOfEdges > 0)
    {
        int sourceNode = rand() % numberOfNodes;
        int destinationNode = rand() % numberOfNodes;
        int cost = rand() + 1;
        if (graph.addEdge(sourceNode, destinationNode, cost))
        {
            --numberOfEdges;
        }
    }

    return graph;
}