/*

The Checker checks whether or not argTwo (the dividend) is divisible by argOne (the divisor) and prints out the result. Both these arguments are positive integers.

If divisible, Checker returns 1. If not divisible, Checker returns 0.

*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char **argv) {

  // get process id, store in pid
  int pid = getpid();

  printf("Checker process [%d]: Starting.\n",pid);
  
  // if arg2 is divisible by arg1, print and return 1
  if(atoi(argv[2])%atoi(argv[1])==0) {
    printf("Checker process [%d]: %s *IS* divisible by %s.\n",pid,argv[2],argv[1]);
    printf("Checker process [%d]: Returning 1.\n",pid);
    return 1;
  }

  // if arg2 is not divisible by arg1, print and return 0
  printf("Checker process [%d]: %s *IS NOT* divisible by %s.\n",pid,argv[2],argv[1]);
  printf("Checker process [%d]: Returning 0.\n",pid);
  return 0;
}

