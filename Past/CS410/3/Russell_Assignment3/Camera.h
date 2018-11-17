#ifndef CAMERA_H
#define CAMERA_H

#include "Ray.h"
#include "Scene.h"
#include "Eigen/Dense"
#include <vector>

class Camera {
 public:
  Eigen::Vector3i** GenerateImage(Scene& s);
  
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
  Ray** ShootRays(Scene& s);
  float width;
  float height;
  Eigen::Vector3d forward;
  Eigen::Vector3d horizontal;
  Eigen::Vector3d vertical;
  
};


#endif