#ifndef SCENE_H
#define SCENE_H

#include "Object.h"
#include "Sphere.h"
#include "Light.h"
#include <vector>

#include <Eigen/Dense>

class Scene {
 public:
  std::vector<Sphere> spheres;
  std::vector<Object> objects;
  Eigen::Vector3d ambientLightColor;
  std::vector<Light> lights;
};

#endif
