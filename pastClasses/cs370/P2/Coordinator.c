#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char **argv) {
  
  
  int pid = 0;


  for (int i=0;i<4;i++){

    pid = fork();
    
    if (pid == 0){
      execlp("./checker","checker",argv[1],argv[2+i]);
    }
    printf("Coordinator: forked process with ID %d.\n",pid);
    printf("Coordinator: waiting for process [%d].\n",pid);
    int status = waitpid(-1,&pid,0);
    printf("Coordinator: child process %d returned %d.\n",status,WEXITSTATUS(pid));
    
  }

  printf("Coordinator: exiting.\n");


  return 1;
}
