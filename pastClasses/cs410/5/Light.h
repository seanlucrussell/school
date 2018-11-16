#ifndef LIGHT_H
#define LIGHT_H

#include <Eigen/Dense>

class Light {
 public:
  Eigen::Vector4d position;
  Eigen::Vector3d color;
};

#endif
