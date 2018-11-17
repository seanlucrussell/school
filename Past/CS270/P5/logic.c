/** @file       logic.c
 *  @brief      ... fill this in
 *  @details    ... fill this in
 *
 *  @author     ... fill this in
 *  @date       ... fill this in
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

