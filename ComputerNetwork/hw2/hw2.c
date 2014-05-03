// Student ID :
// Name :

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

#define PROMPT() {printf("\n>");fflush(stdout);}
#define GETCMD "load"
#define QUITCMD "quit"

int portnum = 0;


int main(int argc, char *argv[])
{
	struct sockaddr_in server, remote;
	int request_sock, new_sock, fromserver_sock = -1;
	int nfound, fd, maxfd, bytesread, addrlen;
	char buf[BUFSIZ];
	fd_set rmask, mask;


	if (argc != 2) {
		(void) fprintf(stderr,"usage: %s portnum \n",argv[0]);
		exit(1);
	}

	portnum = atoi(argv[1]);

	printf("Student ID : 2000000\n");
	printf("Name : Sanghwan Lee\n");

	// Create a Server Socket


	FD_ZERO(&mask);

	// set the mask

	PROMPT(); 

	for (;;) {
		rmask = mask;
		nfound = select(maxfd + 1, &rmask, (fd_set *)0, (fd_set *)0, NULL);

                // check for stdin

                // check for request socket

                // check for the connection to the server

                // check for the connection from the client


	}
} /* main - hw2.c */


