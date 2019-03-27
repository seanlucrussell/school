#ifndef OPERATORS
#define OPERATORS

#include "Pose.h"
#include "Point3D.h"

class Sort {
public:
  int operator() (Pose a, Pose b);
  inline Sort(int n) : n(n) {};
private:
  int n;
};

class Left {
public:
  int operator() (Pose p);
};

class Rotate {
public:
  Pose& operator() (Pose& p);
  inline Rotate(int n) : n(n) {};
private:
  int n;
};

class Transform {
public:
  Pose& operator() (Pose& p);
  inline Transform(double n) : n(n) {};
private:
  double n;
};

#endif
