#ifndef OBJECT_H
#define OBJECT_H

#include <Eigen/Dense>
#include <string>
#include <vector>
#include "Material.h"

class Object {
 public:
  Eigen::MatrixXd vertices;
  std::vector<std::vector<int>> faces;
  Material material;
  void ApplyTransformation(float wx, float wy, float wz, float theta,
			   float scale, float tx, float ty, float tz);
  bool ReadFromFile(const std::string& fileName);
 private:
  bool ParseLine(const std::string& line, std::vector<Eigen::Vector4d>& vertices);
};

#endif
