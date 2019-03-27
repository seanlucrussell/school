#include <iostream>
#include <fstream>
#include <vector>
#include <cstring>
#include <PoseSequence.h>

using std::cout;
using std::endl;
using std::cerr;
using std::fstream;


/// Prints error to console
int error(const char* error){
  cerr << "Error: " << error << endl;
  return -1;
}

int main(int argc, char* argv[]){
  // Check for correct number of arguments
  if (argc != 5)
    return error("incorrect number of arguments");
  // Check long sequence
  PoseSequence seq;
  if (seq.readFromName(argv[1])==-1)
    return error("failed to read sequence");



  if (strcmp(argv[3],"sort") == 0){
    // sort
  } else if (strcmp(argv[3],"rotate") == 0){
    // rotate
  } else if (strcmp(argv[3],"swap") == 0){
    // swap
  } else if (strcmp(argv[3],"left") == 0){
    // left
  } else{
    return error("unsupported operation");
  }


  return 0;
}
