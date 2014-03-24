/*
Student ID : 
Name : 
*/

#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <sys/stat.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <ctype.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void display();


int peertcpSocket = -1;	// peer socket

int main(int argc, char **argv) {
	
  int tcpServ_sock;
  
  struct sockaddr_in tcpServer_addr;
  struct sockaddr_in tcpClient_addr;
  struct sockaddr_in newTcp_addr;

  int clnt_len;
    
  fd_set reads, temps;
  int fd_max;

  char command[1024];


  char *tcpport = NULL;
  char *userid = NULL;

// NEED TO ADD SOME VARIABLES 

  if(argc != 3){
    printf("Usage : %s <tcpport> <userid>\n", argv[0]);
    exit(1);
  }


  display();
	

	// NEED TO CREATE A SOCKET FOR TCP SERVER

  tcpServ_sock = socket(PF_INET, SOCK_STREAM, 0);
  


	// NEED TO bind
			

	// NEED TO listen
  

	// initialize the select mask variables and set the
	// mask with stdin and the tcp server socket

  FD_ZERO(&reads);

  printf("%s> \n", userid);

  while(1) {
    int nfound;

    temps = reads;
    
    nfound = select(fd_max+1, &temps, 0, 0, NULL);
	
	if(FD_ISSET(fileno(stdin), &temps)) {
		// Input from the keyboard
		fgets(command, sizeof (command), stdin);
  		FD_CLR(fileno(stdin), &temps);
		

	// NEED TO IMPLEMENT for input from keybord

  		printf("%s> \n", userid);
	}
	else if(FD_ISSET(tcpServ_sock, &temps))
	{
		//connect request from a peer


	}
	else if(FD_ISSET(peertcpSocket, &temps))
	{

		// message from a peer
	}

  }//while End
}//main End

void display() {
	printf("Student ID : 20000000 \n");
	printf("Name : Sanghwan  \n");
}



