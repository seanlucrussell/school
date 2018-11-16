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
    attenuation << 1,1,1;
  }
}

MaterialLib Material::ReadMaterialLibFromFile(string& fileName) {
  ifstream materialFile(fileName);
  string line;
  MaterialLib lib;
  string currentMat;
  if (materialFile) {
    while (getline(materialFile, line)) {
      istringstream iss(line);
      vector<string> tokens {istream_iterator<string>{iss}, istream_iterator<string>{}};
      if (tokens.size() > 0) {
	if (tokens[0] == "newmtl") {
	  Material newMaterial;
	  currentMat = tokens[1];
	  newMaterial.name = currentMat;
	  newMaterial.attenuation << 1,1,1;
	  lib[currentMat] = newMaterial;
	} else if (tokens[0] == "Ka") {
	  lib[currentMat].ambient << stof(tokens[1]), stof(tokens[2]), stof(tokens[3]);
	} else if (tokens[0] == "Kd") {
	  lib[currentMat].diffuse << stof(tokens[1]), stof(tokens[2]), stof(tokens[3]);
	} else if (tokens[0] == "Ks") {
	  lib[currentMat].specular << stof(tokens[1]), stof(tokens[2]), stof(tokens[3]);
	}
      }
    }
  }
  return lib;
}
