#include "DriverReader.h"
#include "Ray.h"
#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;
using namespace Eigen;

int main(int argv, char** argc) {
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
  
  cout << "Generating image" << endl;
  Eigen::Vector3i** image = driverReader.camera.GenerateImage(driverReader.scene);
  cout << "Writing to file" << endl;
  stringstream ppmContents;
  ppmContents << "P3\n" << driverReader.camera.resY << " " <<
                 driverReader.camera.resX << " 255\n";
  for (int x = driverReader.camera.resX - 1; x >= 0; --x) {
    for (int y = driverReader.camera.resY - 1; y >= 0; --y)
      ppmContents << image[x][y].transpose() << " ";
    ppmContents << endl;
  }
  
  ofstream ppmFile;
  ppmFile.open(argc[2]);
  if (ppmFile) ppmFile << ppmContents.rdbuf();
  
  for (int i = 0; i < driverReader.camera.resX; ++i)
    delete[] image[i];
  delete[] image;
  
  // todo:
  // additional robustness is definitely needed
  // what happens if look vector is directly downwards
  // delete depth map after we are done with it
  // maybe DriverReader should be a part of scene
  // currently each object can have only one material associated with it, change!
  //  each face gets its own material, material files can have multiple materials
}
