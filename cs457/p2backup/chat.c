#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <errno.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <unistd.h>
#include <sys/socket.h>

#include <ctype.h>
#include <netdb.h>
#include <ifaddrs.h>

#define PORT 8080

// done with help of: www.geeksforgeeks.org/socket-programming-cc/

void printHelpMessage() {
  printf("Usage: chat [-p port_number -s server_address]\n");
}

void sendMsg(int sockfd) {
  char* msg = NULL;
  while (1) {
    size_t size = 0;
    printf("You: ");
    getline(&msg, &size, stdin);
    if (strlen(msg) <= 140)
      break;
    printf("Error: Input too long.\n");
  }
  send(sockfd,msg,strlen(msg),0);
}

void recieveMsg(int sockfd) {
  char buffer[140] = {0};
  read(sockfd,buffer,140);
  printf("Friend: %s",buffer);
}

void runServer() {
  printf("Welcome to Chat!\n");
  int sockfd;
  if ((sockfd = socket(AF_INET,SOCK_STREAM,0)) == 0) {
    printf("socket failed\n");
    exit(-1);
  }
  struct sockaddr_in address;
  address.sin_family = AF_INET;
  address.sin_addr.s_addr = INADDR_ANY;
  address.sin_port = htons(PORT);
  if (bind(sockfd,(struct sockaddr*)&address,sizeof(address))<0) {
    printf("bind failed\n");
  }
  //ip address discovery based on: stackoverflow.com/questions/2283494/
  struct ifaddrs *ifaddr, *ifa;
  int family, s;
  char host[NI_MAXHOST];
  getifaddrs(&ifaddr);
  for (ifa = ifaddr; ifa != NULL; ifa = ifa->ifa_next) {
    if (ifa->ifa_addr == NULL)
      continue;
    s = getnameinfo(ifa->ifa_addr, sizeof(struct sockaddr_in), host, NI_MAXHOST, NULL, 0, NI_NUMERICHOST);
    if ((strcmp(ifa->ifa_name,"eno1") == 0)&&(ifa->ifa_addr->sa_family==AF_INET))
      printf("Waiting for a connection on\n%s port %d\n",host,PORT);
  }

  if (listen(sockfd,1) < 0) {
    printf("listen error\n");
    exit(-1);
  }
  int addrlen = sizeof(address);
  int new_socket;
  if ((new_socket = accept(sockfd, (struct sockaddr*)&address,(socklen_t*)&addrlen))<0) {
    printf("problem accepting connection\n");
    exit(-1);
  }
  printf("Found a friend! You receive first.\n");
  while (1) {
    recieveMsg(new_socket);
    sendMsg(new_socket);
  }
}

void runClient(char* port, char* addr) {
  for (int i=0;i<strlen(port);++i){
    if (!isdigit(port[i])) {
      printf("supplied port number is invalid. exiting\n");
      exit(-1);
    }
  }
  int portNum = atoi(port);
  int sockfd;
  if ((sockfd = socket(AF_INET,SOCK_STREAM,0)) < 0) {
    printf("error creating socket\n");
    exit(-1);
  }
  struct sockaddr_in serv_addr;
  memset(&serv_addr, '0',sizeof(serv_addr));
  serv_addr.sin_family = AF_INET;
  serv_addr.sin_port = htons(portNum);
  if(inet_pton(AF_INET,addr,&serv_addr.sin_addr)<=0) {
    printf("invalid address: %s\n",addr);
    exit(-1);
  }
  printf("Connecting to server...\n");
  if (connect(sockfd,(struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
    printf("connection failed\n");
    exit(-1);
  }
  printf("Connected!\nConnected to a friend! You send first.\n");
  while (1) {
    sendMsg(sockfd);
    recieveMsg(sockfd);
  }
}

int main(int argc, char** argv) {
  printf("%d\n",sizeof(char));
  if (argc == 1) {
    runServer();
  } else if (argc == 5) {
    if (strcmp(argv[1],"-p") == 0 && strcmp(argv[3],"-s") == 0) {
      runClient(argv[2],argv[4]);
    } else if (strcmp(argv[3],"-p") == 0 && strcmp(argv[1],"-s") == 0) {
      runClient(argv[4],argv[2]);
    } else {
      printf("Error parsing arguments.\n");
      printHelpMessage();
    }
  } else {
    printHelpMessage();
  }
}
