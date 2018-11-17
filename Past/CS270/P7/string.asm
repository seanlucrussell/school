; Begin reserved section: do not change ANYTHING in reserved section!
;------------------------------------------------------------------------------
; Author: Fritz Sieker
;
; Description: Tests the implementation of a simple string library and I/O
;
; The ONLY exception to this is that you MAY change the .FILL values for
; Option, Value1 and Value2. this makes it easy to initialze the values in the
; program, so that you do not need to continually re-enter them. This
; makes debugging easier as you need only change your code and re-assemble.
; Your test value(s) will already be set.

        .ORIG x3000
        BR Main
Functions
        .FILL Test_strlen       ; (option 0)
        .FILL Test_strcpy       ; (option 1)
        .FILL Test_strcat       ; (option 2)
        .FILL Test_strcmp       ; (option 3)
        .FILL Test_printCC      ; (option 4)
        .FILL Test_pack         ; (option 5)
        .FILL Test_unpack       ; (option 6)
        .FILL Test_getsp        ; (option 7)
        .FILL Test_strunpack    ; (option 8)

; Parameters and return values for all functions
Option  .FILL 8                 ; which function to call
String1 .FILL x4000             ; default location of 1st string
String2 .FILL x4100             ; default location of 2nd string
Result  .BLKW 1                 ; space to store result
Value1  .BLKW 1                 ; used for testing pack/unpack
Value2  .BLKW 1                 ; used for testing pack/unpack
prompt  .STRINGZ "Enter packed string> "

Main    LEA R0,Functions        ; get base of jump table
        LD  R1,Option           ; get option to use, no error checking
        ADD R0,R0,R1            ; add index of array
        LDR R0,R0,#0            ; get address to test
        JMP R0                  ; execute test

Test_strlen 
        LD R0,String1           ; load string pointer
        GETS                    ; get string
        LD R0,String1           ; load string pointer
        JSR strlen              ; calculate length
        ST R0,Result            ; save result
        HALT                    ; done - examine memory[Result]

Test_strcpy 
        LD R0,String1           ; load string pointer
        GETS                    ; get string
        LD R0,String2           ; R0 is dest
        LD R1,String1           ; R1 is src
        JSR strcpy              ; copy string
        PUTS                    ; print result of strcpy
        NEWLN                   ; add newline
        HALT                    ; done - examine output

Test_strcat 
        LD R0,String1           ; load first pointer
        GETS                    ; get first string
        LD R0,String2           ; load second pointer
        GETS                    ; get second string
        LD R0,String1           ; dest is first string
        LD R1,String2           ; src is second string
        JSR strcat              ; concatenate string
        PUTS                    ; print result of strcat
        NEWLN                   ; add newline
        HALT                    ; done - examine output

Test_strcmp 
        LD R0,String1           ; load first pointer
        GETS                    ; get first string
        LD R0,String2           ; load second pointer
        GETS                    ; get second string
        LD R0,String1           ; dest is first string
        LD R1,String2           ; src is second string
        JSR strcmp              ; compare strings
        JSR printCC             ; print result of strcmp
        HALT                    ; done - examine output

Test_printCC    
        LD R0,Value1            ; get the test value
        .ZERO R1                ; reset condition codes
        JSR printCC             ; print condition code
        HALT                    ; done - examine output

Test_pack   
        LD R0,Value1            ; load first character
        LD R1,Value2            ; load second character
        JSR pack                ; pack characters
        ST R0,Result            ; save packed result
        HALT                    ; done - examine Result

Test_unpack 
        LD R0,Value1            ; value to unpack
        JSR unpack              ; test unpack
        ST R0,Value1            ; save upper 8 bits
        ST R1,Value2            ; save lower 8 bits
        HALT                    ; done - examine Value1 and Value2

Test_getsp  
        LEA R0,prompt           ; load prompt
        PUTS                    ; print prompt
        LD R0,String1           ; R0 is dest
        JSR getsp               ; test getsp
        PUTSP                   ; print it back out
        NEWLN                   ; add newline
        HALT                    ; done - examine output and String1

