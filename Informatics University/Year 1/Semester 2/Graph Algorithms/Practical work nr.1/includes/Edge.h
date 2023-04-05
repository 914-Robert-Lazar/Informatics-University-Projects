class Edge
{
private:
    int edgeId;
    int sourceNode;
    int destinationNode;
public:
    Edge();
    Edge(int _edgeId, int _sourceNode, int _destinationNode);
    int getSourceNode();
    int getDestinationNode();
    void setSourceNode(int newValue);
    void setDestinationNode(int newValue);
};