CFLAGS = -I ./ -g

raytracer: main.cpp DriverReader.o Object.o Ray.o Camera.o Material.o
	g++ $(CFLAGS) main.cpp DriverReader.o Object.o Ray.o Camera.o Material.o -o raytracer

DriverReader.o: DriverReader.cpp DriverReader.h
	g++ $(CFLAGS) -c DriverReader.cpp

DriverReader.h: Camera.h Scene.h

Scene.h: Object.h Sphere.h Light.h

Object.o: Object.cpp Object.h
	g++ $(CFLAGS) -c Object.cpp

Object.h: Material.h

Ray.o: Ray.cpp Ray.h
	g++ $(CFLAGS) -c Ray.cpp

Ray.h: Scene.h Material.h

Camera.o: Camera.cpp Camera.h Ray.h
	g++ $(CFLAGS) -c Camera.cpp

Sphere.h: Material.h

Camera.h: Scene.h Ray.h

Light.h:

Material.o: Material.cpp Material.h
	g++ $(CFLAGS) -c Material.cpp

Material.h:

clean:
	rm *.o raytracer *~
