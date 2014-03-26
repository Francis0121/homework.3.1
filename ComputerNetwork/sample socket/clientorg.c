#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <errno.h>
#include <netdb.h>
#include <string.h>

int main(int argc, char *argv[])
{
	struct hostent *hostp;
	struct servent *servp;
	struct sockaddr_in server;
	int sock;
	static struct timeval timeout = { 5, 0 }; /* five seconds */
	fd_set rmask, xmask, mask;
	char buf[BUFSIZ];
	int nfound, bytesread;
	if (argc != 3) {
		(void) fprintf(stderr,"usage: %s service|port host\n",argv[0]);
		exit(1);
	}
	if ((sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0) {
		perror("socket");
		exit(1);
	}
	if (isdigit(argv[1][0])) {
		static struct servent s;
		servp = &s;
		s.s_port = htons((u_short)atoi(argv[1]));
	} else if ((servp = getservbyname(argv[1], "tcp")) == 0) {
		fprintf(stderr,"%s: unknown service\n",argv[1]);
		exit(1);
	}
	if ((hostp = gethostbyname(argv[2])) == 0) {
		fprintf(stderr,"%s: unknown host\n",argv[2]);
		exit(1);
	}
	memset((void *) &server, 0, sizeof server);
	server.sin_family = AF_INET;
	memcpy((void *) &server.sin_addr, hostp->h_addr, hostp->h_length);
	server.sin_port = servp->s_port;
	if (connect(sock, (struct sockaddr *)&server, sizeof server) < 0) {
		(void) close(sock);
		perror("connect");
		exit(1);
	}
	FD_ZERO(&mask);
	FD_SET(sock, &mask);
	FD_SET(fileno(stdin), &mask);
	for (;;) {
		rmask = mask;
		nfound = select(FD_SETSIZE, &rmask, (fd_set *)0, (fd_set *)0, &timeout);
		if (nfound < 0) {
			if (errno == EINTR) {
				printf("interrupted system call\n");
				continue;
			}
			/* something is very wrong! */
			perror("select");
			exit(1);
		}
		if (FD_ISSET(fileno(stdin), &rmask)) {
			/* data from keyboard */
			if (!fgets(buf, sizeof buf, stdin)) {
				if (ferror(stdin)) {
					perror("stdin");
					exit(1);
				}
				exit(0);
			}
			if (write(sock, buf, strlen(buf)) < 0) {
				perror("write");
				exit(1);
			}
		}
		if (FD_ISSET(sock,&rmask)) {
			/* data from network */
			bytesread = read(sock, buf, sizeof buf);
			buf[bytesread] = '\0';
			printf("%s: got %d bytes: %s\n", argv[0], bytesread, buf);
		}
	}
} /* main - client.c */
