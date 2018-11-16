#include "Ray.h"
#include "iostream"
#include "Eigen/Geometry"
#include <math.h>
using namespace std;
using namespace Eigen;

bool Ray::ShootIntoScene(vector<Object>& objects, vector<Sphere>& spheres) {
  intersect = false;
  direction.normalize();
  float dx = direction(0), dy = direction(1), dz = direction(2);
  float lx = start(0), ly = start(1), lz = start(2);
  for (int objectIndex = 0; objectIndex < objects.size(); ++objectIndex) {
    Object object = objects[objectIndex];
    for (int faceIndex = 0; faceIndex < object.faces.size(); ++faceIndex) {
      vector<int> face = object.faces[faceIndex];      
      Vector3d a = object.vertices.col(face[0]-1).hnormalized();
      Vector3d b = object.vertices.col(face[1]-1).hnormalized();
      Vector3d c = object.vertices.col(face[2]-1).hnormalized();
      cout << "a: " << a.transpose() << endl;
      cout << "b: " << b.transpose() << endl;
      cout << "c: " << c.transpose() << endl;
      float ax = a(0), ay = a(1), az = a(2);
      float bx = b(0), by = b(1), bz = b(2);
      float cx = c(0), cy = c(1), cz = c(2);
      float acx = ax - cx;
      float acy = ay - cy;
      float acz = az - cz;
      float abx = ax - bx;
      float aby = ay - by;
      float abz = az - bz;
      float Mdet = (acz*dy-acy*dz)*abx-(acz*dx-acx*dz)*aby+(acy*dx-acx*dy)*abz;
      if (Mdet == 0) {
	continue;
      }
      float alx = ax - lx;
      float aly = ay - ly;
      float alz = az - lz;
      float M1 = (acz*dy-acy*dz)*alx-(acz*dx-acx*dz)*aly+(acy*dx-acx*dy)*alz;
      float beta = M1 / Mdet;
      if (beta < 0) {
	continue;
      }
      float M2 = (alz*dy-aly*dz)*abx-(alz*dx-alx*dz)*aby+(aly*dx-alx*dy)*abz;
      float gamma = M2 / Mdet;
      if (gamma < 0 || gamma + beta > 1) {
	continue;
      }
      float M3 = (aly*acz-acy*alz)*abx-(alx*acz-acx*alz)*aby+(alx*acy-acx*aly)*abz;
      float t = M3 / Mdet;
      if (t < 0) {
	continue;
      }
      if (depth == -1 || t < depth) {
	intersect = true;
	depth = t;
      }
    }
  }
  for (int sphereIndex = 0; sphereIndex < spheres.size(); ++sphereIndex) {
    Sphere s = spheres[sphereIndex];
    Vector3d toCenter = s.center - start;
    float c = toCenter.norm();
    float v = direction.dot(toCenter);
    float d = sqrt( pow(s.radius,2) - pow(c,2) + pow(v,2));
    if (d > 0) {
      float t = v - d;
      if (t > 0 && (depth==-1 || t<depth)) {
	intersect = true;
	depth = t;
      }
    }
  }
}
