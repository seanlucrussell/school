; P6 Assignment
; Author: Sean Russell
; Date:   October 14, 2015
; Email:  slrussel@rams.colostate.edu
; Class:  CS270
;
; Description: Implements the arithmetic, bitwise, and shift operations

;------------------------------------------------------------------------------
; Begin reserved section: do not change ANYTHING in reserved section!

                .ORIG x3000
                BR Main

; A jump table defined as an array of addresses
Functions       .FILL intAdd         ; (option 0)
                .FILL intSub         ; (option 1)
                .FILL intMul         ; (option 2)
                .FILL binaryOr       ; (option 3)
                .FILL leftShift      ; (option 4)
                .FILL rightShift     ; (option 5)

Main            LEA R0,Functions     ; get base of jump table
                LD  R1,Option        ; get option to use, no error checking
                ADD R0,R0,R1         ; add index of array
                LDR R0,R0,#0         ; get address of function
                JSRR R0              ; call selected function
                HALT

; Parameters and return values for all functions
Option          .BLKW 1              ; which function to call
Param1          .BLKW 1              ; space to specify first parameter
Param2          .BLKW 1              ; space to specify second parameter
Result          .BLKW 1              ; space to store result

; End reserved section: do not change ANYTHING in reserved section!
;------------------------------------------------------------------------------

; You may add variables and functions after here as you see fit.

; Constants
One             .FILL #1             ; the number 1

;------------------------------------------------------------------------------
intAdd          LD R0, Param1	     ; load Param1
                LD R1, Param2        ; load Param2
		ADD R0,R0,R1         ; add Param1 and Param2
		ST R0,Result         ; store result in memory
                RET
;------------------------------------------------------------------------------
intSub          LD R0, Param1	     ; load Param1
                LD R1, Param2        ; load Param2
		NOT R1,R1	     ; invert Param2
		ADD R1,R1,1          ; increment Param2 by 1
		ADD R0,R0,R1         ; add Param1 and Param2
		ST R0,Result         ; store result in memory
                RET
;------------------------------------------------------------------------------
intMul          AND R2,R2,0	     ; clear R2
		LD R0, Param1        ; load Param1
		BRz exit	     ; if Param1 is zero go to exit code
		LD R1, Param2	     ; load Param2
		BRz exit	     ; if Param2 is zero go to exit code
loop		ADD R2,R0,R2         ; add Param1 to current total value
		ADD R1,R1,-1	     ; decrement Param2 by 1
		BRp loop	     ; if Param2 is positive go back to loop
exit		ST R2,Result	     ; store result in memory
                RET
;------------------------------------------------------------------------------
binaryOr        LD R0, Param1        ; load Param1
		LD R1, Param2	     ; load Param2
                NOT R0,R0	     ; negate Param1
		NOT R1,R1	     ; negate Param2
		AND R0,R1,R0	     ; and results of previous two operations
		NOT R0,R0	     ; negate result of previous operation
		ST R0,Result	     ; store the result
                RET
;------------------------------------------------------------------------------
leftShift       LD R0, Param1	     ; load Param1
		LD R1, Param2	     ; load Param2
lTest		BRz lExit	     ; if Param2 equals zero, exit
		ADD R0,R0,R0	     ; shift Param1 left by 1
		ADD R1,R1,-1	     ; reduce Param2 by 1
		BRnzp lTest	     ; jump back to test
lExit		ST R0,Result	     ; store result
                RET
;------------------------------------------------------------------------------
rightShift      LD R2,One	     ; set R2 to 1
		LD R3,One	     ; set R3 to 1
		AND R4,R4,0	     ; set R4 to 0
		LD R0, Param1	     ; load Param1
		LD R1, Param2	     ; load Param2
rTest1		BRz rExit1	     ;
		ADD R2,R2,R2	     ;
		ADD R1,R1,-1	     ;
		BRnzp rTest1	     ;
rExit1		AND R1,R1,0	     ;
		ADD R1,R1,15	     ;
rTest2		AND R5,R2,R0	     ;
		BRz skip	     ;
		ADD R4,R3,R4	     ;
skip		ADD R3,R3,R3	     ;
		ADD R2,R2,R2	     ;
		ADD R1,R1,-1	     ;
		BRp rTest2	     ;
		ST R4,Result	     ;
                RET
;------------------------------------------------------------------------------
                .END


