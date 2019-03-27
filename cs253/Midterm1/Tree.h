#ifndef TREE_H_DEFINED
#define TREE_H_DEFINED

#include <Quagga.h>
#include <iostream>
using std::ostream;

class QuaggaTree {
public:
  QuaggaTree();
  QuaggaTree(const Quagga& q);
  ~QuaggaTree();

  bool push(Quagga* q);
  ostream& print(ostream& ostr) const;

protected:
  QuaggaTree* left;
  QuaggaTree* right;
  Quagga* value;
};

#endif // TREE_H_DEFINED
