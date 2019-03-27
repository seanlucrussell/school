#ifndef DISTANCEARRAY_H
#define DISTANCEARRAY_H

#include <vector>
#include <iostream>

#include <Pose.h>
#include <PoseSequence.h>
#include <iomanip>
#include <Distance.h>

class DistanceArray{
 public:
  void poseSequenceCompare(const PoseSequence& a, const PoseSequence& b, Distance& d);
  void printArray() const;
  double dynamicTimeWarping();
 protected:
  vector< vector <double> > distances;
};

#endif
