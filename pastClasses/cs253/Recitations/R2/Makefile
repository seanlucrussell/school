OBJS = Complex.o main.o

Complex: ${OBJS}
	g++ ${OBJS} -o Complex
Complex.o: Complex.cpp Complex.h
	g++ -c -I. Complex.cpp
main.o: main.cpp Complex.h
	g++ -c -I. main.cpp
tar:
	tar -cvf Complex.tar Complex.cpp Complex.h main.cpp	
clean:
	rm -f ${OBJS} Complex

