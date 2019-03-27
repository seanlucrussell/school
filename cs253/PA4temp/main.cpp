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
  cout << "Usage: pa1 in_file_1 in_fil_2 out_file" << endl;
}

int main(int argc, char* argv[]){

  // Check for correct number of arguments
  // If number is incorrect, print error and return -1
  if (argc != 4){
    cerr << "Error: incorrect number of arguments" << endl;
    usage();
    return -1;
  }

  // Check first argument
  // If not valid file name, print error and return -1
  fstream fileStr1;
  fileStr1.open(argv[1]);
  if (!fileStr1.is_open()) {
    cerr << "Error: in_file_1 could not be opened for reading" << endl;
    usage();
    return -1;
  }

  // Check second argument
  // If not valid file name, print error and return -1
  fstream fileStr2;
  fileStr2.open(argv[2]);
  if (!fileStr2.is_open()) {
    cerr << "Error: in_file_2 could not be opened for reading" << endl;
    usage();
    return -1;
  }

  // If failed to read file for any reason, return -1
  PoseSequence sequence1;
  if(sequence1.readPoseSequence(fileStr1)==-1)
    return -1;

  // If failed to read file for any reason, return -1
  PoseSequence sequence2;
  if(sequence2.readPoseSequence(fileStr2)==-1)
    return -1;

  // Make sure sequence1 is shorter than sequence2
  if(sequence1.size()>sequence2.size()){
    PoseSequence temp = sequence1;
    sequence1 = sequence2;
    sequence2 = temp;
  }

  //cout << sequence1.size() << endl;
  //cout << sequence2.size() << endl;


  ofstream outFile;
  outFile.open(argv[3]);
  sequence1.poseSequenceCompare(outFile,sequence2);  // Change to outFile when ready
 
  
  
  fileStr1.close();
  fileStr2.close();
  outFile.close();
  return 0;
}
