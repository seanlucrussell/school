#ifndef MATERIAL_H
#define MATERIAL_H

#include <string>
#include <Eigen/Dense>

class Material {
 public:
  std::string name;
  Eigen::Vector3d ambient;
  Eigen::Vector3d diffuse;
  Eigen::Vector3d specular;
  Eigen::Vector3d attenuation;
  // material files can have more than one material, fix this in the future!
  void ReadFromFile(std::string& fileName);
 private:
  void ParseLine(std::string& line);
};

#endif
