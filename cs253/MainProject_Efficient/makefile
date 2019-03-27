#Macros
OBJS = main.o Pose.o PoseSequence.o PoseDisplay.o Point3D.o
CC = g++
DEBUG = -g
CFLAGS = -Wall -c $(DEBUG) -std=c++11 -I. -lX11 -O2
LFLAGS = -Wall $(DEBUG) -std=c++11 -I. -lX11
TARGET = PA10

all: $(OBJS)
	$(CC) $(LFLAGS) $(OBJS) -o $(TARGET)
main.o: main.cpp PoseDisplay.h PoseSequence.h
	$(CC) $(CFLAGS) main.cpp
Pose.o: Pose.cpp Pose.h PoseDisplay.h
	$(CC) $(CFLAGS) Pose.cpp
PoseSequence.o: PoseSequence.cpp PoseSequence.h PoseDisplay.h Pose.h
	$(CC) $(CFLAGS) PoseSequence.cpp
PoseDisplay.o: PoseDisplay.cpp PoseDisplay.h
	$(CC) $(CFLAGS) PoseDisplay.cpp
Point3D.o: Point3D.cpp Point3D.h
	$(CC) $(CFLAGS) Point3D.cpp

clean:
	rm $(OBJS) $(TARGET)
tar:
	tar -cvf $(TARGET).tar *.cpp *.h makefile
