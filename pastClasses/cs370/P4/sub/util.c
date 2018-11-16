#include <stdlib.h>
#include "util.h"
#include <stdio.h>

void init(const char* fileName) {
  lc = lineCount(fileName);
  read(fileName);
}

// PRINTS PROCESS GOOD DON TUCH
void processPrint(const struct process* p){
  printf("ID:%d Arrival:%d Burst:%d Priority:%d TicketRange:%d-%d\n",p->id,p->arrivalTime,p->burstDuration,p->priority,p->ticketsLow,p->ticketsHigh);
}

// COUNTS LINES WORKS DON TUCH
int lineCount(const char* fileName){
  int lineCount = 0;
  int ch = 0;
  FILE* inFile = fopen(fileName,"r");
  while(!feof(inFile)){
    ch = fgetc(inFile);
    if(ch == '\n') lineCount++;
  }
  fclose(inFile);
  return lineCount;
}

// read the file, fill in the processList array
// WORKS DON TUCH
void read(const char* fileName){
  FILE* inFile = fopen(fileName,"r");
  int lc = lineCount(fileName);
  processList = malloc(lc * sizeof(struct process));
  for (int i=0;i<lc;i++){
    fscanf(inFile,"%d,%d,%d,%d,%d - %d",&processList[i].id,&processList[i].arrivalTime,&processList[i].burstDuration,&processList[i].priority,
                                        &processList[i].ticketsLow,&processList[i].ticketsHigh);
  }
  fclose(inFile);
}

