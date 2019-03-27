#ifndef COMPLEX_H_INCLUDE
#define COMPLEX_H_INCLUDE

/*! \file Complex.h: Declares the Complex number class 
 */

#include<fstream>
using std::istream;
using std::ostream;


/*! \brief A numeric class for complex numbers with arithmetic operations.
 *
 * A class for instances of complex numbers, i.e. numbers with both real and
 * imaginary components. The class not only provides a representation for
 * complex numbers, it also defines simple arithmetic operations (+, -, *)
 * and I/O operations (<< and >>).
 */
class Complex {
public:
  /// Constructor defaults to 0+0i
  Complex(double real_part = 0.0, double imaginary_part = 0.0)
    : real(real_part), imaginary(imaginary_part) {}

  /* simple accessors */
  /// Retrieve the real part of the complex number
  inline double Real() const {return real;}
  inline double Imaginary() const {return imaginary;}
  bool Write(ostream& ostr) const;

  /* Mutators */
  inline void SetReal(double new_value) {real = new_value;}
  inline void SetImaginary(double new_value) {imaginary = new_value;}
  bool Read(istream& istr);

  void Add(const Complex& add_in);
  void Subtract(const Complex& sub_in);
  void Multiply(const Complex& mult_in);
  bool Divide(const Complex& div_in);

private:

  double real;       /// the real component 
  double imaginary;  /// the imaginary component
};


#endif // COMPLEX_H_INCLUDE
