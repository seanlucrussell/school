#include <iostream>
#include <string>
#include <fstream>
#include <sys/stat.h>
#include <sstream>
#include <algorithm>
#include <iterator>
#include <vector>
#include <math.h>
#include <Eigen/Dense>
#include <map>

using namespace std;
using namespace Eigen;

class Object {
public:
    // vertices is a nx4 matrix where each column represents a vertex in the object using
    // homogenous coordinates
    MatrixXd vertices;
    // faces is a list of vectors with three entries, representing the index of vertices in
    // the vertices matrix
    vector<string> otherLines;

    vector<string> comments;
};


void ParseObjectLine(string& line, Object& object, vector<VectorXd>& vertices,
	             vector<string>& otherLines) {
    //cout << line << endl;
    istringstream iss(line);
    vector<string> tokens {istream_iterator<string>{iss}, istream_iterator<string>{}};
    if (tokens.size() > 0) {
	if (tokens[0] == "v") {
	    VectorXd vertex(4);
	    vertex << stof(tokens[1]), stof(tokens[2]), stof(tokens[3]), 1;
	    //cout << "vertex " << endl << vertex << endl;
	    vertices.push_back(vertex);
	}
	else if (tokens[0] == "f") {
	    otherLines.push_back(line);
	    //cout << line << endl;
	}
	else if (tokens[0] == "#") {
	    object.comments.push_back(line);
	}
    }
}

Object ReadObjectFile(std::string& fileName) {
    ifstream objectFile(fileName);
    string line;
    if (objectFile) {
	Object object;
	vector<VectorXd> vertices;
	vector<string> otherLines;
	while (getline(objectFile,line)) {
	    ParseObjectLine(line, object, vertices, otherLines);
	}
	MatrixXd vertexMatrix(4,vertices.size());
	for (int i = 0; i < vertices.size(); ++i){
	    vertexMatrix.col(i) = vertices[i];
	}
	object.vertices = vertexMatrix;
	object.otherLines = otherLines;
	//cout << vertexMatrix << endl;
	return object;
    }
    else {
	string exceptionMsg = "Could not read object file " + fileName;
	throw runtime_error(exceptionMsg);
    }
}

void WriteObjectFile(Object& object, string& fileName) {
    ofstream newObjectFile;
    newObjectFile.open(fileName);

    for (int i=0; i<object.comments.size(); ++i) {
	newObjectFile << object.comments[i] << endl;
    }
    newObjectFile << "# transformed from original file" << endl;

    for (int i=0; i<object.vertices.cols(); ++i) {
	newObjectFile.precision(7);
	newObjectFile << "v " << fixed << object.vertices(0,i) << " " <<
	    object.vertices(1,i) << " " << object.vertices(2,i) << endl;
    }

    for (int i=0; i<object.otherLines.size(); ++i) {
	newObjectFile << object.otherLines[i] << endl;
    }
}

void modelTransform(vector<string>& transform, string& directory, map<string,int>& newFiles) {
    //cout << transform[0] << endl;

    string file = transform[9];

    MatrixXd rotation = MatrixXd::Identity(4,4);
    float theta = stof(transform[4]) * 3.1415926 / 180.0f;
    Vector3d w;
    w << stof(transform[1]),stof(transform[2]),stof(transform[3]);
    w.normalize();
    Vector3d m = w;
    int smallest = 0;
    if (w(1) < w(0)) smallest = 1;
    if (w(2) < w(smallest)) smallest = 2;
    m(smallest) = 1;
    m.normalize();
    Vector3d u = w.cross(m);
    u.normalize();
    Vector3d v = w.cross(u);
    //cout << w << endl << m << endl << u << endl << v << endl;
    rotation(0,0) = u(0);
    rotation(0,1) = u(1);
    rotation(0,2) = u(2);
    rotation(1,0) = v(0);
    rotation(1,1) = v(1);
    rotation(1,2) = v(2);
    rotation(2,0) = w(0);
    rotation(2,1) = w(1);
    rotation(2,2) = w(2);
    MatrixXd zRotation = MatrixXd::Identity(4,4);
    zRotation(0,0) = cos(theta);
    zRotation(0,1) = -sin(theta);
    zRotation(1,0) = sin(theta);
    zRotation(1,1) = cos(theta);
    rotation = rotation.transpose() * zRotation * rotation;
    //cout << endl << rotation << endl << endl;

    MatrixXd scaling = MatrixXd::Identity(4,4);
    float scale = stof(transform[5]);
    scaling(0,0) = scale;
    scaling(1,1) = scale;
    scaling(2,2) = scale;
    //cout << endl << scaling << endl << endl;

    MatrixXd translation = MatrixXd::Identity(4,4);
    translation(0,3) = stof(transform[6]);
    translation(1,3) = stof(transform[7]);
    translation(2,3) = stof(transform[8]);
    //cout << endl << translation << endl << endl;

    MatrixXd transformMatrix = translation * scaling * rotation;
    //cout << endl << transformMatrix << endl << endl;

    Object object;
    try {
    	object = ReadObjectFile(file);
	object.vertices = transformMatrix * object.vertices;
    }
    catch (const exception& e) {
	cerr << e.what() << endl;
    }
    

    int extension = 0;
    if (newFiles.find(file) != newFiles.end()) {
	extension = newFiles[file];
	newFiles[file] = 1 + newFiles[file];
    } else {
	newFiles[file] = 1;
    }

    string newFile = file.substr(0,file.find('.')) + "_mw0" + to_string(extension) + ".obj";

    string outFile = directory + "/" + newFile;
    WriteObjectFile(object, outFile);
}

void parseDriverLine(string& line, string& directory, map<string,int>& newFiles) {
    istringstream iss(line);
    vector<string> tokens {istream_iterator<string>{iss}, istream_iterator<string>{}};
    if (tokens.size() > 0) {
	if (tokens[0] == "model") {
	    if (tokens.size() < 9) {
		cerr << "Insufficient number of instructions in line:" << endl << line << endl;
	    }
	    else {
	        try {
	    	    modelTransform(tokens, directory, newFiles);
	        } catch (const invalid_argument& exception) {
		    cerr << "Invalid line in driver file:" << endl << line << endl;
	    	}
	    }
	}
    }
}

void parseDriverFile(std::ifstream& driverFile, std::string& fileName) {
    string directoryName = fileName.substr(0,fileName.size()-4);
    mkdir(directoryName.c_str(), 0700);
    string line;

    map<string,int> newFiles;

    while (getline(driverFile,line)) {
	parseDriverLine(line, directoryName, newFiles);
    }
    driverFile.close();
}

int main(int argc, char** argv) {
    if (argc < 2) {
        cerr << "Argument 1 missing: name of driver file" << endl;
    	return -1;
    }
    string driverFileName = argv[1];
    // MAKE SURE DRIVER FILE ENDS WITH .TXT
    ifstream driverFile (driverFileName);
    if (driverFile) {
        parseDriverFile(driverFile, driverFileName);
    }
    else {
        cerr << "Error: driver file missing at: " << driverFileName << endl;
    }
}
