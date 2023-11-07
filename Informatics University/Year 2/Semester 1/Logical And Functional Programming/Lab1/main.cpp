#include "lista.h"
#include <iostream>

void substituteIthElement(PNode currentNode, TElem element, int index)
{
   if (currentNode == NULL)
   {
      return;
   }

   if (index == 1)
   {
      currentNode->e = element;
      return;
   }
   
   substituteIthElement(currentNode->next, element, index - 1); 
}

PNode differenceOfTwoSets(PNode head1, PNode head2, PNode head2ToBeginning)
{
   if (head1 == NULL)
   {
      return NULL;
   }

   if (head2 == NULL)
   {
      PNode node = differenceOfTwoSets(head1->next, head2ToBeginning, head2ToBeginning);
      PNode currentNode = new Node();
      currentNode->e = head1->e;
      currentNode->next = node;
      return currentNode;
   }

   if (head1->e == head2->e)
   {
      return differenceOfTwoSets(head1->next, head2ToBeginning, head2ToBeginning);
   }

   return differenceOfTwoSets(head1, head2->next, head2ToBeginning);
}

List customCreate(int n, TElem elements[])
{
   List list;
   
   for (int i = 0; i < n; ++i)
   {
      PNode node = new Node();
      node->e = elements[i];
      node->next = list.head;
      list.head = node;
   }
   return list;
}

int main()
{
   // List list = create();
   // TElem element; 
   // int index;
   // std::cout << "The element: ";
   // std::cin >> element;
   // std::cout << "The index: ";
   // std::cin >> index;
   // print(list);
   // substituteIthElement(list.head, element, index);
   // print(list);
   TElem elements1[6] = {1, 5, 7, 2};
   TElem elements2[6] = {4, 5, 6, 7};

   List set1 = customCreate(4, elements1);
   List set2 = customCreate(4, elements2);
   PNode difference = differenceOfTwoSets(set1.head, set2.head, set2.head);
   print_rec(difference);
}
