#include <iostream>
#include "Ui.h"


GraphUi::GraphUi(DirectedGraph _graph) : graph{ _graph }
{
	readCommand();
}

void GraphUi::readCommand()
{
	while (1)
	{
		printOptions();
		int opt;
		bool invalid_argument = true;
		while (invalid_argument)
		{
			invalid_argument = false;
			std::cin >> opt;
			if (opt < 0 || opt > 11)
			{
				std::cout << "Invalid option!\n";
				invalid_argument = true;
			}
		}
		if (opt == 0)
			break;
		try
		{
			switch (opt)
			{
			case 1: {
				addNode();
				break;
			}
			case 2: {
				addEdge();
				break;
			}
			case 3: {
				removeNode();
				break;
			}
			case 4: {
				removeEdge();
				break;
			}
			case 5: {
				saveToFile();
				break;
			}
			case 6: {
				printNodes();
				break;
			}
			case 7: {
				printOutboundEdges();
				break;
			}
			case 8: {
				printInboundEdges();
				break;
			}
			case 9: {
				degreeOfNode();
				break;
			}
			case 10: {
				getCostOfEdge();
				break;
			}
			case 11: {
				setCostOfEdge();
				break;
			}
			default:
				break;
			}
		}
		catch (std::exception& e)
		{
			std::cout << e.what() << "\n";
		}
	}
}

void GraphUi::printOptions()
{
	std::cout << "Options:\n";
	std::cout << "0: Exit\n";
	std::cout << "1: Add nodes\n";
	std::cout << "2: Add edge\n";
	std::cout << "3: Remove node\n";
	std::cout << "4: Remove edge\n";
	std::cout << "5: Save to file\n";
	std::cout << "6: Print nodes\n";
	std::cout << "7: Print outbound edges\n";
	std::cout << "8: Print inbound edges\n";
	std::cout << "9: Degree of a node\n";
	std::cout << "10: Get cost of and edge\n";
	std::cout << "11: Set cost of and edge\n";
}

void GraphUi::addNode()
{
	cout << "The number of nodes to be added: ";
	int numberOfNodes; cin >> numberOfNodes;
	this->graph.addNodes(numberOfNodes);
}

void GraphUi::addEdge()
{
	cout << "Start node, ending node, cost: ";
	Node start, end;
	Cost cost;
	cin >> start >> end >> cost;
	this->graph.addEdge(start, end, cost);
}

void GraphUi::removeNode()
{
	cout << "The node: ";
	Node node;
	cin >> node;
	this->graph.removeNode(node);
}

void GraphUi::removeEdge()
{
	cout << "Start node, ending node: ";
	Node start, end;
	cin >> start >> end;
	this->graph.removeEdge(start, end);
}

void GraphUi::saveToFile()
{
	writeGraphToFile("graph.out", this->graph);
}

void GraphUi::printNodes()
{
	vector<Node> nodes = this->graph.parseNodes();
	for (auto node : nodes)
		cout << node << " ";
	cout << "\n";
}

void GraphUi::printOutboundEdges()
{
	cout << "The node: ";
	Node node;
	cin >> node;
	vector<pair<Node, EdgeId>> out_nodes = this->graph.parseOutBoundNodes(node);
	for (auto out_node : out_nodes)
		cout << out_node.first << " ";
	cout << "\n";
}

void GraphUi::printInboundEdges()
{
	cout << "The node: ";
	Node node;
	cin >> node;
	vector<pair<Node, EdgeId>> in_nodes = this->graph.parseOutBoundNodes(node);
	for (auto in_node : in_nodes)
		cout << in_node.first << " ";
	cout << "\n";
}

void GraphUi::degreeOfNode()
{
	cout << "The node: ";
	Node node;
	cin >> node;
	cout << "In degree: " << this->graph.getInDegree(node) << "\n";
	cout << "Out degree: " << this->graph.getOutDegree(node) << "\n";
}

void GraphUi::getCostOfEdge()
{
	cout << "Start node, ending node: ";
	Node start, end;
	cin >> start >> end;
	cout << "The cost of the edge is: " << this->graph.getCost(start, end) << "\n";
}

void GraphUi::setCostOfEdge()
{
	cout << "Start node, ending node, cost: ";
	Node start, end;
	int cost;
	cin >> start >> end >> cost;
	this->graph.setCost(start, end, cost);
}