Test_strunpack  
        LD R0,String1           ; load string address
        JSR getsp               ; get a packed string
        LD R0,String2           ; R0 is dest
        LD R1,String1           ; R1 is src
        JSR strunpack           ; test strunpack
        PUTS                    ; print it back out
        NEWLN                   ; add newline
        HALT                    ; done - examine output and String1

;------------------------------------------------------------------------------
; End of reserved section
;
; Author: Sean Russell
; Implement strcmp, and printCC for part A

			    ; size_t strlen(char *s)
			    ; on entry R0 contains pointer to src
			    ; on exit R1 contains length of src
strlen  AND R1,R1,0	    ; clear R1 for counter
lenloop	LDR R2,R0,0	    ; load character into R2
	BRz lenexit	    ; exit loop if null character is found
	ADD R1,R1,1	    ; increment counter
	ADD R0,R0,1	    ; increment string pointer
	BRnzp lenloop	    ; go back to start of loop
lenexit	AND R0,R0,0	    ; set R0 to R1
	ADD R0,R0,R1
        RET

;------------------------------------------------------------------------------

                      	    ; char *strcpy(char *dest, char *src)
                            ; on entry R0 contains dest
                            ;          R1 contains src
                            ; on exit  R0 contains dest
cpypntr	.BLKW 1		    ; space to store pointer to dest
strcpy	ST R0,cpypntr	    ; store pointer to dest
cpyloop	LDR R3,R1,0	    ; load character from src into R3
	STR R3,R0,0	    ; store character from R3 to dest
	ADD R4,R3,0
	BRz cpyexit	    ; if character is null exit loop
	ADD R1,R1,1	    ; increment src pointer
	ADD R0,R0,1	    ; increment dest pointer
	BRnzp cpyloop	    ; go back to start of loop
cpyexit	LD R0,cpypntr	
        RET

;------------------------------------------------------------------------------

                            ; char *strcat(char *dest, char *src)
                            ; on entry R0 contains dest
                            ;          R1 contains src
                            ; on exit  R0 contains dest
catpntr	.BLKW 1
catcntr	.BLKW 1
strcat	ST R0,catpntr	    ; store pointer to dest
	ST R7,catcntr
catloop	LDR R2,R0,0	    ; load character into R2
	BRz catexit	    ; exit loop if null character is found
	ADD R0,R0,1	    ; increment string pointer
	BRnzp catloop	    ; go back to start of loop
catexit	JSR strcpy
	LD R0,catpntr
	LD R7,catcntr
        RET

;------------------------------------------------------------------------------

                            ; int strcmp(char *s1, char *s2)
                            ; on entry R0 contains s1
                            ;          R1 contains s2
                            ; on exit R0 contains negative number, if s1 < s2
                            ; a positive number if s1 > s2, else a zero
strcmp	.ZERO R5	    ; clear R5 for storing result
cmploop	LDR R2,R0,0	    ; load character pointed to by s1
	LDR R3,R1,0	    ; load character pointed to by s2
	NOT R2,R2	    ; negate R2
	ADD R2,R2,1	    ; negate R2
	ADD R3,R3,R2	    ; adding R3 and negated R2
	BRn ncmp
	BRp pcmp
	BRz zcmp
ncmp	ADD R5,R5,-1
	BRnzp cmpexit
pcmp	ADD R5,R5,1
	BRnzp cmpexit
zcmp	LDR R2,R0,0
	BRz cmpexit
	ADD R0,R0,1
	ADD R1,R1,0
	BRnzp cmploop
cmpexit	.ZERO R0
	ADD R0,R0,R5
        RET

;------------------------------------------------------------------------------
	                    ; on entry R0 cotains value
                            ; on exit "NEGATIVE/ZERO/POSITIVE" printed
                            ; followed by newline
neg	.STRINGZ "NEGATIVE" ; memory for storing output
zero	.STRINGZ "ZERO"
pos	.STRINGZ "POSITIVE"
pval	.BLKW 1		    ; memory for storing original value
pret	.BLKW 1		    ; memory for storing return value
printCC	ST R7,pret	    ; save return location
	ADD R0,R0,0	    ; set condition code of R0
	ST R0,pval	    ; store original value
	BRn pneg	    ; branch if negative
	BRz pzero	    ; branch if zero
	BRp ppos	    ; branch if positive
