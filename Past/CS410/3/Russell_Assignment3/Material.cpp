#include "Material.h"

#include <fstream>
#include <sstream>
#include <algorithm>
#include <iterator>
#include <vector>

#include <iostream>

using namespace std;

void Material::ParseLine(string& line) {
  istringstream iss(line);
  vector<string> tokens {istream_iterator<string>{iss}, istream_iterator<string>{}};
  if (tokens.size() > 0) {
    if (tokens[0] == "newmtl") {
      name = tokens[1]; // this causing a segfault?
    } else if (tokens[0] == "Ka") {
      ambient << stof(tokens[1]), stof(tokens[2]), stof(tokens[3]);
    } else if (tokens[0] == "Kd") {
      diffuse << stof(tokens[1]), stof(tokens[2]), stof(tokens[3]);
    } else if (tokens[0] == "Ks") {
      specular << stof(tokens[1]), stof(tokens[2]), stof(tokens[3]);
    }
  }
}

void Material::ReadFromFile(string& fileName) {
  ifstream materialFile(fileName);
  string line;
  if (materialFile) {
    while (getline(materialFile, line)) {
      ParseLine(line);
    }
  }
}
