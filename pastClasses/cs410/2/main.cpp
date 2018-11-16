#include "DriverReader.h"
#include "Ray.h"
#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;
using namespace Eigen;

void PrintDriver(DriverReader& driverReader) {
  Camera camera = driverReader.camera;
  cout << "Successfully read driver file! Do stuff in here!" << endl;
  cout << "\tCamera Specification:" << endl;
  cout << "Eye: "  << camera.eye.transpose() << endl;
  cout << "Look: " << camera.look.transpose() << endl;
  cout << "Up: " << camera.up.transpose() << endl;
  cout << "D: " << camera.d << endl;
  cout << "Bounds: " <<
    camera.leftBound << " " << camera.bottomBound << " " <<
    camera.rightBound << " " << camera.topBound << endl;
  cout << "Res: " << camera.resX << " " << camera.resY << endl;
  cout << "\tSpheres:" << endl;
  for (int i = 0; i < driverReader.spheres.size(); ++i) {
    Sphere sphere = driverReader.spheres[i];
    cout << "Center: " << sphere.center.transpose() << endl;
    cout << "Radius: " << sphere.radius << endl;
  }
  cout << "\tObjects:" << endl;
  for (int i = 0; i < driverReader.objects.size(); ++i) {
    Object object = driverReader.objects[i];
    cout << "Vertices:" << endl;
    cout << object.vertices << endl;
    cout << "Faces:" << endl;
    for (int i=0; i<object.faces.size(); ++i) {
      cout << object.faces[i][0] << " " << object.faces[i][1] << " "
	   << object.faces[i][2] << endl;
    }
  }
}

void ShootRay(DriverReader& driverReader) {
  Ray r;
  r.start = driverReader.camera.eye;
  r.direction = driverReader.camera.look - driverReader.camera.eye;
  r.ShootIntoScene(driverReader.objects, driverReader.spheres);
  if (r.intersect) {
    cout << "Ray intersects at depth " << r.depth << endl;
  } else {
    cout << "The ray has no intersections" << endl;
  }
}

int main(int argv, char** argc) {

  // 2nd argument is location to save image
  // (can make more robust by adding indication when overwriting file)

  if (argv < 3) {
    cerr << "raytracer requires 2 arguments" << endl;
    return -1;
  }
  DriverReader driverReader;
  bool success = driverReader.ReadDriver(argc[1]);
  cout << driverReader.status << endl;

  if (!success) {
    cerr << "Error while reading driver file. Exiting." << endl;
    return -1;
  }


  cout << "Casting rays into scene" << endl;
  //PrintDriver(driverReader);
  //ShootRay(driverReader);
  float** depthMap = driverReader.camera.GenerateDepthMap(driverReader.objects,
							  driverReader.spheres);
  float tmin = -1;
  float tmax = -1;
  
  for (int x = 0; x <driverReader.camera.resX; ++x) {
    for (int y = 0; y < driverReader.camera.resY; ++y) {
      float depth = depthMap[x][y];
      if (tmin > depth && depth != -1) {
	tmin = depth;
      }
      if (tmin == -1) {
	tmin = depth;
      }
      if (tmax < depth) {
	tmax = depth;
      }
    }
  }

  cout << "Calculating colors and writing to file" << endl;
  stringstream ppmContents;
  ppmContents << "P3\n" << driverReader.camera.resY << " " <<
    driverReader.camera.resX << " 255\n";
  for (int x = driverReader.camera.resX - 1; x >= 0; --x) {
    for (int y = driverReader.camera.resY - 1; y >= 0; --y) {
      int r = 239, g = 239, b = 239;
      if (depthMap[x][y] != -1) { 
	float ratio = 2 * (depthMap[x][y] - tmin) / (tmax - tmin);
	//cout << ratio << " ";
	r = max(0,int(255*(1-ratio)));
	b = max(0,int(255*(ratio-1)));
	g = 255 - b - r;
      }
      ppmContents << r << " " << g << " " << b << " ";
    }
    ppmContents << endl;
  }
  
  ofstream ppmFile;
  ppmFile.open(argc[2]);
  if (ppmFile) {
    ppmFile << ppmContents.rdbuf();
  }
  // todo:
  // additional robustness is definitely needed
  // move stuff out of main
  // the names of resX and rexY are backwards i think
  // what happens if look vector is directly downwards
  // experiment with different values in the driver file, like resolution and bounds
  // make a better build system so things aren't so slow
  // if object file cannot be read, things crash
  // delete depth map after we are done with it
}
