#ifndef SPHERE_H
#define SPHERE_H

#include "Eigen/Dense"

class Sphere {
 public:
  Sphere(float x, float y, float z, float r) {
    center << x, y, z;
    radius = r;
  }
  
  Eigen::Vector3d center;
  float radius;
};

#endif
