
#include <assert.h>
#include "Bag.h"
#include "TestIntersection.h"
#include "BagIterator.h"

void testIntersection()
{
    Bag b;
    b.add(5);
	b.add(1);
	b.add(10);
	b.add(7);
	b.add(1);
	b.add(11);
	b.add(-3);
    b.add(-3);
    b.add(10);
    b.add(10);

    Bag b2;
    b2.add(1);
    b2.add(1);
    b2.add(1);
    b2.add(-3);
    b2.add(15);
    b2.add(15);
    b2.add(9);
    b.intersection(b2);
    assert(b.size() == 3);
    assert(b.nrOccurrences(1) == 2);
    assert(b.nrOccurrences(-3) == 1);
    assert(b.nrOccurrences(10) == 0);
    assert(b.nrOccurrences(15) == 0);
    assert(b.nrOccurrences(7) == 0);
    assert(b.nrOccurrences(19) == 0);
}