#ifndef CAMERA_H
#define CAMERA_H

#include "Object.h"
#include "Sphere.h"
#include "Eigen/Dense"
#include <vector>

class Camera {
 public:

  float** GenerateDepthMap(std::vector<Object>& objects, std::vector<Sphere>& spheres);
  
  Eigen::Vector3d eye;
  Eigen::Vector3d look;
  Eigen::Vector3d up;
  float d;
  float leftBound;
  float bottomBound;
  float rightBound;
  float topBound;
  int resX;
  int resY;

 private:
  Eigen::Vector3d PixelToPoint(int x, int y);

  float width;
  float height;
  Eigen::Vector3d forward;
  Eigen::Vector3d horizontal;
  Eigen::Vector3d vertical;
  
};


#endif
