#include <iostream>
#include <fstream>
#include <vector>
#include <cstring>
#include <algorithm>
#include <PoseSequence.h>
#include <PoseDisplay.h>
#include <Pose.h>
#include <Operators.h>

int error(const char* error){
  cerr << "Error: " << error << endl;
  return -1;
}

int main(int argc, char* argv[]){
  // Check for correct number of arguments
  if (argc != 5)
    return error("incorrect number of arguments");
  // Check read sequence
  PoseSequence seq;
  if (seq.readFromName(argv[1])==-1)
    return error("failed to read sequence");
  PoseDisplay pDisp(argv[2],0);
  vector<Pose> &poses = seq.getPoses();

  // JUST GOTTA FIGURE OUT THE RIGHT TRANSFORMATIONS
  if(strcmp(argv[3],"sort")==0){
    if(atoi(argv[4]) < 1 || atoi(argv[4]) > 75)
      return error("invalid arg4 for sort");
    sort(poses.begin(),poses.end(),Sort(atoi(argv[4])-1));
  } else if(strcmp(argv[3],"rotate")==0){
    if(atoi(argv[4]) < 1 || atoi(argv[4]) > 75)
      return error("invalid arg4 for rotate");
    transform(poses.begin(),poses.end(),poses.begin(),Rotate(atoi(argv[4])));
  } else if(strcmp(argv[3],"swap")==0){
    if(atoi(argv[4]) < 1 || atoi(argv[4]) > seq.size())
      return error("invalid arg4 for swap");
    transform(poses.begin(),poses.end(),poses.begin(),Rotate(atoi(argv[4])));
    std::swap(seq.getPose(0),seq.getPose(atoi(argv[4])-1));
  } else if(strcmp(argv[3],"left")==0){
    poses.erase(remove_if(poses.begin(),poses.end(),Left()),poses.end());
  } else if(strcmp(argv[3],"transform")==0){
    transform(poses.begin(),poses.end(),poses.begin(),Transform(atof(argv[4])));
  } else{
    return error("unsupported operation");
  }

  seq.writePoseSequence(pDisp);
  
  return 0;
}
