        .ORIG x3000
lOne	ADD R1,R2,R3
	ADD R7,R2,#12
	AND R3,R5,R3
	AND R5,R5,xf
        BRz lOne
	JMP R6
	JSR lOne
	JSRR R4
	LD R2,dOne
	LDI R6,dTwo
	LDR R4,R4,3
	LEA R0, dTwo
	NOT R5,R5
	RET
	RTI
	ST R4,dOne
	STI R1,dTwo
	STR R3,R1,6
	TRAP x1A

dOne	.BLKW 4
dTwo	.FILL xABCD

	GETC
	OUT
	PUTS
	IN
	PUTSP
	HALT
	GETS
	
        .END

