#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <math.h>
#include <iterator>
#include <algorithm>

#include "DriverReader.h"

using namespace std;

bool DriverReader::ParseDriverLine(const string& line) {
  //cout << "\t" << line << endl;
  istringstream iss(line);
  vector<string> tokens {istream_iterator<string>{iss}, istream_iterator<string>{}};
  if (tokens.size() > 0) {
    try {
      string first = tokens[0];
      if (first == "eye") {
	if (tokens.size() < 4) {
	  return false;
	}
	camera.eye << stof(tokens[1]), stof(tokens[2]), stof(tokens[3]);
	eyeRead = true;
      } else if (first == "look") {
	if (tokens.size() < 4) {
	  return false;
	}
	camera.look << stof(tokens[1]), stof(tokens[2]), stof(tokens[3]);
	lookRead = true;
      } else if (first == "up") {
	if (tokens.size() < 4) {
	  return false;
	}
	camera.up << stof(tokens[1]), stof(tokens[2]), stof(tokens[3]);
	upRead = true;
      } else if (first == "d") {
	if (tokens.size() < 2) {
	  return false;
	}
	camera.d = stof(tokens[1]);
	dRead = true;
      } else if (first == "bounds") {
	if (tokens.size() < 5) {
	  return false;
	}
	camera.leftBound = stof(tokens[1]);
	camera.bottomBound = stof(tokens[2]);
	camera.rightBound = stof(tokens[3]);
	camera.topBound = stof(tokens[4]);
	boundsRead = true;
      } else if (first == "res") {
	if (tokens.size() < 3) {
	  return false;
	}
	camera.resY = stoi(tokens[1]);
	camera.resX = stoi(tokens[2]);
	resRead = true;
      } else if (first == "sphere") {
	if (tokens.size() < 5) {
	  return false;
	}
	Sphere s(stof(tokens[1]),stof(tokens[2]),stof(tokens[3]),stof(tokens[4]));
	spheres.push_back(s);
      } else if (first == "model") {
	// read 9 values to define object
	Object o;
	bool success = o.ReadFromFile(tokens[9]);
	if (success) {
	  o.ApplyTransformation(stof(tokens[1]),stof(tokens[2]),stof(tokens[3]),
				stof(tokens[4]),stof(tokens[5]),stof(tokens[6]),
				stof(tokens[7]),stof(tokens[8]));
	  objects.push_back(o);
	}
      } else {
	// ignore line, could throw error if in wrong format
      }
    } catch (invalid_argument e) {
      return false;
    }
  }
  return true;
}

bool DriverReader::ReadDriver(const string& fileName) {
  cout << "Reading " << fileName << endl;
  ifstream driverFile(fileName);
  if (driverFile) {
    cout << fileName << " successfully opened" << endl;
    string driverLine;
    while (getline(driverFile, driverLine)) {
      ParseDriverLine(driverLine);
    }
  } else {
    status = "Could not open driver file for reading";
    return false;
  }

  if (eyeRead && lookRead && upRead && dRead && boundsRead && resRead) {
    cout << "Camera successfully created" << endl;
  } else {
    status = "Error encountered while specifying camera";
    return false;
  }
  
  // read in camera information
  //  will only read the last of each value (ie if eye shows up multiple times, camera only has last value
  //  requires multiple lines
  //  each field needs to be filled
    
  // read in sphere information
  // only needs one line

  // read in object information
  // only needs one line

  // ignore comments (lines beginning with #)
  
  status = "Driver file read";
  return true;
}
