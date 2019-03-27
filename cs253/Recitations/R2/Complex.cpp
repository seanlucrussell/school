#include <Complex.h>

/*! \file Complex.cpp: implements the Complex number class */

/*! Increment this complex number by another. This means
 * adding the real parts together and the imaginary parts
 * together. 
 */
void Complex::Add(const Complex& add_in)
{
  real += add_in.Real();
  imaginary += add_in.Imaginary();
}

/*! Decrement this complex number by another. This means
 * subtracting the real parts from each other and the 
 * imaginary parts from each other.
 */
void Complex::Subtract(const Complex& sub_in)
{
  real -= sub_in.Real();
  imaginary -= sub_in.Imaginary();
}

/*! Destructively multiply two complex numbers together, modifying. 
 * the first. Since i*i = -1, the real part ends up being the product
 * of the real parts minus the product of the imaginary parts, while
 * the imaginary part ends up being the sum of the cross terms.
 */
void Complex::Multiply(const Complex& mult_in)
{
  double new_real = Real() * mult_in.Real();
  new_real -= Imaginary() * mult_in.Imaginary();
  double new_imag = Real() * mult_in.Imaginary();
  new_imag += Imaginary() * mult_in.Real();
  real = new_real;
  imaginary = new_imag;
}

/*! Destructively divide this number by the argument. To divide two
 * complex numbers, multiply both numbers by the complex complement
 * of the divisor first. This will make the divisor real. Then do
 * the division.
 */
bool Complex::Divide(const Complex& div_in)
{
  Complex complement(div_in.Real(), -div_in.Imaginary());
  Complex temp_divisor(div_in);
  Multiply(complement);
  temp_divisor.Multiply(complement);

  if (temp_divisor.Real() == 0.0) return false;
  else {
    real /= temp_divisor.Real();
    imaginary /= temp_divisor.Real();
    return true;
  }
}




/// Input operator. Format is "a+bi" or "a-bi", where a and b are real numbers.
/// Any other format causes the input stream to fail.
bool Complex::Read (istream& istr) 
{
  char ch_connector, ch_i;
  istr >> real >> ch_connector >> imaginary >> ch_i;
  if (istr.fail()) return false;

  // if connector is +, then imaginary part is OK
  if (ch_connector == '+') {}
  // if the connector is minus, negate the imaginary part
  else if (ch_connector == '-') {
    imaginary = -imaginary;
  } 
  // otherwise, the connector is an error!
  else {
    return false;
  }

  // make sure the second number ends in i.
  if (ch_i != 'i')  {
    return false;
  }
  return true;
}

bool Complex::Write(ostream& ostr) const
{
  if (ostr.fail()) return false;

  ostr << Real();

  if (Imaginary() >= 0.0) {
    ostr << '+' << Imaginary();
  }
  else {
    // remember that negatives print with a '-' anyway!
    ostr << Imaginary();
  }

  ostr << 'i';

  return true;
}
