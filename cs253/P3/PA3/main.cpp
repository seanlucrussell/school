#include <iostream>
#include <fstream>
#include <vector>

#include <PoseDisplay.h>
#include <PoseSequence.h>

using std::cout;
using std::endl;
using std::cerr;
using std::fstream;


// Proper usage
void usage() {
  cout << "Usage: pa1 in_file out_file" << endl;
}

int main(int argc, char* argv[]){

  // Check for correct number of arguments
  // If number is incorrect, print error and return -1
  if (argc != 3){
    cerr << "Error: incorrect number of arguments" << endl;
    usage();
    return -1;
  }

  // Check first argument
  // If not valid file name, print error and return -1
  fstream fileStr;
  fileStr.open(argv[1]);
  if (!fileStr.is_open()) {
    cerr << "Error: in_file could not be opened for reading" << endl;
    usage();
    return -1;
  }

  // If failed to read file for any reason, return -1
  PoseSequence p;
  if(p.readPoseSequence(fileStr)==-1)
    return -1;

  ofstream outFile;
  outFile.open(argv[2]);
  p.poseSequenceCompare(outFile);
  outFile.close();

  // Call constructor for PoseDisplay with out_file
  // If out_file cannot be opened, print error and return -1
  //PoseDisplay pDisplay(argv[2]);
  //if (p.writePoseSequence(pDisplay)==-1)
  //  return -1; 
  
  
  fileStr.close();
  return 0;
}
