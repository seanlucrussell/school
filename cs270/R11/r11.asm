;; File:        r11.asm
;; Description: Assembly code for recitation 11, cs270
;; Author:      Sean Russell
;; Date:        11/3/2015

          .ORIG   x3000

;; Main program
MAIN       LD     R6, STACK          ; Initialize stack pointer
           LD     R5, STACK          ; Initialize frame pointer

           LD R0,PARAM0              ; Load parameters
	   LD R1,PARAM1
	   LD R2,PARAM2
           PUSH R2                   ; Push parameters
	   PUSH R1
	   PUSH R0
           JSR COMPUTE               ; Call function

           POP R0                    ; Pop result    
           
           ADD R6,R6,#3              ; Clean up parameters

           ST R0,RESULT              ; Store result     

           HALT                      ; Program complete

;; Compute function definition
;; int Compute(int i, int j, int k)
;; {
;;    return (i + j - k);
;; }
COMPUTE     ;; stack entry
            ADD   R6,R6,#-1          ; Allocate return value
            PUSH  R7                 ; Save return address
            PUSH  R5                 ; Save frame pointer
            ADD   R5,R6,#0           ; Setup frame pointer

            ;; function body
            LDR   R2,R5,#3           ; Load parameter 0
            LDR   R3,R5,#4           ; Load parameter 1
            LDR   R4,R5,#5           ; Load parameter 2
            NOT   R4,R4              ; 2's complement
            ADD   R4,R4,#1
            ADD   R7,R3,R2           ; Add parameter 0 and 1
            ADD   R7,R7,R4           ; Subtract parameter 2

            ;; stack exit
            STR   R7,R5,#2           ; Store return value
            POP   R5                 ; Restore frame pointer
            POP   R7                 ; Restore return address
            RET                      ; Return

;; Data declarations
STACK     .FILL    x4000
PARAM0    .FILL    x1234
PARAM1    .FILL    x2345
PARAM2    .FILL    x3456
RESULT    .BLKW    1

          .END

