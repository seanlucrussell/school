#ifndef POSE_H
#define POSE_H

#include <iostream>
#include <vector>
#include <string>
#include <sstream>

#include <Point3D.h>
#include <PoseDisplay.h>

using std::vector;
using std::string;
using std::istringstream;

using std::cerr;

using std::cout;
using std::endl;

class Pose {
public:

  inline Point3D& getPoint3D(const int position) {return points.at(position);}
  inline const Point3D& getPoint3D(const int position) const {return points.at(position);}
  
  int readPose(string line);				// reads a pose from a string
  int writePose(PoseDisplay &pDisplay);			// write values in points to file and display
  double poseCompare(const Pose& p);			// compares difference between two poses
  

protected:
  vector<Point3D> points;                               // vector to hold all of the poses
};

#endif // POSE_H included
