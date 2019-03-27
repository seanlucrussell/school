/*

The Checker checks whether or not argTwo (the dividend) is divisible by argOne (the divisor) and prints out the result. Both these arguments are positive integers.

If divisible, Checker returns 1. If not divisible, Checker returns 0.

*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/shm.h>

int main(int argc, char **argv) {

  // get process id, store in pid
  int pid = getpid();
  printf("Checker process [%d]: starting.\n",pid);

  // get shared memory from pipe
  int shmid;
  read(atoi(argv[3]), &shmid, sizeof(shmid));
  printf("Checker process [%d]: read 4 bytes containing shm ID %d.\n",pid,shmid);

  // close pipe
  close(atoi(argv[3]));

  
  int* shared = (int*) shmat(shmid,NULL,0);

  

  int x;
  // if arg2 is divisible by arg1, print and return 1
  if(atoi(argv[2])%atoi(argv[1])==0) {
    printf("Checker process [%d]: %s *IS* divisible by %s.\n",pid,argv[2],argv[1]);
    
    shared[0]=1;
    printf("Checker process [%d]: wrote result (1) to shared memory.\n",pid);
    shmdt(shared);
    return 1;
  }

  // if arg2 is not divisible by arg1, print and return 0
  printf("Checker process [%d]: %s *IS NOT* divisible by %s.\n",pid,argv[2],argv[1]);

  shared[0]=0;
  printf("Checker process [%d]: wrote result (0) to shared memory.\n",pid);
  shmdt(shared);
  return 0;
}

