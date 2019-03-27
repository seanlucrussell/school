// Q1 Programming Quiz - Header file
// Author: ?????
// Date:   ?????
// Class:  CS270
// Email:  ?????

// Include files
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Data structure

// ***** Create and typedef Student structure
typedef struct Student{
  struct Student* next;
  char* name;
  int id;
  float gpa;
}Student;

// Iterate function
typedef void (*iterate_function)(Student *student);

// Functions

void readFile(char *filename);
void insertStudent(char *name, int id, float gpa);
void removeStudent(char *name);
void iterate(iterate_function func);
void terminate();

