// P1 Assignment
// Author: Sean Russell
// Date:   8/25/2015
// Class:  CS270
// Email:  slrussel@rams.colostate.edu

// Include files
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

bool computeCircle(double radius, double *area)
{
    // Compute volume
    double result = (3.141593 * radius * radius);

    // Dereference pointer
    *area = result;

    return true;
}

bool computeTriangle(double side, double *area)
{
    // Compute volume
    double result = (0.433013 * side * side);

    // Dereference pointer
    *area = result;

    return true;
}

bool computeSquare(double side, double *area)
{
    // Compute volume
    double result = (side * side);

    // Dereference pointer
    *area = result;

    return true;
}

bool computePentagon(double side, double *area)
{
    // Compute volume
    double result = (1.720477 * side * side);

    // Dereference pointer
    *area = result;

    return true;
}

bool computeHexagon(double side, double *area)
{
    // Compute volume
    double result = (2.598076 * side * side);

    // Dereference pointer
    *area = result;

    return true;
}

int main(int argc, char *argv[])
{
    // Check number of arguments
    if (argc != 6)
        printf("usage P1 <double>\n");

    // Parse arguments
    double radius = atof(argv[1]);
    double tSide = atof(argv[2]);
    double sSide = atof(argv[3]);
    double pSide = atof(argv[4]);
    double hSide = atof(argv[5]);
    
    // Local variable
    double areaC;
    double areaT;
    double areaS;
    double areaP;
    double areaH;

    // Call function
    computeCircle(radius, &areaC);
    computeTriangle(tSide, &areaT);
    computeSquare(sSide, &areaS);
    computePentagon(pSide, &areaP);
    computeHexagon(hSide, &areaH);
    
    // Print volume
    printf("CIRCLE, radius = %.5f, area = %.5f.\n", radius, areaC);
    printf("TRIANGLE, length = %.5f, area = %.5f.\n", tSide, areaT);
    printf("SQUARE, length = %.5f, area = %.5f.\n", sSide, areaS);
    printf("PENTAGON, length = %.5f, area = %.5f.\n", pSide, areaP);
    printf("HEXAGON, length = %.5f, area = %.5f.\n", hSide, areaH);
}
/*
Write five functions, each of which accepts a parameter of type double, a pointer to a double, and returns a boolean.
The second function is computeTriangle, which computes the area of an equilateral triangle, using 0.433013 * side * side;
The third function is computeSquare, which computes the area of a square, using side * side;
The fourth function is computePentagon, which computes the area of a regular pentagon, using 1.720477 * side * side;
The fifth function is computeHexagon, which computes the area of a regular hexagon, using 2.598076 * side * side;
I realize that the floating-point values given above are only approximations. Live with it. Don’t improve them.
In the main entry point call the five functions in order.

    Convert argv[2] to a double, call computeTriangle, and print the area of the triangle.
    Convert argv[3] to a double, call computeSquare, and print the area of the square.
    Convert argv[4] to a double, call computePentagon, and print the area of the pentagon.
    Convert argv[5] to a double, call computeHexagon, and print the area of the hexagon. 
*/