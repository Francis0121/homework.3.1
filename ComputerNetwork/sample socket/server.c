#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>

int main(int argc, char *argv[])
{
	struct sockaddr_in server, remote;
	int request_sock, new_sock;
	int bytesread, addrlen;
	int i;
	char buf[BUFSIZ];
	if (argc != 2) {
		(void) fprintf(stderr,"usage: %s port\n", argv[0]);
		exit(1);
	}
	if ((request_sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0) {
		perror("socket");
		exit(1);
	}

	memset((void *) &server, 0, sizeof (server));
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;
	server.sin_port = htons((u_short)atoi(argv[1]));

	if (bind(request_sock, (struct sockaddr *)&server, sizeof (server)) < 0) {
		perror("bind");
		exit(1);
	}
	if (listen(request_sock, SOMAXCONN) < 0) {
		perror("listen");
		exit(1);
	}
	for (;;) {
		addrlen = sizeof(remote);
		new_sock = accept(request_sock,
			(struct sockaddr *)&remote, &addrlen);
		if (new_sock < 0) {
			perror("accept");
			exit(1);
		}
		printf("connection from host %s, port %d, socket %d\n",
			inet_ntoa(remote.sin_addr), ntohs(remote.sin_port), new_sock);

		for (;;) {
			bytesread = read(new_sock, buf, sizeof (buf) - 1);
			if (bytesread<=0) {
				printf("server: end of file on %d\n", new_sock);
				if (close(new_sock)) 
					perror("close");
				break;
			}
			buf[bytesread] = '\0';
			printf("%s: %d bytes from %d: %s\n", 
				argv[0], bytesread, new_sock, buf);
			for(i = 0; i < bytesread; i++)
				buf[i] = toupper(buf[i]);
			/* echo it back */
			if (write(new_sock, buf, bytesread) != bytesread)
				perror("echo");
		}
	}
}
