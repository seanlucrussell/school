#include <Pose.h>


int Pose::readPose(string line) {
  istringstream lineStream (line);
  double x=0,y=0,z=0;
  for (int i=0;i<25;i++){
    lineStream >> x;
    lineStream >> y;
    lineStream >> z;
    Point3D p (x,y,z);
    points.push_back(p);
  }
  if (lineStream.fail()) {
    cerr << "Error: value other than double encountered" << endl;
    return -1;
  }
  string s;
  lineStream >> s;
  if (!lineStream.fail()) {
    cerr << "Error: too many entries on line" << endl;
    return -1;
  }

  return 0;
}

int Pose::writePose(PoseDisplay &pDisplay) {
  pDisplay.Pose(points);
  return 0;
}
