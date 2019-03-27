#Makefile template for CS 270

# List of files
C_SRCS          = printnum.c testPrint.c
C_OBJS          = printnum.o testPrint.o
C_HEADERS       = printnum.h

OBJS            = ${C_OBJS}
EXE             = testPrint

# Compiler and loader commands and flags
GCC             = gcc
GCC_FLAGS       = -g -std=c99 -Wall -c
LD_FLAGS        = -g -std=c99 -Wall

# Compile .c files to .o files
.c.o:
	$(GCC) $(GCC_FLAGS) $<

# Target is the executable
$(EXE): $(OBJS)
	$(GCC) $(LD_FLAGS) $(OBJS) -o $(EXE)

# Recompile C objects if headers change
${C_OBJS}:      ${C_HEADERS}

# Clean up the directory
clean:
	rm -f *.o *~ $(EXE)
                                    
