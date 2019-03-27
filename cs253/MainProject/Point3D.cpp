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


double Point3D::pointCompare(const Point3D p) const{
  return sqrt(  pow( (X()-p.X()),2 ) + pow( (Y()-p.Y()),2 ) + pow( (Z()-p.Z()),2 )  );
}
