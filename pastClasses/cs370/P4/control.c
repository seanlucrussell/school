#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "util.h"
#include "fcfs.h"
#include "priorityalgorithm.h"
#include "lottery.h"
#include "sjfnp.h"


int main(int argc, char *argv[]) {
  init(argv[1]);
  char in[20];

  while(1){
    printf("Enter an algorithm: ");
    fgets(in,sizeof(in),stdin);
    if(strcmp(in,"exit\n")==0)
      break;
    else if(strcmp(in,"FCFS\n")==0)
      firstComeFirstServed();
    else if(strcmp(in,"priority\n")==0)
      priorityAlgorithm();
    else if(strcmp(in,"lottery\n")==0)
      lottery(atoi(argv[2]));
    else if(strcmp(in,"SJFnp\n")==0)
      shortestJobFirstNonPreemptive();
    else if(strcmp(in,"SJFp\n")==0)
      shortestJobFirstNonPreemptive();
    else
      printf("Options (case sensitive):\n- FCFS\n- SJFnp\n- SJFp\n- priority\n- lottery\n- exit\n");
  }


  free(processList);
  return 0;
}
