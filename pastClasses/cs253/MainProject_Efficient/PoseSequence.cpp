#include <PoseSequence.h>

int PoseSequence::readFromName(const char* fileName) {
  fstream fileStr;
  fileStr.open(fileName);
  if(readPoseSequence(fileStr)==-1){
    fileStr.close();
    return -1;
  }
  fileStr.close();
  return 0;
}

int PoseSequence::readPoseSequence(fstream &file) {
  if (!file.is_open())
    return -1;

  string line;
  getline(file,line);
  int fail = 0;

  while (!line.empty()/*FIGURE THIS SHIT OUT*/) {
    Pose p;
    fail = p.readPose(line);
    if (fail == -1)
      return -1;
    poses.push_back(p);
    getline(file,line);
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
  
  return 0;
}

int PoseSequence::writePoseSequence(PoseDisplay &pDisplay) {
  for (vector<Pose>::iterator iter = poses.begin(); iter != poses.end(); ++iter){
    (*iter).writePose(pDisplay);
  }
  return 0;
}

