#ifndef INTERSECTION_H
#define INTERSECTION_H

#include "Material.h"
#include <Eigen/Dense>
#include <limits>



class Intersection {
 public:
  float depth = std::numeric_limits<float>::infinity();
  Eigen::Vector3d normal;
  Material material;
};

#endif
