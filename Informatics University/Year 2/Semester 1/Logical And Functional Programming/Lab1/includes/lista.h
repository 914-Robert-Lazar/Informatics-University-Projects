#ifndef LISTA_H
#define LISTA_H

typedef int TElem;

struct Node;

typedef Node *PNode;

struct Node{
    TElem e;
	PNode next;

	Node()
	{
		next = nullptr;
	}
};

struct List{
	PNode head;

	List()
	{
		head = nullptr;
	}
};


List create();

void print(List l);
void destroy(List l);
void print_rec(PNode p);

#endif
