#include <PoseSequence.h>
#include <iostream>

#include <iomanip>

int PoseSequence::readPoseSequence(fstream &file) {
  string line;
  getline(file,line);
  int fail = 0;

  int lineCount = 0;
  double xtransform = 0;
  double ytransform = 0;
  double ztransform = 0;

  double maxVal = 0;

  while (!line.empty()/*FIGURE THIS SHIT OUT*/) {
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
  getline(file,line); // SERIOUSLY WHAT THE HELL
  if (!file.eof()) {
    cerr << "Error: empty line found" << endl;
    return -1;
  }
  
  if (poses.size()==0){
    cerr << "Error: file is empty" << endl;
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

double PoseSequence::poseSequenceCompare(ostream &file, PoseSequence& other){
  for (std::size_t i=0, max=poses.size();i<max;i++) {
    for (std::size_t j=0, max=other.size();j<max;j++)
      file << std::fixed << std::setprecision(8) <<  poses.at(i).poseCompare(other.getPose(j)) << " ";
    file << endl;
  }
  return 0;
}



