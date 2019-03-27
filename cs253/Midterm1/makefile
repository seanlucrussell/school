CC = g++
INC = -Wall -g -I.

QuaggaTree:	Tree.o main.o
	$(CC) Tree.o main.o -o QuaggaTree

Tree.o:	Tree.h Tree.cpp Quagga.h
	$(CC) $(INC) Tree.cpp -c

main.o:	main.cpp Quagga.h Tree.h
	$(CC) $(INC) main.cpp -c

