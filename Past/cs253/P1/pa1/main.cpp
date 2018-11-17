#include <iostream>
#include <fstream>
#include <PoseDisplay.h>
#include <Point3D.h>
#include <vector>
#include <sstream>



//STILL NEED TO DEAL WITH PROBLEM INPUT FILES
// If line is empty but not eof



using namespace std;

// Proper usage
void usage() {
  cout << "Usage: pa1 in_file out_file" << endl;
}

// Convert a line of text to a vector
// Return 0 if line was properly formatted, -1 otherwise
int lineToPose(const string& line, vector<Point3D>& pose) {
  //cout << line << endl;
  istringstream lineStream (line);
  double x,y,z;
  for (int i=0;i<25;i++){
    lineStream >> x;
    lineStream >> y;
    lineStream >> z;
    Point3D p (x,y,z);
    pose.push_back(p);
  }
  if (lineStream.fail()) {
    cerr << "Error: value other than double encountered" << endl;
    return -1;
  }
  string s;
  lineStream >> s;
  if (!lineStream.fail()) {
    cerr << "Error: too many entries on line" << endl;
    return -1;
  }

  return 0;
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

  // Call constructor for PoseDisplay with out_file
  // If out_file cannot be opened, print error and return -1
  try {
    PoseDisplay* pDisplay = new PoseDisplay(string(argv[2]));
    string line;
    getline(fileStr,line);
    vector<Point3D> pose;
    int fail = 0;
    
    while (!line.empty()) {
      pose.clear();
      fail = lineToPose(line,pose);
      if (fail == -1)
        return -1;
      pDisplay->Pose(pose);
      getline(fileStr,line);
    }
    if (!fileStr.eof()) {
      cerr << "Error: empty line found" << endl;
      return -1;
    }
    
    //cout << line << endl;
    delete pDisplay;
  }
  catch (std::exception) {
    cerr << "Error: out_file could not be opened for writing" << endl;
    usage();
    return -1;
  }  
  
  fileStr.close();
  return 0;
}