pneg	LEA R0,neg	    ; load pointer to neg
	BRnzp pexit
pzero	LEA R0,zero	    ; load pointer to zero
	BRnzp pexit
ppos	LEA R0,pos	    ; load pointer to pos
pexit	PUTS		    ; output string
	NEWLN		    ; output newline
	LD R0,pval	    ; restore original value
	LD R7,pret	    ; restore return location
        RET


;------------------------------------------------------------------------------
                            ; on entry R0 contains 1st value (upper bits)
                            ;          R1 contains 2nd value (lower bits)
                            ; on exit R0 result of pack
mask 	.FILL x00FF
pack	AND R2,R2,#0
	ADD R2,R2,#8
packl	ADD R0,R0,R0
	ADD R2,R2,-1
	BRp packl
	LD R2,mask
	AND R1,R1,R2
	ADD R0,R0,R1
        RET

;------------------------------------------------------------------------------
                            ; on entry R0 contains a value
                            ; on exit R0 contains upper 8 bits
                            ;         R1 contains lower 8 bits
one	.FILL x0001
eight	.FILL x0008
unpack	LD R2,mask
	AND R1,R0,R2
	LD R2,eight
	LD R3,one
uplo	ADD R3,R3,R3
	ADD R2,R2,-1
	BRp uplo
	LD R2,eight
	LD R4,one
	.ZERO R6
uplt	AND R5,R3,R0
	BRz skip
	ADD R6,R6,R4
skip	ADD R4,R4,R4
	ADD R3,R3,R3
	ADD R2,R2,-1
	BRp uplt
	ADD R0,R6,#0
        RET

;------------------------------------------------------------------------------
                            ; on entry R0 contains dest
                            ; on exit R0 constains
                            ; dest filled with packed string
                            ; be sure and add null char
gret	.BLKW 1
gdest	.BLKW 1
gpoint	.BLKW 1
thirt	.FILL xFFF3
getsp	ST R7,gret
	ST R0,gdest
	ADD R3,R0,#0
	.ZERO R0
	.ZERO R1

gloop	GETC
	OUT
	LD R4,thirt
	ADD R2,R0,R4
	BRz gexit
	AND R2,R1,-1
	BRnp sttwo
	ADD R1,R0,#0
	BRnzp stend
sttwo	ST R3,gpoint		;character is already stored in R0
	JSR pack
	LD R3,gpoint
	STR R0,R3,#0
	.ZERO R0
	.ZERO R1
	ADD R3,R3,#1
stend	BRnzp gloop
gexit	ADD R4,R1,#0
	BRz gexitt
	STR R1,R3,#0
gexitt	ADD R3,R3,#1
	.ZERO R2
	STR R2,R3,#0
	LD R0,gdest
	LD R7,gret
        RET


;------------------------------------------------------------------------------
                            ; char *strunpack(char *dest, char *src)
                            ; on entry R0 contains dest
                            ;          R1 contains src
                            ; on exit  R0 contains dest
unret	.BLKW 1
undest	.BLKW 1
unsrc	.BLKW 1
undpntr	.BLKW 1
strunpack
	ST R7,unret
	ST R0,undest
	ADD R2,R1,#0
	ADD R3,R0,#0

unloop	LDR R0,R2,#0
	ST R2,unsrc
	ST R3,undpntr
	JSR unpack
	LD R2,unsrc
	LD R3,undpntr

	STR R1,R3,#0
	ADD R3,R3,#1

	ADD R0,R0,#0
	BRz unexit

	STR R0,R3,#0
	ADD R3,R3,#1
	
	ADD R1,R1,#0
	BRz unexit

	ADD R2,R2,#1
	BRnzp unloop

unexit	.ZERO R0
	STR R0,R3,#0
	LD R0,undest
	LD R7,unret
        RET

;------------------------------------------------------------------------------
                .END

