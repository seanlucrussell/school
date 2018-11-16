#ifndef PIXEL_H_DEFINED
#define PIXEL_H_DEFINED
#include <algorithm>
using std::max;
using std::min;

// one method, returns char c such that 0 <= c <= 255, so if c < 0 will return 0, and if c > 255 will return 255
class Pixel {
public:
protected:
  inline unsigned char clip(int x) {return max(0, min(255, x)); } 
};

// inherits from Pixel, stores single char from 0 to 255, provides mutator, accessor, and conversion to int
class Byte : public Pixel {
public:
  Byte(int src = 0) : value(clip(src)) {}	// initializes value to src, cuts off between 0 and 255
  unsigned char Value() const {return value;}	// const accessor
  unsigned char& Value() {return value;}	// mutator
  operator int() const {return value;}		// converts Byte to int, uses int value
protected:
  unsigned char value;				// stores single char
};

// inherits from Pixel, stores 3 chars from 0 to 255, provides 2 constructors and 3 accessors, one for each color
class RGB : public Pixel {
public:
  RGB(int intensity = 0) : values(new unsigned char[3])						// 0 or 1 arg constructor, init values to array 3 long
  {values[0] = clip(intensity); values[1] = clip(intensity); values[2] = clip(intensity);}	// set each element to intensity (default 0)
  RGB(int red, int green, int blue) : values(new unsigned char[3])				// 3 arg constructor, init values to array 3 long
  {values[0] = clip(red); values[1] = clip(green); values[2] = clip(blue);}			// set each element according to arg
  unsigned char Red() const {return values[0];}							// const accessors for each color
  unsigned char Green() const {return values[1];}						// Red = 0, Green = 1, Blue = 3
  unsigned char Blue() const {return values[2];}						// each stored as char from 0 to 255
protected:
  unsigned char* values;									// array of characters, memory leak
};


#endif // PIXEL_H_DEFINED
