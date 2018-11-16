#include "Ray.h"
#include "iostream"
#include "Eigen/Geometry"
#include <math.h>
using namespace std;
using namespace Eigen;

bool Ray::ShootIntoScene(Scene& scene) {
  intersect = false;
  direction.normalize();
  float dx = direction(0), dy = direction(1), dz = direction(2);
  float lx = start(0), ly = start(1), lz = start(2);
  for (int objectIndex = 0; objectIndex < scene.models.size(); ++objectIndex) {
    Model object = scene.models[objectIndex];
    for (int faceIndex = 0; faceIndex < object.faces.size(); ++faceIndex) {
      vector<int> face = object.faces[faceIndex].first;
      Vector3d a = object.vertices.col(face[0]-1).hnormalized();
      Vector3d b = object.vertices.col(face[1]-1).hnormalized();
      Vector3d c = object.vertices.col(face[2]-1).hnormalized();
      float ax = a(0), ay = a(1), az = a(2);
      float bx = b(0), by = b(1), bz = b(2);
      float cx = c(0), cy = c(1), cz = c(2);
      float acx = ax - cx, acy = ay - cy, acz = az - cz;
      float abx = ax - bx, aby = ay - by, abz = az - bz;
      float Mdet = (acz*dy-acy*dz)*abx-(acz*dx-acx*dz)*aby+(acy*dx-acx*dy)*abz;
      if (Mdet == 0) continue;
      float alx = ax - lx, aly = ay - ly, alz = az - lz;
      float M1 = (acz*dy-acy*dz)*alx-(acz*dx-acx*dz)*aly+(acy*dx-acx*dy)*alz;
      float beta = M1 / Mdet;
      if (beta < 0) continue;
      float M2 = (alz*dy-aly*dz)*abx-(alz*dx-alx*dz)*aby+(aly*dx-alx*dy)*abz;
      float gamma = M2 / Mdet;
      if (gamma < 0 || gamma + beta > 1) continue;
      float M3 = (aly*acz-acy*alz)*abx-(alx*acz-acx*alz)*aby+(alx*acy-acx*aly)*abz;
      float t = M3 / Mdet;
      if (t > 0.01 && t < depth) {
	hitSphere = false;
	intersect = true;
	depth = t;
	material = object.faces[faceIndex].second;
	normal = ((c - a).cross(b - a)).normalized();
      }
    }
  }
  for (int sphereIndex = 0; sphereIndex < scene.spheres.size(); ++sphereIndex) {
    Sphere s = scene.spheres[sphereIndex];
    Vector3d toCenter = s.center - start;
    float c = toCenter.norm();
    float v = direction.dot(toCenter);
    float d = sqrt( pow(s.radius,2) - pow(c,2) + pow(v,2));
    if (d > 0) {
      float t = v - d;
      if (t > 0.01 && t<depth) {
	hitSphere = true;
	intersect = true;
	depth = t;
	material = s.material;
	Vector3d intersection = start + t * direction;
	normal = intersection - s.center;
	normal.normalize();
      }
    }
  }
}
