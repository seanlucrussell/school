#ifndef RAY_H
#define RAY_H

#include <vector>
#include <Eigen/Dense>
#include "Object.h"
#include "Sphere.h"

class Ray {

 public:
  Eigen::Vector3d start;
  Eigen::Vector3d direction;
  float depth = -1;
  bool intersect = false;
  
  bool ShootIntoScene(std::vector<Object>& objects, std::vector<Sphere>& spheres);

};

#endif
