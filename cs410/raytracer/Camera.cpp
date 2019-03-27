#include "Camera.h"
#include "Ray.h"
#include <iostream>
using namespace std;
using namespace Eigen;

Vector3d Camera::PixelToPoint(int i, int j) {
  float px = bottomBound + float(i) / (resX - 1) * height;
  float py = leftBound +  float(j) / (resY - 1) * width;
  Vector3d point = eye + (d * forward) + (px * vertical) + (py * horizontal);
  return point;
}

float** Camera::GenerateDepthMap(vector<Object>& objects, vector<Sphere>& spheres) {
  float** depthMap = new float*[resX];
  up.normalize();
  forward = look - eye;
  forward.normalize();
  horizontal = up.cross(forward);
  horizontal.normalize();
  vertical = forward.cross(horizontal);
  vertical.normalize();
  width = rightBound - leftBound;
  height = topBound - bottomBound;
  cout << "Rendering progress:" << endl;
  for (int x = 0; x < resX; ++x) {
    depthMap[x] = new float[resY];
    for (int y = 0; y < resY; ++y) {
      Ray r;
      r.start = PixelToPoint(x,y);
      r.direction = r.start - eye;
      r.ShootIntoScene(objects, spheres);
      depthMap[x][y] = r.depth;
      cout << float(x + 1) / resX * 100 << " %\r";
      cout.flush();
    }
  }
  cout << endl;
  return depthMap;
}
