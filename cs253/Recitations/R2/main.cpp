/*! \file main.cpp: main function for complex number calculator */
#include <Complex.h>
#include <iostream>
using std::cout;
using std::endl;
#include <fstream>
using std::ifstream;

/// Print the correct usage in case of user syntax error.
int Usage(char* arg0)
{
  cout << "Usage: " << arg0 << " filename" << endl;
  cout << "where file contains complex op complex" << endl;
  cout << "complex is of the form a+bi or a-bi" << endl;
  cout << "and op is one of +,-,*,/" << endl;
  return -1;
}

int main(int argc, char* argv[])
{
  // check for the correct number of 
  if (argc != 2) return Usage(argv[0]);
  
  ifstream istr(argv[1]);
  if (istr.fail()) return Usage(argv[0]);

  Complex c1;
  if (!c1.Read(istr)) return Usage(argv[0]);

  char op;
  istr >> op;
  cout << "op = " << op << endl;
  if ((op != '+') && (op != '-') &&
      (op != '*') && (op != '/')) {
    return Usage(argv[0]);
  }
  
  Complex c2;
  if (!c2.Read(istr)) return Usage(argv[0]);

  if (op == '+') {
    c1.Add(c2);
  }
  else if (op == '-') {
    c1.Subtract(c2);
  }
  else if (op == '*') {
    c1.Multiply(c2);
  }
  else if (op == '/') {
    if (!c1.Divide(c2)) {
      cout << "calculation divides by zero" << endl;
      return 0;
    }
  }

  c1.Write(std::cout);
  return 0;
}




