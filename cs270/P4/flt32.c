#include "flt32.h"

/** @file flt32.c
 *  @brief You will modify this file and implement nine functions
 *  @details Your implementation of the functions defined in flt32.h.
 *  You may add other function if you find it helpful. Added function
 *  should be declared <b>static</b> to indicate they are only used
 *  within this file.
 *  <p>
 *  @author <b>Sean Russell</b> goes here
 */

// FINISHED
int flt32_get_sign (flt32 x) {
  return (x >> 31) & 1;
}
// FINISHED
int flt32_get_exp (flt32 x) {
  return ((x&~(1 << 31))>>23);
}
// FINISHED
int flt32_get_val (flt32 x) {
  return (x & ((1 << 24)-1)) | 1 << 23;
}
// FINISHED
void flt32_get_all(flt32 x, int* sign, int* exp, int* val) {
  *sign = flt32_get_sign(x);
  *exp = flt32_get_exp(x);
  *val = flt32_get_val(x);
}
// FINISHED
int flt32_left_most_1 (int bits) {
  int position = 31;
  while (position > -1){
    if (flt32_get_sign(bits)){
      return position;
    }
    position--;
    bits = bits << 1;
  }
  return -1;
}
// FINISHED
flt32 flt32_abs (flt32 x) {
  return x & ~(1 << 31);
}
// FINISHED
flt32 flt32_negate (flt32 x) {
  return x ^ (1 << 31);
}

/** Add two floating point values
 *  @param x an integer containing a IEEE floating point value
 *  @param y an integer containing a IEEE floating point value
 *  @return x + y. Your code needs to account for a value of 0.0, but no other
 *  special cases (e.g. infinities)
 */
flt32 flt32_add (flt32 x, flt32 y) {
  // Return one value if other is zero
  if (x==0)
    return y;
  if (y==0)
    return x;
  
  // Create data to hold float information for x and y
  flt32 xSign = 0, xExp = 0, xVal = 0;
  flt32 ySign = 0, yExp = 0, yVal = 0;
  
  // STEP 1: Decompose operands
  flt32_get_all(x,&xSign,&xExp,&xVal);
  flt32_get_all(y,&ySign,&yExp,&yVal);

  // Create data to construct result float
  flt32 rSign = 0, rExp = 0, rVal = 0;
  flt32 rFlt = 0;
  
  // STEP 2: Equalizing operand exponents
  if (xExp > yExp){
    int diff = xExp - yExp;
    yVal = yVal >> diff;
    yExp = yExp + diff;
  }
  if (yExp > xExp){
    int diff = yExp - xExp;
    xVal = xVal >> diff;
    xExp = xExp + diff;
  }
  rExp = xExp;
  
  // STEP 3: Convert operands from signed magnitude to twos complement
  if (xSign){
    xVal = (~xVal) + 1;
  }
  if (ySign){
    yVal = (~yVal) + 1;
  }
  
  // STEP 4: Add mantissas
  rVal = xVal + yVal;
  
  // STEP 5: Convert from twos complement to signed magnitude
  if (flt32_get_sign(rVal)){
    rSign = 1;
    rVal = (~rVal) + 1;
  }
  
  // STEP 6: Normalize result
  rExp = rExp + flt32_left_most_1(rVal) - 23;
  if ((flt32_left_most_1(rVal) - 23) > 0)
    rVal = rVal >> (flt32_left_most_1(rVal) - 23);
  if ((flt32_left_most_1(rVal) - 23) < 0)
    rVal = rVal << (23 - flt32_left_most_1(rVal));

  
  // STEP 7: Compose result (and remove implicit 1)
  rFlt = (rSign << 31) | (rExp << 23) | (rVal & ((1 << 23)-1));
  
  return rFlt;
}

/** Subtract two floating point values
 *  @param x an integer containing a IEEE floating point value
 *  @param y an integer containing a IEEE floating point value
 *  @return x - y. Your code needs to account for a value of 0.0, but no other
 *  special cases (e.g. infinities)
 */
flt32 flt32_sub (flt32 x, flt32 y) {
  return flt32_add(x,y ^ (1 << 31));
}


