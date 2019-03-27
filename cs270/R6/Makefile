# Makefile template for CS 270 (specialized for R6) 

# List of files
C_SRCS		= main.c struct.c
C_OBJS		= main.o struct.o
C_HEADERS	= struct.h

OBJS		= ${C_OBJS} 
EXE		= R6

# Compiler and loader commands and flags
CC		= c99
CC_FLAGS	= -g -Wall -c
LD_FLAGS	= -g -Wall

# The first, and hence default, target is the executable
$(EXE): $(OBJS)
	@echo "Linking all object modules."
	$(CC) $(LD_FLAGS) $(OBJS) -o $(EXE)

# Recompile C objects if headers change
${C_OBJS}: ${C_HEADERS}

# Compile .c files to .o files
.c.o:
	@echo "Compiling each C source file separately."
	$(CC) $(CC_FLAGS) $<

# Clean up the directory
clean:
	@echo "Cleaning project directory."
	rm -f *.o *~ $(EXE)

# Package the directory
package:
	@echo "Packaging project directory."
	tar -cvf $(EXE).tar ../$(EXE)

# Define my own target
date:
	@echo "Running my commands."
	date
	uname -a
	pwd
