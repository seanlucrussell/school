#include "Camera.h"
#include "Ray.h"
#include <iostream>
#include <algorithm>
#include <cmath>
#include <math.h>
using namespace std;
using namespace Eigen;


Vector3d pairwise(const Vector3d& a, const Vector3d& b) {
  Vector3d c;
  c << a(0) * b(0), a(1) * b(1), a(2) * b(2);
  return c;
}


Vector3d Camera::PixelToPoint(int i, int j) {
  float px = bottomBound + float(i) / (resX - 1) * height;
  float py = leftBound +  float(j) / (resY - 1) * width;
  Vector3d point = eye + (d * forward) + (px * vertical) + (py * horizontal);
  return point;
}

Ray refract(Ray& incoming, double eta1, double eta2) {
  Vector3d from = -incoming.direction;
  double mu = eta1 / eta2;
  double alpha = -mu;
  double dot = from.dot(incoming.normal);
  double beta = mu * dot - sqrt(pow(mu,2) * (pow(dot,2) - 1) + 1);
  Ray refracted;
  refracted.start = incoming.start + incoming.depth * incoming.direction;
  refracted.direction = alpha * from + beta * incoming.normal;
  return refracted;
}

Vector3d Camera::RaytraceRecursive(Ray& ray, int recursionDepth) {
  ray.ShootIntoScene(s);
  Vector3d color;
  color << 0,0,0;
  if (ray.intersect) {
    Vector3d ambient = s.ambientLightColor;
    Material material = ray.material;
    color = pairwise(ambient, material.ambient);
    Vector3d hit = ray.start + ray.depth * ray.direction;
    Vector3d toCamera = -ray.direction;
    if (ray.normal.dot(toCamera) < 0) {
      ray.normal = -1 * ray.normal;
    }
    if (recursionDepth > 0) {
      Ray reflectionRay;
      reflectionRay.start = hit;
      Vector3d reflectionVector = (2 * ray.normal.dot(toCamera) * ray.normal) - toCamera;
      reflectionVector.normalize();
      reflectionRay.direction = reflectionVector;
      color += pairwise(RaytraceRecursive(reflectionRay, recursionDepth-1),
			material.attenuation);
      // NEED TO INCLUDE REFLECTIVITY
      if (ray.hitSphere) { // AND MATERIAL IS TRANSPARENT

	
	double indexOfRefraction = .8; // change to real index
	Ray refracted = refract(ray, 1, indexOfRefraction);
	Vector3d opacity;
	opacity << .8,.8,.8;
	color += pairwise(material.attenuation,
			  pairwise(RaytraceRecursive(refracted, recursionDepth-1), opacity));
	//color += pairwise(material.attenuation,
	//		  pairwise(RaytraceRecursive(refracted, recursionDepth-1), opacity));
	/*
	double mu = 1.0 / 1.5;
	double alpha = -mu;
	double dot = toCamera.dot(reflectionVector);
	double beta = mu * dot - sqrt(1 - pow(mu,2) + pow(mu,2) * pow(dot,2));
	Ray refractionRay;
	refractionRay.start = hit;
	refractionRay.direction = alpha * toCamera + beta * reflectionVector;

	refractionRay.ShootIntoScene(s);

	Ray exitRay;
	exitRay.start = refractionRay.start + refractionRay.depth * refractionRay.direction;
	double mu2 = 1.5 / 1.0;
	double alpha2 = -mu2;
	Vector3d toCamera2 = -refractionRay.direction;
	Vector3d reflectionVector2 = (2 * refractionRay.normal.dot(toCamera2) * refractionRay.normal)
	  - toCamera2;
	double dot2 = toCamera2.dot(reflectionVector2);
	double  beta2 = mu2 * dot2 - sqrt(1 - pow(mu2,2) + pow(mu2,2) * pow(dot2,2));
	exitRay.direction = alpha2 * toCamera2 + beta2 * reflectionVector2;
	Vector3d opacity;
	opacity << .5,.5,.5;
	//color += pairwise(RaytraceRecursive(refractionRay, recursionDepth-1), opacity);
	color += pairwise(RaytraceRecursive(exitRay, recursionDepth-1), opacity);
	*/

      }
      
      /*
      if (true) { // MATERIAL IS TRANSPARENT
	Vector3d opacity;
	opacity << .6,.6,.6;
	Ray refractionRay;
	refractionRay.start = hit;
	// direction =
	// mu = index of refraction of the current material divided by material the ray is entering
	double dot = toCamera.dot(reflectionVector);

	double mu = 1.0 / 1.5;
	double alpha = -mu;
	double beta = mu * dot;
	beta -= sqrt(1 - mu * mu + mu * mu * dot * dot);
	refractionRay.direction = alpha * toCamera + beta * reflectionVector;
	// THIS IS WRONG, USE 1-opacity INSTEAD
	color += pairwise(RaytraceRecursive(refractionRay, recursionDepth-1), opacity);
      }
      */
      // if recursionDepth > 0 and sphere is transparent
      //  do all of the refraction stuff
      //  how do we deal with spheres as different than objects?

      // in general, keep track of the current index of refraction and then whenever an intersection
      // with a transparent object occurs, calculate the angle of the new ray from that

      // start index of refraction at 1
      // when a ray hits a transparent object:
      //  find index of refraction of that object
      //  use this index to calculate trajectory of ray coming out of the other side of intersection
      
      
      
      // general version of refraction:
      // assuming objects are closed and objects do not intersect
      //  have stack of objects (each object has an index of refraction)
      //  when you hit an object:
      //   if that object isn't already in the stack:
      //    add it to the stack
      //    
      //   otherwise
      //    remove it from the stack
      //   use snells law

      
      
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
      if (dot > 0.0 && (rayToLight.depth > distanceToLight)) {
	color += pairwise(light.color, material.diffuse) * dot;
	Vector3d reflection = (2 * ray.normal.dot(toLight) * ray.normal) - toLight;
	int PHONG = 16;
	color += pairwise(light.color, material.specular) * pow(toCamera.dot(reflection), PHONG);
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
