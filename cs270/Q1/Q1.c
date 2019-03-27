// Q1 Programming Quiz - Source file
// Author: ?????
// Date:   ?????
// Class:  CS270
// Email:  ?????

// Include files
#include "Q1.h"

// Global variables
Student *head = NULL;
Student *tail = NULL;

// ***** Implement four functions in header file

void readFile(char *filename){
  FILE * fp = fopen(filename,"r");
  char line [81];
  char* linecpy;
  char* tempname;
  char* gpas;
  while (fgets(line,sizeof(line),fp) != NULL) {
    if (line[0]==' ')
      break;
    linecpy = strdup(line);
    tempname = strdup(strtok(linecpy," "));
    gpas = strtok(NULL," ");
    if (gpas == NULL) {
      free(linecpy);
      free(tempname);
      break;
    }
    //printf("%f\n", atof(strtok(NULL," ")));
    insertStudent(tempname,atoi(gpas),atof(strtok(NULL," ")));
    free(linecpy);
    free(tempname);
  }
  fclose(fp);
}

void insertStudent(char *name, int id, float gpa){
  Student* temppointer = NULL;
  if (head != NULL)
    temppointer = head;
  head = calloc(1,sizeof(Student));
  head->next = temppointer;
  head->name = strdup(name);
  head->id = id;
  head->gpa = gpa;
}

void removeStudent(char *name){
  Student* current = head;
  Student* next = current;
  while (next != NULL) {
    if (strcmp(next->name,name)) {
      current->next = next->next;
      break;
    }
    current = next;
    next = next->next;
  }
}

void iterate(iterate_function func){
  Student* next = head;
  while (next != NULL){ 
    (*func)(next);
    next = next->next;
  }
}

void terminate(){
  Student* next = head;
  Student* cpy = next;
  while (next !=NULL) {
    cpy = next->next;
    removeStudent(next->name);
    next = cpy;
  }
}

