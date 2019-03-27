#include "field.h"

/** @file field.c
 *  @brief You will modify this file and implement six functions
 *  @details Your implementation of the functions defined in field.h.
 *  You may add other function if you find it helpful. Added functions
 *  should be declared <b>static</b> to indicate they are only used
 *  within this file. For example, methods you write may need a mask value.
 *  You might write a method to compute a mask for you and use it wherever
 *  it is needed as opposed to just putting the code in line.
 *  These additional <b>static</b> functions are similar to Java’s private methods.
 * <p>
 * @author <b>Sean Russell</b> goes here
 */


int getBit (int value, int position) {
    value = value >> position;
    return value & 1;
}

int setBit (int value, int position) {
    int mask = 1 << position;
    return value | mask;
}

int clearBit (int value, int position) {
    int mask = ~(1 << position);
    return value & mask;
}

int getField (int value, int hi, int lo, bool isSigned) {
    if (hi < lo){
        int a = lo;
        lo = hi;
        hi = a;
    }
    value = value >> lo;
    hi = hi-lo;
    if (isSigned && getBit(value,hi)){
        int mask = ~((1 << hi)-1);
        value = value | mask;
    }
    else {
        int mask = (1 << (hi+1))-1;
        value = value & mask;
    }
    return value;
}

int setField (int oldValue, int hi, int lo, int newValue) {
    if (hi < lo){
        int a = lo;
        lo = hi;
        hi = a;
    }
    int mask = (1 << (hi-lo+1))-1;
    newValue = newValue & mask;
    mask = ~(((1 << (hi-lo+1))-1)<<lo);
    oldValue = oldValue & mask;
    mask = newValue<<lo;
    return oldValue | mask;
}

/** Determine if a value will fit in a specified field
 *  @param value  the source value
 *  @param width the number of bits holding the value
 *  @param isSigned false means the field is unsigned, true means the field is signed
 *  @return zero if the field does NOT fit. Return 1 if the value fits.
 */
int fieldFits (int value, int width, bool isSigned) {
    int size = (1 << width)-1;
    if (isSigned)
        size = size / 2;
    if (value <= size)
        return 1;
    return 0;
}


