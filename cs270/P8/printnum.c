/** @file printnum.c
 *  @brief You will modify this file and implement three functions
 *  @details Your implementation of the functions defined in printnum.h.
 * <p>
 * @author <b>Sean Russell</b> goes here
 */

#include <stdio.h> // for putchar()

#include "printnum.h"

/** @todo Implement based on documentation contained in printnum.h */
char getDigit (int val) {
  if (val <= 9)
    return val + '0';
  return val + 'A' - 10;
}

/** @todo Implement based on documentation contained in printnum.h */
void divRem (int numerator, int divisor, int* quotient, int* remainder) {
  *quotient = 0;
  while (numerator >= divisor) {
    numerator = numerator - divisor;
    *quotient = *quotient + 1;
  }
  *remainder = numerator;
}

/** @todo Implement based on documentation contained in printnum.h */
void printNum (int x, int base) {
  int q = 0;
  int r = 0;
  divRem (x,base,&q,&r);
  if (q<=0) {
    putchar(getDigit(r));
    return;
  }
  printNum(q,base);
  putchar(getDigit(r));
}

