#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <string.h>
#include <errno.h>

int main(int argc, char *argv[])
{
	struct servent *servp;
	struct sockaddr_in server, remote;
	int request_sock, new_sock;
	int nfound, fd, maxfd, bytesread, addrlen;
	fd_set rmask, mask;
	static struct timeval timeout = { 5, 0 }; /* 5 seconds */

	char buf[BUFSIZ];
	if (argc != 2) {
		(void) fprintf(stderr,"usage: %s service|port\n",argv[0]);
		exit(1);
	}
	if ((request_sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0) {
		perror("socket");
		exit(1);
	}
	if (isdigit(argv[1][0])) {
		static struct servent s;
		servp = &s;
		s.s_port = htons((u_short)atoi(argv[1]));
	} else if ((servp = getservbyname(argv[1], "tcp")) == 0) {
		fprintf(stderr,"%s: unknown service\n", "tcp");
		exit(1);
	}
	memset((void *) &server, 0, sizeof server);
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;
	server.sin_port = servp->s_port;
	if (bind(request_sock, (struct sockaddr *)&server, sizeof server) < 0) {
		perror("bind");
		exit(1);
	}
	if (listen(request_sock, SOMAXCONN) < 0) {
		perror("listen");
		exit(1);
	}
	FD_ZERO(&mask);
	FD_SET(request_sock, &mask);
	maxfd = request_sock;
	for (;;) {
		rmask = mask;
		nfound = select(maxfd+1, &rmask, (fd_set *)0, (fd_set *)0, &timeout);
		if (nfound < 0) {
			if (errno == EINTR) {
				printf("interrupted system call\n");
				continue;
			}
			/* something is very wrong! */
			perror("select");
			exit(1);
		}
		if (FD_ISSET(request_sock, &rmask)) {
			/* a new connection is available on the connetion socket */
			addrlen = sizeof(remote);
			new_sock = accept(request_sock,
				(struct sockaddr *)&remote, &addrlen);
			if (new_sock < 0) {
				perror("accept");
				exit(1);
			}
			printf("connection from host %s, port %d, socket %d\n",
				inet_ntoa(remote.sin_addr), ntohs(remote.sin_port),
				new_sock);
			FD_SET(new_sock, &mask);
			if (new_sock > maxfd)
				maxfd = new_sock;
			FD_CLR(request_sock, &rmask);
		}
		for (fd=0; fd <= maxfd ; fd++) {
			/* look for other sockets that have data available */
			if (FD_ISSET(fd, &rmask)) {
				/* process the data */
				bytesread = read(fd, buf, sizeof buf - 1);
				if (bytesread<0) {
					perror("read");
					/* fall through */
				}
				if (bytesread<=0) {
					printf("server: end of file on %d\n",fd);
					FD_CLR(fd, &mask);
					if (close(fd)) perror("close");
					continue;
				}
				buf[bytesread] = '\0';
				printf("%s: %d bytes from %d: %s\n",
					argv[0], bytesread, fd, buf);
				/* echo it back */
				if (write(fd, buf, bytesread)!=bytesread)
					perror("echo");
			}
		}
	}
} /* main - server.c */
