#ifndef DRIVER_READER_H
#define DRIVER_READER_H

#include "Object.h"
#include "Sphere.h"
#include "Camera.h"

#include <string>
#include <vector>

class DriverReader {
 public:

  bool ReadDriver(const std::string& fileName);
  
  Camera camera;
  std::vector<Sphere> spheres;
  std::vector<Object> objects;
  std::string status = "No driver file has been read yet";

 private:
  bool ParseDriverLine(const std::string& line);

  bool eyeRead = false;
  bool lookRead = false;
  bool upRead = false;
  bool dRead = false;
  bool boundsRead = false;
  bool resRead = false;
};

#endif
