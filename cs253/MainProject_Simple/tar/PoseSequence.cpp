#include <PoseSequence.h>

int PoseSequence::readFromName(const char* fileName) {
  fstream fileStr;
  fileStr.open(fileName);
  if(fileStr.is_open() == 0 || readPoseSequence(fileStr)==-1){
    fileStr.close();
    return -1;
  }
  fileStr.close();
  return 0;
}

int PoseSequence::readPoseSequence(fstream &file) {
  string line;
  getline(file,line);

  while (!line.empty()/*FIGURE THIS SHIT OUT*/) {
    Pose p;
    if (p.readPose(line) == -1)
      return -1;
    poses.push_back(p);
    getline(file,line);

  }
  getline(file,line); // SERIOUSLY WHAT THE HELL
  if (!file.eof()) {
    cerr << "Error: empty line found" << endl;
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


