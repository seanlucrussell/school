#ifndef DISTANCE_H
#define DISTANCE_H

#include <Pose.h>
#include <vector>
#include <algorithm>
#include <cstring>

class Distance{
 public:
  virtual double poseDistance(const Pose& a, const Pose& b);
  inline virtual ~Distance(){};
  static Distance* getDistance(const char* distType);
};

class MedianDistance: public Distance{
 public:
  virtual double poseDistance(const Pose& a, const Pose& b);
  inline virtual ~MedianDistance(){};
};

class AverageDistance: public Distance{
 public:
  virtual double poseDistance(const Pose& a, const Pose& b);
  inline virtual ~AverageDistance(){};
};

class MaxDistance: public Distance{
 public:
  virtual double poseDistance(const Pose& a, const Pose& b);
  inline virtual ~MaxDistance(){};
};

class EuclidianDistance: public Distance{
 public:
  virtual double poseDistance(const Pose& a, const Pose& b);
  inline virtual ~EuclidianDistance(){};
};


#endif
