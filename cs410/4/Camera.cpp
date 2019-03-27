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

Vector3d Camera::RaytraceRecursive(Ray& ray, int recursionDepth) {
  ray.ShootIntoScene(s);
  Vector3d color;
  color << 0,0,0;
  if (ray.intersect) {
    Vector3d ambient = s.ambientLightColor;
    Material material = ray.material;
    color = (ambient.array() * material.ambient.array()).matrix();
    Vector3d hit = ray.start + ray.depth * ray.direction;
    Vector3d toCamera = (ray.start - hit).normalized();
    if (recursionDepth > 0) {
      Ray reflectionRay;
      reflectionRay.start = hit;
      reflectionRay.direction = (2 * ray.normal.dot(toCamera) * ray.normal) - toCamera;
      color = color + (RaytraceRecursive(reflectionRay, recursionDepth-1).array() *
		       material.attenuation.array()).matrix();
    }
    for (int l = 0; l < s.lights.size(); ++l) {
      Light light = s.lights[l];
      Vector3d toLight = (light.position.hnormalized() - hit).normalized();
      float distanceToLight = (light.position.hnormalized() - hit).norm();
      Ray rayToLight;
      rayToLight.start = hit;
      rayToLight.direction = toLight;
      rayToLight.ShootIntoScene(s);
      float dot = ray.normal.dot(toLight);
      
      // SHADOWS ARE REVERSED BETWEEN MY BLENDER FILES AND THE PROVIDED ONES
      // for some reason it thinks that its intersecting when it shouldn't be

      
      if (dot > 0.0 && (rayToLight.depth > distanceToLight || ray.material.name == "None")) {
	color += (light.color.array() * material.diffuse.array()).matrix() * dot;

	Vector3d reflection = (2 * ray.normal.dot(toLight) * ray.normal) - toLight;
	// CHANGE TO VALUE PROVIDED BY MATERIAL
	int PHONG = 16;
	color += (light.color.array() * material.specular.array()).matrix() *
	  pow(toCamera.dot(reflection), PHONG);
      }
    }
  }
  return color;
}

Vector3i** Camera::GenerateImage() {
  Vector3i** image = new Vector3i*[resX];
  for (int x = 0; x < resX; ++x) image[x] = new Vector3i[resY];
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
    for (int y = 0; y < resY; ++y) {
      Ray ray;
      ray.start = PixelToPoint(x,y);
      ray.direction = ray.start - eye;
      Vector3d color = RaytraceRecursive(ray,s.recursionDepth);
      image[x][y] << max(0,min(int(color(0)*255),255)),
	max(0,min(int(color(1)*255),255)),
	max(0,min(int(color(2)*255),255));
    }
    cout << float(x + 1) / resX * 100 << " %\r";
    cout.flush();
  }
  cout << endl;
  return image;
}
