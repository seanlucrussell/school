# Makefile template for CS 270

# List of files
C_SRCS          = Q1.c Q1Test.c
C_OBJS          = Q1.o Q1Test.o
C_HEADERS       = Q1.h

# Executable name
EXE             = Q1

# Compiler and loader commands and flags
GCC             = gcc
GCC_FLAGS       = -g -std=c11 -D_GNU_SOURCE -Wall -c
LD_FLAGS        = -g -std=c11 -D_GNU_SOURCE -Wall

# Compile C source to object files
.c.o:
	$(GCC) $(GCC_FLAGS) $<

# Target is the executable
default: $(C_OBJS)
	$(GCC) $(LD_FLAGS) $(C_OBJS) -o $(EXE)

# Recompile C objects if headers change
${C_OBJS}:      ${C_HEADERS}

# Clean up the directory
clean:
	rm -f *.o *~ $(EXE)

# Package files
package: Q1.c Q1.h
	tar -cvf Q1.tar Q1.c Q1.h

                                    
