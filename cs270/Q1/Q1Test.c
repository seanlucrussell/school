// Q1 Programming Quiz - Test Program
// Author: Chris Wilcox
// Date:   12/7/2015
// Class:  CS270
// Email:  wilcox@cs.colostate.edu

// Include files
#include "Q1.h"

// Printing function
void print(Student *student) {
    printf("%s (%d): %.2f\n", student->name, student->id, student->gpa);
}

// Main entry point with test code
int main(int argc, char *argv[])
{
    // Simple test code
    readFile(argv[1]);
    insertStudent ("Wilcox", 121212, 4.0);

    printf("Print list of students:\n");
    iterate(&print);

    removeStudent("Anderson");
    removeStudent("Davis");
    removeStudent("Green");
    removeStudent("Hall");
    removeStudent("Bogus"); // should be ignored

    printf("Print remaining students:\n");
    iterate(&print);

    terminate();
    return 0;
}


