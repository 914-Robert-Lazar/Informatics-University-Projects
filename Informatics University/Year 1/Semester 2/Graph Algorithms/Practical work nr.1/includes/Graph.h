#include <unordered_map>
#include "Edge.h"
#include <vector>
#include <fstream>

using namespace std;

#define EdgeId  int
#define Node    int
#define Cost    int
class DirectedGraph
{
private:
    int numberOfNodes;
    int numberOfEdges;
    unordered_map<EdgeId, pair<Edge, Cost>> edgeInformation;
    unordered_map<Node, vector<pair<Node, EdgeId>>> outBoundNodes;
    unordered_map<Node, vector<pair<Node, EdgeId>>> inBoundNodes;
public:
    DirectedGraph(int _numberOfNodes);
    int getNumberOfNodes();
    int getNumberOfEdges();
    vector<Node> parseNodes();
    EdgeId isEdgeBetweenTwoNodes(int sourceNode, int destinationNode);
    int getInDegree(int node);
    int getOutDegree(int node);
    Cost getCost(int sourceNode, int destinationNode);
    void setCost(int sourceNode, int destinationNode, Cost newCost);
    vector<pair<Node, EdgeId>> parseOutBoundNodes(int node);
    vector<pair<Node, EdgeId>> parseInBoundNodes(int node);
    pair<Node, Node> getEndPoints(int edgeId);

    bool addNodes(int numberOfVertexesToBeAdded);
    bool removeNode(int nodeId);
    bool addEdge(int sourceNode, int destinationNode, int cost);
    bool removeEdge(int sourceNode, int destinationNode);
};

DirectedGraph readGraphFromFile(string fileName);
void writeGraphToFile(string fileName, DirectedGraph &graph);
DirectedGraph createRandomGraph(int numberOfNodes, int numberOfEdges);