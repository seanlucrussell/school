; Recitation 8
; Author: Sean Russell
; Date:   October 13 2015
; Email:  slrussel@rams.colostate.edu
; Class:  CS270
; Description: Mirrors least significant byte to most significant
;--------------------------------------------------------------------------
; Begin reserved section: do not change ANYTHING in reserved section!

                .ORIG x3000

                JSR mirror           ; call function
                HALT

; Parameter and return value
Param           .BLKW 1              ; space to specify parameter
Result          .BLKW 1              ; space to store result

; Constants
One             .FILL #1             ; the number 1       
Eight           .FILL #8             ; the number 8
Mask            .FILL x00ff          ; mask top bits

; End reserved section: do not change ANYTHING in reserved section!
;--------------------------------------------------------------------------
mirror                               ; Mirrors bits 7:0 to 15:8
                                     ; ~20 lines of assembly code
 
                LD R0,Param          ; load pattern
                LD R1,Param	     ; works
		LD R2,Mask	     ; works
		AND R1,R1,R2	     ; works
		LD R2,One	     ; set R2 to 1
		LD R3,One	     ; set R3 to 1
		LD R4,Eight	     ; set R4 to 8
Loop1		ADD R3,R3,R3
		ADD R4,R4,-1
		BRp Loop1
		LD R4,Eight	     ; reset R4 to 8
Loop2		AND R5,R2,R1
		BRz CondEnd
		ADD R1,R1,R3
CondEnd		ADD R2,R2,R2
		ADD R3,R3,R3
		ADD R4,R4,-1
		BRp Loop2
                ST R1,Result         ; store result
                RET
;--------------------------------------------------------------------------
               .END

