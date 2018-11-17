/** @file       logic.h
 *  @brief      ... fill this in
 *  @details    ... fill this in
 *
 *  @author     ... fill this in
 *  @date       ... fill this in
 */
 
// Include files
#include <assert.h>
#include <stdio.h>

// Enumerated type for a single bit.
typedef enum { FALSE=0, TRUE=1 } BIT;

// Function Declarations (gates)
BIT not_gate(BIT A);
BIT or_gate(BIT A, BIT B);
BIT and_gate(BIT A, BIT B);
BIT xor_gate(BIT A, BIT B);

// Function Declarations (circuits)
BIT rs_latch(BIT S, BIT R);
BIT d_latch(BIT D, BIT WE);
int adder(int O1, int O2,  BIT carryIn, BIT *carryOut);
BIT multiplexer(BIT A, BIT B, BIT C, BIT D, BIT S1, BIT S0);
void decoder(BIT A, BIT B, BIT *O0, BIT *O1, BIT *O2, BIT *O3);

// For multiplexer, think of S1,S0 as a two-bit binary number,
// indexing into A,B,C,D, in the order given.
// S1==0 & S0==0 refers to A
// S1==0 & S0==1 refers to B
// ...

// For decoder, think of A,B as a two-bit binary number,
// indexing into O0,O1,O2,O3 in the order given.
// S1==0 & S0==0 results in O0 being set.
// S1==0 & S0==1 results in O1 being set.
// ...
