#include <iostream>
#include <fstream>
#include <vector>
#include <cstring>
#include <PoseSequence.h>
#include <DistanceArray.h>
#include <Distance.h>

using std::cout;
using std::endl;
using std::cerr;
using std::fstream;

void usage() {
  cout << "Usage: pa1 long_file short_file_n ... dist" << endl << "Dist must be avg, med, Linf, or L2, case sensitive" << endl;
}

/// Prints error to console
/// @param error: string holding error message
/// @return returns -1
int error(const char* error){
  cerr << "Error: " << error << endl;
  usage();
  return -1;
}

int main(int argc, char* argv[]){
  // Check for correct number of arguments
  if (argc < 4)
    return error("too few arguments");
  // Check long sequence
  PoseSequence sequenceLong;
  if (sequenceLong.readFromName(argv[1])==-1)
    return error("failed to read long sequence");
  // Check other arguments
  vector<PoseSequence> shortPoses;
  for (int i=0;i<argc-3;i++){
    PoseSequence p;
    shortPoses.push_back(p);
    if (shortPoses[i].readFromName(argv[2+i])==-1)
      return error("failed to read short sequence");
    if (shortPoses[i].size() > sequenceLong.size())
      return error("one of the short videos is longer than the long video");
  }
  // Select distance type to use
  Distance* dist = Distance::getDistance(argv[argc-1]);
  if (dist==NULL)
    return error("invalid distance argument");

  DistanceArray d;
  d.poseSequenceCompare(shortPoses[0],sequenceLong,*dist);
  int smallestIndex = 0;
  double smallest = d.dynamicTimeWarping();
  for(size_t i=0;i<shortPoses.size();i++) {
    DistanceArray d;
    d.poseSequenceCompare(shortPoses[i],sequenceLong,*dist);
    double dtw = d.dynamicTimeWarping();
    if(dtw < smallest){
      smallest = dtw;
      smallestIndex = i;
    }
  }
  cout << argv[smallestIndex + 2] << endl;
  
  delete dist;
  return 0;
}
