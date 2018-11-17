#include <PoseSequence.h>

#include <iostream>

//vector<Pose> poses; 

int PoseSequence::readPoseSequence(fstream &file) {
  string line;
  getline(file,line);
  int fail = 0;

  int lineCount = 0;
  double xtransform = 0;
  double ytransform = 0;
  double ztransform = 0;

  double maxVal = 0;

  while (!line.empty()) {
    Pose p;
    fail = p.readPose(line);
    if (fail == -1)
      return -1;
    poses.push_back(p);
    getline(file,line);

    lineCount++;

    Point3D current = p.getPoint3D(0);    
    xtransform += current.X();
    ytransform += current.Y();
    ztransform += current.Z();
  }
  
  if (!file.eof()) {
    cerr << "Error: empty line found" << endl;
    return -1;
  }
  
  if (lineCount < 2) {
    cerr << "Error: file must contain at least two lines" << endl;
    return -1;
  }
  
  xtransform /= lineCount;
  ytransform /= lineCount;
  ztransform /= lineCount;

  for (vector<Pose>::iterator iter = poses.begin(); iter != poses.end(); ++iter){
    for (int i=0;i<25;i++){
      Point3D& c = (*iter).getPoint3D(i);
      c.transform(-xtransform,-ytransform,-ztransform);
      maxVal = (fabs(c.X()) > maxVal ? fabs(c.X()) : maxVal);
      maxVal = (fabs(c.Y()) > maxVal ? fabs(c.Y()) : maxVal);
      maxVal = (fabs(c.Z()) > maxVal ? fabs(c.Z()) : maxVal);
    }
  }
  
  if (maxVal == 0)
    return 0;

  cout << maxVal << endl;

  for (vector<Pose>::iterator iter = poses.begin(); iter != poses.end(); ++iter){
    for (int i=0;i<25;i++){
      Point3D& c = (*iter).getPoint3D(i);
      c.scale(1 / maxVal);
    }
  }
  
  return 0;
}

int PoseSequence::writePoseSequence(PoseDisplay &pDisplay) {

  for (vector<Pose>::iterator iter = poses.begin(); iter != poses.end(); ++iter){
    (*iter).writePose(pDisplay);
  }
  return 0;
}

double PoseSequence::poseSequenceCompare(ofstream &file){
  for (std::size_t i=0, max=poses.size()-1;i<max;i++)
    file << poses.at(i).poseCompare(poses.at(i+1)) << endl;
  return 0;
}



