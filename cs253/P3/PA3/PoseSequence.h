#ifndef POSESEQUENCE_H
#define POSESEQUENCE_H

#include <iostream>
#include <vector>
#include <fstream>
#include <cmath>

#include <Pose.h>
#include <PoseDisplay.h>
#include <Point3D.h>

using std::vector;
using std::fstream;
using std::ofstream;

class PoseSequence {
public:

  int readPoseSequence(fstream &file);			 // reads a sequence of poses from a file and normalizes
  int writePoseSequence(PoseDisplay &pDisplay);		 // write values in poses to file and display

  double poseSequenceCompare(ofstream &file);


protected:
  vector<Pose> poses;                                    // vector to hold all of the poses
};


#endif // POSE_SEQUENCE_H included
