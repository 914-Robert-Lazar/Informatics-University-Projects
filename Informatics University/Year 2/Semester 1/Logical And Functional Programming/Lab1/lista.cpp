#include "lista.h"
#include <iostream>

using namespace std;

PNode create_rec(){
  TElem x;
  cout << "x=";
  cin >> x;
  if (x == 0)
    return NULL;
  else
  {
    PNode p = new Node();
    p->e = x;
    p->next = create_rec();
    return p;
  }
}

List create(){
  List l;
  l.head = create_rec();
}

void print_rec(PNode p){
  if (p != NULL){
    cout << p->e << " ";
    print_rec(p->next);
  }
  else
  {
      cout << endl;
  }
}

void print(List l){
  print_rec(l.head);
}

void destroy_rec(PNode p){
  if (p!=NULL){
    destroy_rec(p->next);
    delete p;
  }
}

void destroy(List l) {
	//se elibereaza memoria alocata nodurilor listei
  destroy_rec(l.head);
}

