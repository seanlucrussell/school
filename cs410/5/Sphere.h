#ifndef SPHERE_H
#define SPHERE_H

#include "Material.h"
#include "Eigen/Dense"

class Sphere {
 public:
  Sphere(float x, float y, float z, float r) {
    center << x, y, z;
    radius = r;
  }
  
  Eigen::Vector3d center;
  float radius;
  Material material;
};

#endif
