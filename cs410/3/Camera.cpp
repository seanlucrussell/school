#include "Camera.h"
#include "Ray.h"
#include <iostream>
#include <algorithm>
#include <cmath>
using namespace std;
using namespace Eigen;

Vector3d Camera::PixelToPoint(int i, int j) {
  float px = bottomBound + float(i) / (resX - 1) * height;
  float py = leftBound +  float(j) / (resY - 1) * width;
  Vector3d point = eye + (d * forward) + (px * vertical) + (py * horizontal);
  return point;
}

Ray** Camera::ShootRays(Scene& s) {
  Ray** rays = new Ray*[resX];
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
    rays[x] = new Ray[resY];
    for (int y = 0; y < resY; ++y) {
      Ray r;
      r.start = PixelToPoint(x,y);
      r.direction = r.start - eye;
      r.ShootIntoScene(s);
      rays[x][y] = r;
    }
    cout << float(x + 1) / resX * 100 << " %\r";
    cout.flush();
  }
  cout << endl;
  return rays;
}

Vector3i** Camera::GenerateImage(Scene& s) {
  Vector3i** image = new Vector3i*[resX];
  for (int x = 0; x < resX; ++x) image[x] = new Vector3i[resY];
  
  Ray** rays = ShootRays(s);

  for (int x = 0; x < resX; ++x) {
    for (int y = 0; y < resY; ++y) {
      Ray ray = rays[x][y];
      if (!ray.intersect) {
	image[x][y] << 0,0,0;
	continue;
      }
      Vector3d ambient = s.ambientLightColor;
      Material material = rays[x][y].material;
      Vector3d color;

      color = (ambient.array() * material.ambient.array()).matrix();

      for (int l = 0; l < s.lights.size(); ++l) {
	Light light = s.lights[l];
	Vector3d hit = ray.start + ray.depth * ray.direction;
	// NEXT LINE SHOULD BE DIFFERENT IF light.position(3) == 1
	Vector3d toLight = (light.position.hnormalized() - hit).normalized();
	float dot = ray.normal.dot(toLight);
	if (dot > 0.0) {
	  color += (light.color.array() * material.diffuse.array()).matrix() * dot;
	  Vector3d toCamera = (ray.start - hit).normalized();
	  Vector3d reflection = (2 * ray.normal.dot(toLight) * ray.normal) - toLight;

	  // CHANGE TO VALUE PROVIDED BY MATERIAL
	  int PHONG = 16;
	  color += (light.color.array() * material.specular.array()).matrix() *
	    pow(toCamera.dot(reflection), PHONG);
	}
      }
      image[x][y] << max(0,min(int(color(0)*255),255)),
	max(0,min(int(color(1)*255),255)),
	max(0,min(int(color(2)*255),255));
    }
  }

  for (int i = 0; i < resX; ++i)
    delete[] rays[i];
  delete[] rays;
  return image;
}
