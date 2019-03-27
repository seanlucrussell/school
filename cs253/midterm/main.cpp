#include <iostream>
#include "Pixel.h"
#include "Image.tpp"

using std::cout;
using std::endl;

int main() {
  Image<RGB> i(9,10);
  Image<RGB> j = Image<RGB>(i);
  RGB r(52);
  //j(5,6) = r;
  cout << j.size() << endl;
}
