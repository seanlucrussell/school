#include "Operators.h"

#include <iostream>

int Sort::operator() (Pose a, Pose b){
  switch (n%3) {
    case 0: return a.getPoint3D(n/3).X() < b.getPoint3D(n/3).X();
    case 1: return a.getPoint3D(n/3).Y() < b.getPoint3D(n/3).Y();
    case 2: return a.getPoint3D(n/3).Z() < b.getPoint3D(n/3).Z();
  }
  return -1;
}


int Left::operator() (Pose p){
  return p.getPoint3D(11).Z() < p.getPoint3D(7).Z();
}

Pose& Rotate::operator() (Pose& p){
  double start = 0;
  for(int i=0;i<n;i++){
    start = p.getPoint3D(0).X();
    for(int i=0;i<24;i++){
      p.getPoint3D(i).setX(p.getPoint3D(i).Y());
      p.getPoint3D(i).setY(p.getPoint3D(i).Z());
      p.getPoint3D(i).setZ(p.getPoint3D(i+1).X());
    }
    p.getPoint3D(24).setX(p.getPoint3D(24).Y());
    p.getPoint3D(24).setY(p.getPoint3D(24).Z());
    p.getPoint3D(24).setZ(start);
  }
  return p;
}

Pose& Transform::operator() (Pose& p){
  for (int i=0;i<25;i++){
    Point3D& p3 = p.getPoint3D(i);
    p3.setX(p3.X() + n);
    p3.setY(p3.Y() + n);
    p3.setZ(p3.Z() + n);
  }
  return p;
}

