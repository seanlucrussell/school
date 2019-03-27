#include <DistanceArray.h>

void DistanceArray::poseSequenceCompare(const PoseSequence& a, const PoseSequence& b, Distance& d) {
  for (size_t i=0, maxa=a.size();i<maxa;i++){
    vector<double> temp;
    for (size_t j=0, maxb=b.size();j<maxb;j++){
      temp.push_back(d.poseDistance(a.getPose(i),b.getPose(j)));
    }
    distances.push_back(temp);
  }
}

void DistanceArray::printArray() const {
  for (size_t i = 0; i < distances.size(); i++){
    for (size_t j = 0; j < distances[i].size(); j++)
        cout << std::fixed << std::setprecision(8) << distances[i][j] << " ";
    cout << endl;
  }
}

double DistanceArray::dynamicTimeWarping() {
  vector< vector<double> > w;
  w.push_back(distances[0]);

  for (size_t i=1;i<distances.size();i++){
    vector<double> temp;
    for (size_t j=0;j<distances[i].size();j++){
      double smallest = w[i-1][0];
      for (size_t k=1;k<=j;k++){
        if (w[i-1][k]<smallest)
          smallest = w[i-1][k];
      }
      temp.push_back(smallest + distances[i][j]);
    }
    w.push_back(temp);
  }
  
  double smallest = w[w.size()-1][0];
  for (size_t i=0;i<w[0].size();i++)
    if(w[w.size()-1][i]<smallest)
      smallest = w[w.size()-1][i];
  return smallest;
}
