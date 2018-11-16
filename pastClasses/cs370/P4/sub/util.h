#ifndef UTIL
#define UTIL

struct process {
  int id;
  int arrivalTime;
  int burstDuration;
  int priority;
  int ticketsLow;
  int ticketsHigh;
};

int lc;
struct process* processList;

void init(const char* fileName);
void processPrint(const struct process* p);
int lineCount(const char* fileName);
void read(const char* fileName);


#endif
