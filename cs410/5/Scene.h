#ifndef SCENE_H
#define SCENE_H

#include "Model.h"
#include "Sphere.h"
#include "Light.h"
#include <vector>

#include <Eigen/Dense>

class Scene {
 public:
  std::vector<Sphere> spheres;
  std::vector<Model> models;
  Eigen::Vector3d ambientLightColor;
  std::vector<Light> lights;
  int recursionDepth;
};

#endif
