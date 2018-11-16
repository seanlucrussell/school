#ifndef OBJECT_H
#define OBJECT_H

#include <Eigen/Dense>
#include <string>
#include <vector>
#include <map>
#include <utility>
#include "Material.h"

class Object {
 public:
  Eigen::MatrixXd vertices;

  typedef std::pair<std::vector<int>,Material> Face;
  
  //  std::vector<std::vector<int>> faces;

  std::vector<Face> faces;
  
  MaterialLib materialLib;
    Material material;
  void ApplyTransformation(float wx, float wy, float wz, float theta,
			   float scale, float tx, float ty, float tz);
  bool ReadFromFile(const std::string& fileName);
 private:
  bool ParseLine(const std::string& line, std::vector<Eigen::Vector4d>& vertices);
};

#endif
