#ifndef OBJECT_H
#define OBJECT_H

#include "Intersection.h"

class Object {
 public:
  Intersection Intersect() = 0;
  double IndexOfRefraction;
  
  
};

#endif
