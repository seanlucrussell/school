#include <Pose.h>

int Pose::readPose(string line) {
  istringstream lineStream (line);
  double value = 0;
  for (int i=0;i<75;i++){
    lineStream >> value;
    points.push_back(value);
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
  vector<Point3D> point3Ds;
  for(int i=0;i<25;i++){
    point3Ds.push_back(Point3D(points[3*i],points[3*i+1],points[3*i+2]));
  }
  
  pDisplay.Pose(point3Ds);
  return 0;
}
