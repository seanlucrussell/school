// File:        pointers.c
// Description: Program to teach C pointers
// Author:      Chris Wilcox
// Date:        9/29/2010

// Include files
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>

// Function prototypes
void exercise1();
void exercise2();
void exercise3();
void exercise4();
void exercise5();

// Global data
int globalInteger;
float globalFloat;
char globalChar;
char *globalString;
int globalArray[4];
int *globalPointer;

// Function:     main
// Description:  entry point  
int main(int argc, char *argv[])
{
    bool bContinue = true;
    while (bContinue)
    {
        // Display menu
        printf("\n");
        printf("1) Exercise 1, Pointer Basics\n"); 
        printf("2) Exercise 2, Pointer Arguments\n"); 
        printf("3) Exercise 3, Arrays and Pointers\n"); 
        printf("4) Exercise 4, Strings and Pointers\n"); 
        printf("5) Exercise 5, Static versus Dynamic\n"); 
        printf("Enter Number: ");

        // Get selection
        int iSelection;
        scanf("%d", &iSelection);
        printf("\n");
    
        // Run exercise
        switch (iSelection)
        {
            case 1: exercise1(); break;
            case 2: exercise2(); break;
            case 3: exercise3(); break;
            case 4: exercise4(); break;
            case 5: exercise5(); break;
            default: bContinue = false;
        }
    }

    return 0;
}

// Exercise 1, Pointer Basics
void exercise1()
{
    printf("Exercise 1\n");

    // Variables
    int localInteger = 12345678;
    float localFloat = 0.113355;
    char localChar   = '&';

    // Pointers
    int *pInteger = &localInteger;
    float *pFloat = &localFloat;
    char *pChar = &localChar;

    // Print variables and pointers
    printf("localInteger: value = %d, address = %p\n", localInteger, pInteger); 
    printf("localFloat:   value = %f, address = %p\n", localFloat, pFloat); 
    printf("localChar:    value = %c, address = %p\n", localChar, pChar);
    
    // Print the pointer and the address of the variable
    printf("pInteger value = %p: localInteger address = %p\n",pInteger,&localInteger);
    printf("pFloat value = %p: localFloat address = %p\n",pFloat,&localFloat);
    printf("pChar value = %p: localChar address = %p\n",pChar,&localChar);
  
    // Use the pointer to change the value of each variable
    *pInteger = 87654321;
    *pFloat = 0.553311;
    *pChar = '$';
    
 
    // Print variables and pointers
    printf("localInteger: value = %d, address = %p\n", localInteger, pInteger); 
    printf("localFloat:   value = %f, address = %p\n", localFloat, pFloat); 
    printf("localChar:    value = %c, address = %p\n", localChar, pChar);
}

// Exercise 2, Pointer Arguments
void exercise2()
{
    printf("Exercise 2\n");

    void passByValue(int i, float f, char c)
    {
        i = 87654321; 
        f = 0.224466; 
        c = '#';

        printf("passbyValue: i = %d\n", i); 
        printf("passbyValue: f = %f\n", f); 
        printf("passbyValue: c = %c\n", c); 
    };

    void passByReference(int *i, float *f, char *c)
    {
        *i = 87654321; 
        *f = 0.224466; 
        *c = '#';

        printf("passbyReference: i = %d\n", *i); 
        printf("passbyReference: f = %f\n", *f); 
        printf("passbyReference: c = %c\n", *c); 
    };

    // Variables
    int localInteger = 12345678;
    float localFloat = 0.113355;
    char localChar   = '&';

    // Print the local variables
    printf("localInteger: value = %d\n", localInteger); 
    printf("localFloat:   value = %f\n", localFloat);
    printf("localChar:    value = %c\n", localChar);

    // Call passByValue with locals
    passByValue(localInteger,localFloat,localChar);

    // Print the local variables
    printf("localInteger: value = %d\n", localInteger); 
    printf("localFloat:   value = %f\n", localFloat);
    printf("localChar:    value = %c\n", localChar);

    // Call passByReference with addresses of locals
    passByReference(&localInteger,&localFloat,&localChar);

   
    // Print the local variables
    printf("localInteger: value = %d\n", localInteger); 
    printf("localFloat:   value = %f\n", localFloat);
    printf("localChar:    value = %c\n", localChar);
}

// Exercise 3, Arrays and Pointers
void exercise3()
{
    printf("Exercise 3\n");

    // Static allocation
    int intArray[4];
    intArray[0] = 1234;
    intArray[1] = 2345;
    intArray[2] = 3456;
    intArray[3] = 4567;

    // Dynamic allocation
    int *intPointer = (int *)malloc(4*sizeof(int));
    *(intPointer+0) = 6543;
    *(intPointer+1) = 5432;
    *(intPointer+2) = 4321;
    *(intPointer+3) = 3210;

    // Access and print static array, using pointer access, e.g. *(intArray+0) 
    printf("*(intArray+0): %d\n",*(intArray+0));
    printf("*(intArray+1): %d\n",*(intArray+1));
    printf("*(intArray+2): %d\n",*(intArray+2));
    printf("*(intArray+3): %d\n",*(intArray+3));

    // Access and print dynamic array, using array notation, e.g. intPointer[0]
    printf("intPointer[0]: %d\n",intPointer[0]);
    printf("intPointer[1]: %d\n",intPointer[1]);
    printf("intPointer[2]: %d\n",intPointer[2]);
    printf("intPointer[3]: %d\n",intPointer[3]);

    // Free allocated memory
    free(intPointer);
}

// Exercise 4, Strings and Pointers
void exercise4()
{
    printf("Exercise 4\n");

    char *string0   = "String0";
    char string1[8] = "String1"; // don't forget the null termination
    char *string2   = (char *)malloc(8*sizeof(char));
    strcpy(string2, "String2");
    
    // Add code to print out these strings using %s and the name
    printf("string0 = %s\n",string0);
    printf("string1 = %s\n",string1);
    printf("string2 = %s\n",string2);


    // Add code to print out the sixth character using %c and array access, e.g. string1[6]
    printf("string0[6] = %c\n",string0[6]);
    printf("string1[6] = %c\n",string1[6]);
    printf("string2[6] = %c\n",string2[6]);
    
    free(string2);
}

// Exercise 5, Static versus Dynamic
void exercise5()
{
    printf("Exercise 5\n");

    // Static allocation
    int intArray[4];
    float intFloat[4];
    char intChar[4];

    // Dynamic allocation
    int *intPointer = (int *)malloc(4*sizeof(int));
    float *floatPointer = (float *)malloc(4*sizeof(float));
    char *charPointer = (char *)malloc(4*sizeof(char));
    
    // Add code to display pointers to the static arrays
    printf("Address of intArray[0] = %p\n", (intArray));
    printf("Address of intArray[2] = %p\n", (intArray+2));
    printf("Address of intArray[2] = %p\n", &intArray[2]);

    printf("Address of intFloat = %p\n", (intFloat));
    printf("Address of intChar = %p\n", (intChar));
            
    // Add code to display pointers to the dynamic arrays
    printf("intPointer = %p\n", intPointer);
    printf("floatPointer = %p\n", floatPointer);
    printf("charPointer = %p\n", charPointer);

    free(intPointer);
    free(floatPointer);
    free(charPointer);

}
