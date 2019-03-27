CFLAGS = -I ./ -g -O2

raytracer: main.cpp DriverReader.o Model.o Ray.o Camera.o Material.o
	g++ $(CFLAGS) main.cpp DriverReader.o Model.o Ray.o Camera.o Material.o -o raytracer

DriverReader.o: DriverReader.cpp DriverReader.h
	g++ $(CFLAGS) -c DriverReader.cpp

DriverReader.h: Camera.h Scene.h

Scene.h: Model.h Sphere.h Light.h

Model.o: Model.cpp Model.h
	g++ $(CFLAGS) -c Model.cpp

Model.h: Material.h

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
