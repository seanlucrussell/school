#include <Distance.h>

Distance* Distance::getDistance(const char* distType){
  Distance* dist = NULL;
  if (strcmp(distType,"avg")==0)
    dist = new AverageDistance();
  else if (strcmp(distType,"med")==0)
    dist = new MedianDistance;
  else if (strcmp(distType,"Linf")==0)
    dist = new MaxDistance();
  else if (strcmp(distType,"L2")==0)
    dist = new EuclidianDistance();
  return dist;
}

double Distance::poseDistance(const Pose& a, const Pose& b){
  double total = 0;
  for (int i=0;i<25;i++)
    total += a.getPoint3D(i).pointCompare(b.getPoint3D(i));
  return total;
}

double MedianDistance::poseDistance(const Pose& a, const Pose& b){
  vector<double> dists(25);
  for (int i=0;i<25;i++)
    dists.push_back(a.getPoint3D(i).pointCompare(b.getPoint3D(i)));
  std::sort(dists.begin(), dists.end()); 
  return dists[12];
}

double AverageDistance::poseDistance(const Pose& a, const Pose& b){
  double total = 0;
  for (int i=0;i<25;i++)
    total += a.getPoint3D(i).pointCompare(b.getPoint3D(i));
  return total/25;
}

double MaxDistance::poseDistance(const Pose& a, const Pose& b){
  double max = a.getPoint3D(0).pointCompare(b.getPoint3D(0));
  for (int i=1;i<25;i++)
    if (a.getPoint3D(i).pointCompare(b.getPoint3D(i)) > max)
      max = a.getPoint3D(i).pointCompare(b.getPoint3D(i));
  return max;
}

double EuclidianDistance::poseDistance(const Pose& a, const Pose& b){
  double total = 0;
  for (int i=0;i<25;i++)
    total += pow(a.getPoint3D(i).pointCompare(b.getPoint3D(i)),2);
  return sqrt(total);
}

