# Makefile template for CS 270

# List of files
C_SRCS		= r3.c main.c
C_OBJS		= r3.o main.o
C_HEADERS	= 

OBJS		= ${C_OBJS} 
EXE	        = r3

# Compiler and loader commands and flags
CC		= c11
CC_FLAGS	= -g -Wall -c
LD_FLAGS	= -g -Wall -lm

# Compile .c files to .o files
.c.o:
	$(CC) $(CC_FLAGS) $<

# Target is the executable
default: $(OBJS)	
	$(CC) $(LD_FLAGS) $(OBJS) -o $(EXE)

# Recompile C objects if headers change
${C_OBJS}:      ${C_HEADERS}

# Clean up the directory
clean:
	rm -f *.o *~ $(EXE) 


