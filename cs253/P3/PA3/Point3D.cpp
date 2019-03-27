#include <Point3D.h>

void Point3D::scale (double scale){
  x *= scale;
  y *= scale;
  z *= scale;
}


void Point3D::transform(double xTransform, double yTransform, double zTransform){
  x += xTransform;
  y += yTransform;
  z += zTransform;
}


double Point3D::pointCompare(Point3D p){
  return sqrt(  pow( (x-p.X()),2 ) + pow( (y-p.Y()),2 ) + pow( (z-p.Z()),2 )  );
}
