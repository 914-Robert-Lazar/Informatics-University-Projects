#include <time.h>
#include <cstdlib>
#include "Ui.h"
#include <iostream>

using namespace std;

int main()
{
    srand(time(NULL));
    cout << "xd";
    DirectedGraph graph = createRandomGraph(10, 5);
    GraphUi console = GraphUi(graph);
    return 0;
}