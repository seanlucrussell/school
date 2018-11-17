#ifndef RAY_H
#define RAY_H

#include "Material.h"
#include "Scene.h"
#include <vector>
#include <Eigen/Dense>
#include <limits>

class Ray {

 public:
  Eigen::Vector3d start;
  Eigen::Vector3d direction;
  
  bool intersect = false;
  float depth = std::numeric_limits<float>::infinity();
  Eigen::Vector3d normal;
  Material material;
  
  bool ShootIntoScene(Scene& s);

};

#endif
