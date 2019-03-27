/** @file       logic.c
 *  @brief      main body of logic functions
 *  @details    Contains a number of functions emulating basic logic gates and circuts
 *
 *  @author     Sean Russell
 *  @date       September 28, 2015
 */

#include "logic.h"

/**
 * Simulates NOT logic gate.
 *
 *  @param A    input to the NOT gate
 *  @return     NOT gate output, returns NOT A
 *
 * Truth table
 <pre>
        A   return
        0   1
        1   0
 </pre>
 */
BIT not_gate(BIT A)
{
    if (A==FALSE) {
        return TRUE;
    } else {
        return FALSE;
    }
}

/**
 * Simulates OR logic gate.
 *
 *  @param A    first input to the OR gate
 *  @param B    second input to the OR gate
 *  @return     returns A OR B
 *
 * Truth table
 <pre>
        A B  return
        0 0    0    
        0 1    1    
        1 0    1    
        1 1    1    
 </pre>
 */
BIT or_gate(BIT A, BIT B)
{
    if (A==FALSE && B==FALSE) {
        return FALSE;
    } else {
        return TRUE;
    }
}

/**
 * Simulates AND logic gate.
 *
 *  @param A    first input to the AND gate
 *  @param B    second input to the AND gate
 *  @return     returns A AND B
 *
 * Truth table
 <pre>
        A B  return
        0 0    0    
        0 1    0    
        1 0    0    
        1 1    1    
 </pre>
 */
BIT and_gate(BIT A, BIT B)
{
    if (A==TRUE && B==TRUE) {
        return TRUE;
    } else {
        return FALSE;
    }
}

/**
 * Simulates XOR logic gate.
 *
 *  @param A    first input to the XOR gate
 *  @param B    second input to the XOR gate
 *  @return     returns A XOR B
 *
 * Truth table
 <pre>
        A B  return
        0 0    0    
        0 1    1    
        1 0    1    
        1 1    0    
 </pre>
 */
BIT xor_gate(BIT A, BIT B)
{
    if (A==B) {
        return FALSE;
    } else {
        return TRUE;
    }
}

/**
 * Simulates RS latch circuit.
 * Asserts if S = R = 0.
 *
 *  @param S    set input line
 *  @param R    reset input line
 *  @return     returns RS latch output
 *
 * Truth table
 <pre>
        S R  return
        0 0   assert
        0 1   1 
        1 0   0 
        1 1   previous output 
 </pre>
 */
BIT rs_latch(BIT S, BIT R)
{
    static BIT saved_bit = FALSE;
    
    // if both bits are zero then behavior of R-S latch
    // is undefined so we will have the simulation assert
    assert(!(S==FALSE && R==FALSE));
    
    // implements the truth table
    if (S==FALSE) {
        saved_bit = TRUE; // Set
    } else if (R==FALSE) {
        saved_bit = FALSE; // Reset
    } else {
        saved_bit = saved_bit; // Unchanged
    }

    return saved_bit;
}

/**
 * Simulates D latch circuit.
 *
 *  @param D    the bit to be saved
 *  @param WE   toggles write enabled for circut
 *  @return     returns D latch output
 *
 * Truth table
 <pre>
        S R  return
        0 0   previous output
        0 1   0 
        1 0   previous output
        1 1   1 
 </pre>
 */
BIT d_latch(BIT D, BIT WE)
{
    static BIT saved_bit = FALSE;
    
    // implements the truth table
    if (WE==TRUE) {
        saved_bit = D; // Set
    } else {
        saved_bit = saved_bit; // Unchanged
    }

    return saved_bit;
}


/**
 * Simulates multiplexer circut.
 *
 *  @param A    1st input to the multiplexer
 *  @param B    2nd input to the multiplexer
 *  @param C    3rd input to the multiplexer
 *  @param D    4th input to the multiplexer
 *  @param S1   Selector bit 1
 *  @param S0   Selector bit 0
 *  @return     returns multiplexer output
 *
 * Truth table
 <pre>
         <pre>
        S1 S0  return
        0  0   A
        0  1   B 
        1  0   C
        1  1   D 
 </pre>
 */
BIT multiplexer(BIT A, BIT B, BIT C, BIT D, BIT S1, BIT S0) {
    if (S1==FALSE && S0==FALSE)
        return A;
    else if (S1==FALSE && S0==TRUE)
        return B;
    else if (S1==TRUE && S0==FALSE)
        return C;
    return D;
}

/**
 * Simulates decoder circut.
 *
 *  @param A    1st input bit
 *  @param B    2nd input bit
 *  @param *O0  Address of 1st output bit
 *  @param *O1  Address of 2nd output bit
 *  @param *O2  Address of 3rd output bit
 *  @param *O3  Address of 4th output bit
 *
 * Truth table
 <pre>
         <pre>
        A B   O0 01 02 03 
        0 0   1  0  0  0
        0 1   0  1  0  0
        1 0   0  0  1  0
        1 1   0  0  0  1
 </pre>
 */
void decoder(BIT A, BIT B, BIT *O0, BIT *O1, BIT *O2, BIT *O3) {
    *O0=FALSE;
    *O1=FALSE;
    *O2=FALSE;
    *O3=FALSE;
    if (A==FALSE && B==FALSE)
        *O0=TRUE;
    if (A==FALSE && B==TRUE)
        *O1=TRUE;
    if (A==TRUE && B==FALSE)
        *O2=TRUE;
    if (A==TRUE && B==TRUE)
        *O3=TRUE;
}

/**
 * Simulates adder circut.
 *
 *  @param O1    	1st operand int (between 0 and 15)
 *  @param O2		2nd operand int (between 0 and 15)
 *  @param carryIn	Carry in bit
 *  @param *carryOut	Address of carryOut bit
 *  @return	  	returns sum output
 *
 */
int adder(int O1, int O2,  BIT carryIn, BIT* carryOut) {
    int retVal = O1 + O2 + carryIn;
    if (retVal > 15) {
        *carryOut = 1;
        retVal -= 15;
    }
    return retVal;
}
