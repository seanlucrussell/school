; Recitation 7
; Author: Sean Russell
; Date:   October 6 2015
; Email:  slrussel@rams.colostate.edu
; Class:  CS270
;
; Description: Implements integer (16-bit) addition and subtraction, and multiplication
;
;------------------------------------------------------------------------------
; Begin reserved section: do not change ANYTHING in reserved section!

                .ORIG x3000
                BR Main

; A jump table defined as an array of addresses
Functions       .FILL IntAdd         ; address of add routine      (option 0)
                .FILL IntSub         ; address of subtract routine (option 1)
                .FILL IntMul         ; address of multiply routine (option 2)

Main            LEA R0,Functions     ; get base of jump table
                LD  R1,Option        ; get option to use, no error checking
                ADD R0,R0,R1         ; add index of array
                LDR R0,R0,0          ; get address of function
                JSRR R0              ; call selected function
                HALT

; Parameters and return values for all functions
Option          .BLKW 1              ; which function to call
Param1          .BLKW 1              ; space to specify first parameter
Param2          .BLKW 1              ; space to specify second parameter
Result          .BLKW 1              ; space to store result

; End reserved section: do not change ANYTHING in reserved section!
;------------------------------------------------------------------------------
IntAdd          LD R0, Param1	     ; load Param1
                LD R1, Param2        ; load Param2
		ADD R0,R0,R1         ; add Param1 and Param2
		ST R0,Result         ; store result in memory
		RET
;------------------------------------------------------------------------------
IntSub          LD R0, Param1	     ; load Param1
                LD R1, Param2        ; load Param2
		NOT R1,R1	     ; invert Param2
		ADD R1,R1,1          ; increment Param2 by 1
		ADD R0,R0,R1         ; add Param1 and Param2
		ST R0,Result         ; store result in memory
                RET

;------------------------------------------------------------------------------
IntMul          AND R2,R2,0	     ; clear R2
		LD R0, Param1        ; load Param1
		BRz Exit	     ; if Param1 is zero go to exit code
		LD R1, Param2	     ; load Param2
		BRz Exit	     ; if Param2 is zero go to exit code
Loop		ADD R2,R0,R2         ; add Param1 to current total value
		ADD R1,R1,-1	     ; decrement Param2 by 1
		BRp Loop	     ; if Param2 is positive go back to loop
Exit		ST R2,Result	     ; store result in memory
                RET

;------------------------------------------------------------------------------
               .END

