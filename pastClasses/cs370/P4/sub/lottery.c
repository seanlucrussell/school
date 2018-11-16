#include "lottery.h"

void lottery(int seed){
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

  int maxTickets = processList[0].ticketsHigh;
  for (int i=1;i<lc;i++)
    if(processList[i].ticketsHigh > maxTickets)
      maxTickets = processList[i].ticketsHigh;
  printf("%d\n",maxTickets);

  srand(seed);

  puts("\n************************************************************\n\nFCFS SCHEDULING ALGORITHM:\n\n============================================================\n\nProcess\tAT\tBT\tWT\tTAT");
  for(int k=0;k<lc;k++){
    int smallest = 0;
    int ticket = 0;
    ticket = rand()%maxTickets;
    for(int i=0;i<lc;i++)
      if(ticket < processList[i].ticketsHigh && ticket > processList[i].ticketsLow)
        smallest=i;
    
    int alreadyBeenUsed = 0;
    for(int i=0;i<listLocation;i++)
      if(pidList[i]==processList[smallest].id)
        alreadyBeenUsed = 1;
    
    // find process with largest number of tickets that has not been serviced
    if(alreadyBeenUsed==1){
      int c = 0;
      int largestProcess = 0;
      while(1){
        int b = 0;
        for(int i=0;i<listLocation;i++){
          if(processList[largestProcess].id==pidList[i]){
            largestProcess++;
            b = 1;
          }
        }
        if (b==0) break;
      }

      for(int i=1;i<lc;i++){
        for(int j=0;j<listLocation;j++){
          if(pidList[j]==processList[i].id){
            c = 1;
            break;
          }
        }
        if(c==1) continue;

        if( (processList[i].ticketsHigh-processList[i].ticketsLow) > (processList[largestProcess].ticketsHigh-processList[largestProcess].ticketsLow))
          largestProcess = i;
      }
      smallest = largestProcess;
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
