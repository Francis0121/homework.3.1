#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <string.h>

int main(int argc, char *argv[])
{
	struct hostent *hostp;
	struct sockaddr_in server;
	int sock;

	char buf[BUFSIZ];
	int bytesread;

	if(argc != 3)
	{
		(void) fprintf(stderr,"usage: %s host port\n", argv[0]);
		exit(1);
	}

	if ((sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0) {
		perror("socket");
		exit(1);
	}

	if ((hostp = gethostbyname(argv[1])) == 0) {
		fprintf(stderr,"%s: unknown host\n",argv[2]);
		exit(1);
	}

	memset((void *) &server, 0, sizeof (server));
	server.sin_family = AF_INET;
	memcpy((void *) &server.sin_addr, hostp->h_addr, hostp->h_length);
	server.sin_port = htons((u_short)atoi(argv[2]));

	if (connect(sock, (struct sockaddr *)&server, sizeof (server)) < 0) {
		(void) close(sock);
		fprintf(stderr, "connect");
		exit(1);
	}
	for (;;) {
		/* data from keyboard */
		if (!fgets(buf, sizeof buf, stdin)) {
			exit(0);
		}
		if (write(sock, buf, strlen(buf)) < 0) {
			perror("write");
			exit(1);
		}
		bytesread = read(sock, buf, sizeof buf);
		buf[bytesread] = '\0';
		printf("%s: got %d bytes: %s\n", argv[0], bytesread, buf);
	}
} 

