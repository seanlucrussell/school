#include <Point3D.h>

void Point3D::scale (const double scale){
  x *= scale;
  y *= scale;
  z *= scale;
}


void Point3D::transform(const double xTransform, const double yTransform, const double zTransform){
  x += xTransform;
  y += yTransform;
  z += zTransform;
}


double Point3D::pointCompare(const Point3D &p) const{
  return sqrt(  pow( (X()-p.X()),2 ) + pow( (Y()-p.Y()),2 ) + pow( (Z()-p.Z()),2 )  );
}
