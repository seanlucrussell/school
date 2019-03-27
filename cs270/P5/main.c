/** @mainpage CS270 Spring 2015
 *  \htmlinclude "P5.html"
 */

/** @file: main.c
 *  @brief Driver to test functions of number_detective.c (do not modify)
 *
 * @details This program is for testing the functions of logic.c and logic.h
 * <p>
 * @author Sean Russell
 * @date   September 30, 2015
 */

// Include files
#include "logic.h"

/** Entry point of the program
 * @param   argc count of arguments, will always be at least 1
 * @param   argv array of parameters to program argv[0] is the name of
 *          the program, so additional parameters will begin at index 1
 * @details If one of the test cases fail then the program will assert.
 * @return  0 the Linux convention for success.
 */
int main(int argc, char *argv[])
{
    // check NOT gate truth table
    // A   NOT(A)
    // 0     1
    // 1     0
    printf("===== Testing not_gate =====\n");
    assert(not_gate(FALSE) == TRUE); 
    assert(not_gate(TRUE) == FALSE); 


    // check OR gate truth table
    // A B (A OR B)
    // 0 0   0
    // 0 1   1
    // 1 0   1
    // 1 1   1
    printf("===== Testing or_gate =====\n");
    assert(or_gate(FALSE,FALSE) == FALSE); 
    assert(or_gate(FALSE,TRUE) == TRUE); 
    assert(or_gate(TRUE,FALSE) == TRUE); 
    assert(or_gate(TRUE,TRUE) == TRUE); 
    

    // check AND gate truth table
    // A B (A AND B)
    // 0 0   0
    // 0 1   0
    // 1 0   0
    // 1 1   1
    printf("===== Testing and_gate =====\n");
    assert(and_gate(FALSE,FALSE) == FALSE); 
    assert(and_gate(FALSE,TRUE) == FALSE); 
    assert(and_gate(TRUE,FALSE) == FALSE); 
    assert(and_gate(TRUE,TRUE) == TRUE); 
    
    // check XOR gate truth table
    // A B (A XOR B)
    // 0 0   0
    // 0 1   1
    // 1 0   1
    // 1 1   0
    printf("===== Testing xor_gate =====\n");
    assert(xor_gate(FALSE,FALSE) == FALSE); 
    assert(xor_gate(FALSE,TRUE) == TRUE); 
    assert(xor_gate(TRUE,FALSE) == TRUE); 
    assert(xor_gate(TRUE,TRUE) == FALSE); 
    
    // check RS latch truth table
    // the order matters so that the saved_bit will be true when expected
    //  S	R	saved_bit   rs_latch(S,R)
    //  FALSE	FALSE	FALSE	    will assert in rs_latch
    //  FALSE	FALSE	TRUE	    will assert in rs_latch
    //  TRUE	FALSE	FALSE	    FALSE
    //	TRUE	TRUE	FALSE	    FALSE
    //  FALSE	TRUE	FALSE	    TRUE
    //  FALSE	TRUE	TRUE	    TRUE
    //	TRUE	TRUE	TRUE	    TRUE
    //  TRUE	FALSE	TRUE	    FALSE
    printf("===== Testing rs_latch =====\n");
    assert( rs_latch( TRUE, FALSE )==FALSE );
    assert( rs_latch( TRUE, TRUE )==FALSE );
    assert( rs_latch( FALSE, TRUE )==TRUE );
    assert( rs_latch( FALSE, TRUE )==TRUE );
    assert( rs_latch( TRUE, TRUE )==TRUE );
    assert( rs_latch( TRUE, FALSE )==FALSE );

    // check D latch truth table
    // the order matters so that the saved_bit will be true when expected
    //  D	WE	saved_bit   d_latch(D,WE)
    //  TRUE	FALSE	FALSE	    FALSE
    //  TRUE	TRUE	TRUE	    TRUE
    //  FALSE	TRUE	FALSE	    FALSE
    //	FALSE	FALSE	FALSE	    FALSE
    printf("===== Testing d_latch =====\n");
    assert( d_latch( TRUE, FALSE )==FALSE );
    assert( d_latch( TRUE, TRUE )==TRUE );
    assert( d_latch( FALSE, TRUE )==FALSE );
    assert( d_latch( TRUE, TRUE )==TRUE );
    assert( d_latch( FALSE, FALSE )==TRUE );

    // check multiplexer gate truth table
    // A	B	C	D	S1	S0	Output
    // 0	0	0	0	0	0	0
    // 1	1	1	1	1	1	1
    // 1	0	1	0	1	0	1
    // 0	1	0	1	0	1	1
    printf("===== Testing multiplexer =====\n");
    assert( multiplexer( FALSE, FALSE, FALSE, FALSE, FALSE, FALSE )==FALSE);
    assert( multiplexer( TRUE, TRUE, TRUE, TRUE, TRUE, TRUE )==TRUE);
    assert( multiplexer( TRUE, FALSE, TRUE, FALSE, TRUE, FALSE )==TRUE);
    assert( multiplexer( FALSE, TRUE, FALSE, TRUE, FALSE, TRUE )==TRUE);

    // check decoder gate truth table
    // A	B  |	W	X	Y	Z
    // 0	0  |	1	0	0	0
    // 1	1  |	0	1	0	0
    // 1	0  |	0	0	1	0
    // 0	1  |	0	0	0	1
    printf("===== Testing decoder =====\n");
    BIT w = FALSE;
    BIT x = FALSE;
    BIT y = FALSE;
    BIT z = FALSE;
    decoder(FALSE,FALSE,&w,&x,&y,&z);
    assert( w==TRUE && x==FALSE && y==FALSE && z==FALSE);
    decoder(FALSE,TRUE,&w,&x,&y,&z);
    assert( w==FALSE && x==TRUE && y==FALSE && z==FALSE);
    decoder(TRUE,FALSE,&w,&x,&y,&z);
    assert( w==FALSE && x==FALSE && y==TRUE && z==FALSE);
    decoder(TRUE,TRUE,&w,&x,&y,&z);
    assert( w==FALSE && x==FALSE && y==FALSE && z==TRUE);

    // check ADDER truth table
    // O1	O2	Carryin	Output Sum	Output Carryout
    // 0101	0101	0	1010		0
    // 0101	0101	1	1011		0
    // 0001	0111	1	1001		0
    // 0111	0111	0	1110		0
    // 1101	1101	1	1100		1
    printf("===== Testing adder =====\n");
    BIT carryOut = 0;
    int sum = 0;
    sum = adder(0b0101, 0b0101, 0, &carryOut);
    assert (sum==0b1010 && carryOut==0);
    carryOut = 0;
    sum = 0;
    sum = adder(0b0101, 0b0101, 1, &carryOut);
    assert (sum==0b1011 && carryOut==0);
    carryOut = 0;
    sum = 0;
    sum = adder(0b0001, 0b0111, 1, &carryOut);
    assert (sum==0b1001 && carryOut==0);
    carryOut = 0;
    sum = 0;
    sum = adder(0b0111, 0b0111, 0, &carryOut);
    assert (sum==0b1110 && carryOut==0);
    carryOut = 0;
    sum = 0;
    sum = adder(0b1101, 0b1101, 1, &carryOut);
    assert (sum==0b1100 && carryOut==1);


    return 0;
}
