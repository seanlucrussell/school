#ifndef QUAGGA_DEFINED
#define QUAGGA_DEFINED

#include <string>
using std::string;

class Quagga {
public:
  Quagga() : name("Uninitialized") {}
  Quagga(const string& _name) : name(_name) {}
  Quagga(const Quagga& src) : name(src.name) {name.append("_copy");}
  const string& Name() const {return name;}

 protected:
  string name;
};


#endif // QUAGGA_DEFINED
