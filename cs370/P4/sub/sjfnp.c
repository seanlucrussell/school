#include "sjfnp.h"

void shortestJobFirstNonPreemptive(){
  int* pidList = calloc(lc, sizeof(int));
  int listLocation = 0;
  double currentTime = 0;
  double waitTime = 0;
  double tat = 0;
  double averageWait = 0;
  double averageTAT = 0;

  int smallestWait = 0;
  for (int i=1;i<lc;i++)
    if(processList[i].arrivalTime < processList[smallestWait].arrivalTime)
      smallestWait = i;
  currentTime = processList[smallestWait].arrivalTime;

  puts("\n************************************************************\nSHORTEST JOB FIRST NON PREEMPTIVE SCHEDULING ALGORITHM:\n\n============================================================\n\nProcess\tAT\tBT\tWT\tTAT");

// find process with lowest arrival time firstly, then lowest burst time

  for(int k=0;k<lc;k++){
    int smallest = 0;
    for(int j=0;j<listLocation;j++)
      for(int i=0;i<listLocation;i++)
        if(processList[smallest].id==pidList[i])
          smallest++;
    for(int i=smallest;i<lc;++i){
      // if process has not already arrived, ignore it
      if(currentTime < processList[i].arrivalTime)
        continue;
      // if process has already been taken care of, ignore it
      int c=0;
      for(int j=0;j<listLocation;j++)
        if(processList[i].id==pidList[j]){
          c=1;
          break;
        }
      if (c==1) continue;

      if (processList[i].burstDuration<processList[smallest].burstDuration
                         || (processList[i].burstDuration<=processList[smallest].burstDuration
                         && processList[i].arrivalTime<processList[smallest].arrivalTime)){
        smallest = i;
      }
    }

    waitTime = currentTime-processList[smallest].arrivalTime;
    tat = currentTime + processList[smallest].burstDuration - processList[smallest].arrivalTime;
    averageWait += waitTime;
    averageTAT += tat;

    pidList[listLocation] = processList[smallest].id;
    listLocation++;
    printf("P%d\t%d\t%d\t%.1f\t%.1f\n",processList[smallest].id,processList[smallest].arrivalTime,processList[smallest].burstDuration,waitTime,tat);
    currentTime+=processList[smallest].burstDuration;
  }

  printf("\naverage waiting time: %.3f\n\naverage turnaround time: %.3f\n\nthroughput: %.8f\n\n",averageWait/lc,averageTAT/lc,lc/currentTime);

  
  free(pidList);
}
