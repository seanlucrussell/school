#include "Object.h"
#include <fstream>
#include <iostream>
#include <sstream>
#include <algorithm>
#include <iterator>
using namespace std;
using namespace Eigen;
void Object::ApplyTransformation(float wx, float wy, float wz, float theta,
				 float scale, float tx, float ty, float tz) {
  MatrixXd rotation = MatrixXd::Identity(4,4);
  float t = theta * 3.1415926 / 180.0f;
  Vector3d w;
  w << wx, wy , wz;
  w.normalize();
  Vector3d m = w;
  int smallest = 0;
  if (w(1) < w(0)) smallest = 1;
  if (w(2) < w(smallest)) smallest = 2;
  m(smallest) = 1;
  m.normalize();
  Vector3d u = w.cross(m);
  u.normalize();
  Vector3d v = w.cross(u);
  //cout << w << endl << m << endl << u << endl << v << endl;
  rotation(0,0) = u(0);
  rotation(0,1) = u(1);
  rotation(0,2) = u(2);
  rotation(1,0) = v(0);
  rotation(1,1) = v(1);
  rotation(1,2) = v(2);
  rotation(2,0) = w(0);
  rotation(2,1) = w(1);
  rotation(2,2) = w(2);
  MatrixXd zRotation = MatrixXd::Identity(4,4);
  zRotation(0,0) = cos(t);
  zRotation(0,1) = -sin(t);
  zRotation(1,0) = sin(t);
  zRotation(1,1) = cos(t);
  rotation = rotation.transpose() * zRotation * rotation;
  //cout << endl << rotation << endl << endl;

  MatrixXd scaling = MatrixXd::Identity(4,4);
  scaling(0,0) = scale;
  scaling(1,1) = scale;
  scaling(2,2) = scale;
  //cout << endl << scaling << endl << endl;

  MatrixXd translation = MatrixXd::Identity(4,4);
  translation(0,3) = tx;
  translation(1,3) = ty;
  translation(2,3) = tz;
  //cout << endl << translation << endl << endl;

  MatrixXd transformMatrix = translation * scaling * rotation;
  //cout << endl << transformMatrix << endl << endl;

  vertices = transformMatrix * vertices;
}

bool Object::ParseLine(const string& line, vector<Vector4d>& vertices) {
  istringstream iss (line);
  vector<string> tokens {istream_iterator<string>{iss}, istream_iterator<string>{}};
  if (tokens.size() > 0) {
    if (tokens[0] == "v") {
      Vector4d vertex;
      vertex << stof(tokens[1]), stof(tokens[2]), stof(tokens[3]), 1;
      vertices.push_back(vertex);
    }
    if (tokens[0] == "f") {
      vector<int> face;
      face.push_back(stoi(tokens[1]));
      face.push_back(stoi(tokens[2]));
      face.push_back(stoi(tokens[3]));
      faces.push_back(face);
    }
    if (tokens[0] == "mtllib") {
      material.ReadFromFile(tokens[1]);
    }
  }
}

bool Object::ReadFromFile(const string& fileName) {
  ifstream objectFile(fileName);
  string line;
  if (objectFile) {
    vector<Vector4d> vertices;
    while (getline(objectFile, line)) {
      ParseLine(line, vertices);
    }
    MatrixXd vertexMatrix(4, vertices.size());
    for (int i=0; i<vertices.size(); ++i) {
      vertexMatrix.col(i) = vertices[i];
    }
    this->vertices = vertexMatrix;
  } else {
    cerr << "Could not read object file at " << fileName << endl;
    return false;
  }
  return true;
}
