; Author: Fritz Sieker
;
; ------------------------------------------------------------------------
; Begin reserved section - do not change ANYTHING is this section

               .ORIG x3000
               BR Main

option         .BLKW 1          ; select routine to test
data1          .BLKW 1          ; use ONLY for testing
data2          .BLKW 1          ; use ONLY for testing

stackBase      .FILL X4000      ; initial sttack pointer

Main           LD R6,stackBase  ; initialize stack pointer
               LD R0,option     ; select routine to test
               BRZ testPrintNum ; option = 0 means test printNum

               ADD R0,R0,#-1
               BRZ testGetDigit ; option = 1 means test getDidit

               ADD R0,R0,#-1
               BRZ testDivRem   ; option = 2 means test divRem

               HALT             ; invalid option if here

testPrintNum                    ; call printNum(x, base)
               LD R0,data2
               PUSH R0          ; push base argument
               LD R0,data1
               PUSH R0          ; push value argument
               JSR printNum
               ADD R6,R6,#2     ; caller clean up 2 parameters
               NEWLN
               BR endTest

testGetDigit                    ; call getChar(val)
               LD R0,data1
               PUSH R0          ; push argument (val to convert to ASCII)
               JSR getDigit     ; 
               POP R0           ; get the corresponding character
               ADD R6,R6,#1     ; caller clean up 1 parameter
               OUT              ; print the character
	       NEWLN
               BR endTest

testDivRem                      ; call divRem(num, denom, *quotient, *remainder)
               LEA R0,data2     ; get pointer to remainder (&data2)
               PUSH R0
               LEA R0,data1     ; get pointer to quotient (&data1)
               PUSH R0
               LD R0,data2
               PUSH R0          ; push demoninator
               LD R0,data1
               PUSH R0          ; push numerator
               JSR divRem       ; call routine
               ADD R6,R6,#4     ; caller clean up 4 parameters

endTest        LEA R0,endMsg
               PUTS
theEnd         HALT             ; look at data1/data2 for quotient/remainder

                                ; useful constants
endMsg         .STRINGz "Test complete!\n"

negSign        .FILL    x2D     ; ASCII '-'
digits         .STRINGZ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ" ; up to base 36

; end reserved section

; ------------------------------------------------------------------------
; Author: Sean Russell
; ------------------------------------------------------------------------
;
; C declaration: char getDigit (int val);

getDigit        ADD R6,R6,#-1    ; callee setup
		PUSH R7
		PUSH R5
		ADD R5,R6,#0

		LDR R2,R5,#3	; code for getDigit
		LEA R3,digits
		ADD R2,R3,R2
		LDR R7,R2,#0

		STR R7,R5,#2	; callee cleanup
		POP R5
		POP R7
               	RET             ; return

; ------------------------------------------------------------------------
;
; C declaration: void divRem(int num, int denom, int*quotient, int*remainder);

divRem         	ADD R6,R6,#-1    ; callee setup
		PUSH R7
		PUSH R5
		ADD R5,R6,#0

                LDR R3,R5,#3    ; remainder pointer
		LDR R2,R5,#4	; quotient pointer
		LDR R1,R5,#5	; divisor
		LDR R0,R5,#6	; numerator
		.ZERO R4	; *quotient = 0
		STR R4,R1,#0
divRemLoop	NOT R4,R2	; while (numerator >= divisor)
		ADD R4,R4,#1
		ADD R4,R4,R3
		BRn divRemExit
		ADD R3,R4,#0	; numerator = numerator - divisor;
		LDR R4,R1,#0	; *quotient = *quotient + 1;
		ADD R4,R4,#1
		STR R4,R1,#0
		BR divRemLoop
divRemExit	STR R3,R0,#0	; *remainder = numerator

                STR R7,R5,#2	; callee cleanup
		POP R5
		POP R7
               	RET              ; return
; ------------------------------------------------------------------------
;
; C declaration: void printNum (int val, int base);

;void printNum (int x, int base) {
;  int q = 0;
;  int r = 0;
;  divRem (x,base,&q,&r);
;  if (q<=0) {
;    putchar(getDigit(r));
;    return;
;  }
;  printNum(q,base);
;  putchar(getDigit(r));
;}

printNum        ADD R6,R6,#-1    ; callee setup
		PUSH R7
		PUSH R5
		ADD R5,R6,#0

		LDR R0,R5,#3	; x
		LDR R1,R5,#4	; base

		ADD R6,R6,#-2	; allocate two local variables
		.ZERO R2
		STR R2,R5,#-1	; int q = 0;
		STR R2,R5,#-2	; int r = 0;

		ADD R2,R5,#-1	; &q
		ADD R3,R5,#-2	; &r

		PUSH R3		; divRem (x,base,&q,&r)
		PUSH R2
		PUSH R1
		PUSH R0
		JSR divRem
		ADD R6,R6,#4

		LDR R0,R5,#-1	; if (q<=0) 
		BRp recurse
		
		LDR R1,R5,#-2	; putchar(getDigit(r));
		PUSH R1
		JSR getDigit
		POP R0
		OUT
		ADD R6,R6,#1
		BR exit		; return

recurse		LDR R0,R5,#-1	; printNum(q,base);
		LDR R1,R5,#4
		PUSH R1
		PUSH R0
		JSR printNum
		ADD R6,R6,#3

		LDR R1,R5,#-2	; putchar(getDigit(r));
		PUSH R1
		JSR getDigit
		POP R0
		OUT
		ADD R6,R6,#1
		BR exit

exit		ADD R6,R6,#3	        
		STR R7,R5,#2	; callee cleanup
		POP R5
		POP R7
              	RET              ; return
; ------------------------------------------------------------------------
               .END
