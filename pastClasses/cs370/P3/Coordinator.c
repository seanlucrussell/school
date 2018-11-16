/*

8. If divisible, Checker should write 1 (true) to the shared memory segment, or 0 (false)
otherwise.
9. Parent-specific processing in the Coordinator should ensure that the Coordinator will
wait() for each instance of the child-specific processing to complete. This is done
AFTER all the processes have been started. The results retrieved from shared memory
should be printed and match up with what was printed in (7).
10. Both the Coordinator and Checker should clean up: FDs should be closed and shared
memory marked to be destroyed (use the shmctl() command).

*/

#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <errno.h>

int main(int argc, char **argv) {
  
  int pid[4];
  int fd[2];
  char *buffer;
  int shmid[4];
  int *shared[4];

  for (int i=0;i<4;i++){

    // Create a pipe and convert read id to string
    pipe(fd);
    sprintf(buffer,"%d",fd[0]); // change to 1 if not working

    // Create two copies of this process
    pid[i] = fork();

    // Create new checker process
    if (pid[i] == 0){
      close(fd[1]);
      execlp("checker","checker",argv[1],argv[2+i],buffer);
    }

    printf("Coordinator: forked process with ID %d.\n",pid[i]);

    // Close read end of pipe
    close(fd[0]);
    
    // Allocate shared memory, creat variable to store ID
    shmid[i] = shmget(IPC_PRIVATE,sizeof(int),0777 | IPC_CREAT);

    shared[i] = (int*)shmat(shmid[i],NULL,0);

    // Write shared memory ID to pipe
    write(fd[1],&shmid[i],sizeof(shmid[i])); 

    // Close write end of pipe
    close(fd[1]);  

    printf("Coordinator: wrote shm ID %d to pipe.\n",shmid[i]);
    
  }
  

  for (int i=0;i<4;i++){
    printf("Coordinator: waiting on child process ID [%d]...\n",pid[i]);
    int status = waitpid(-1,&pid[i],0);

    int x = *shared[i];
    if (x==0) printf("Coordinator: result (0) read from shared memory: %s *IS NOT* divisible by %s\n",argv[2+i],argv[1]);
    else printf("Coordinator: result (1) read from shared memory: %s *IS* divisible by %s\n",argv[2+i],argv[1]);
    
    // Cleaning up
    shmdt(shared[i]);
    shmctl(shmid[i],IPC_RMID,NULL);
  }


  printf("Coordinator: exiting.\n");

  return 1;
}
