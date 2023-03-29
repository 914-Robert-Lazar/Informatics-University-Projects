#include "Bag.h"
#include "ShortTest.h"
#include "ExtendedTest.h"
#include "TestIntersection.h"
#include <iostream>

using namespace std;

int main() {

	testAll();
	cout << "Short tests over" << endl;
	testIntersection();
	cout << "Testing the intersection is over" << endl;
	testAllExtended();
	// testIterator();
	cout << "All test over" << endl;
	return 0;
}