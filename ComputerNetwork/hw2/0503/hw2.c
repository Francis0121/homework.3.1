// Student ID : 20093267
// Name : Sung Geun Kim

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
#include <assert.h>

#define TRUE 1
#define FALSE 0

#define PROMPT() {printf("\n>");fflush(stdout);}
#define GETCMD "load"
#define QUITCMD "quit"

char** str_split(char* a_str, const char a_delim, int *size);
int clientConnect(char *hostName, char *newPort, char *userid, fd_set reads, fd_set temps);

int portnum = 0; // Port Number

int main(int argc, char *argv[])
{
	struct sockaddr_in server, // Server 의 Socket addr
									remote;
	int request_sock, // Server 생성시 만든 Socket 번호
		new_sock, // Client가 생성하는 socket?
		fromserver_sock = -1; // Client로부터 받은 socket?
	int nfound,
		fd,
		maxfd, 
		bytesread;	
	char buf[1024];
	char request[10000], response[10000];
	fd_set temps, reads;
	socklen_t client_len; // int type client_len re define


	if (argc != 2) {
		(void) fprintf(stderr,"usage: %s portnum \n",argv[0]);
		exit(1);
	}

	portnum = atoi(argv[1]); // Set Port Number

	printf("Student ID : 20093267\n");
	printf("Name : Sung Geun Kim\n");

	/**
	*	Make Server
	*	Create(Srever)-> Bind(Server) -> Listen(From Client) -> 
	*	Accept(From Client) -> Work(Server)
	*/
	// Create(Server)
	if(  (request_sock = socket(PF_INET, SOCK_STREAM, 0) ) < 0 ){
		perror("Error : [Server Socket] doesn't create\n");
		exit(1);
	}// End Create
	
	// Setting Address Structure
	memset( (void *) &server, 0, sizeof(server) ); // initiliaze variable tcpServer_addr to 0
	server.sin_family = PF_INET; 
	server.sin_addr.s_addr = htonl(INADDR_ANY); // set IP Address
	server.sin_port = htons( portnum ); // set port number

	// Bind(Server) Start
	if( bind(request_sock, (struct sockaddr *) &server, sizeof(server) ) < 0 ){ 
		perror("Error : [Server Socket] dosen't bind\n");
		exit(1);
	}// Bind(Server) End

	// Wait Listen(From Client)
	if( listen(request_sock, SOMAXCONN) < 0){
		perror("Error : [Server Socket] doesn't listen\n");
		exit(1);
	}// Wait Listen(From Client) End

	FD_ZERO(&reads); // FD_ZERO - 모든 소켓 기술자를 세크(read)에서 제거합니다.
	FD_SET(request_sock, &reads); // FT_SET - 지정된 소켓 기술자(tcpServ_sock)를 세트(read)에 추가한다.
	FD_SET(fileno(stdin), &reads); // 지정된 소켓 기술자(fileno(stdin))를 세트(read)에 추가한다.
	
	maxfd = request_sock;

	PROMPT(); 

	while (TRUE) {
		temps = reads;
		nfound = select(maxfd + 1, &temps, (fd_set *)0, (fd_set *)0, NULL);
		
		if (nfound < 0) {
			if (errno == EINTR) {
				printf("Warn : [Server Socket] Interrupted system call\n");
				continue;
			}
			perror("Error : [Server Socket] select( ) function\n");
			exit(1);
		}

		if(FD_ISSET(fileno(stdin), &temps)) {
			struct hostent *hostp; // new connect server use
			struct servent *servp;
			struct sockaddr_in	 client;
			static struct timeval timeout = { 5, 0 };
			
			char** tokens;
			int *bufSize = (int *) malloc(1*sizeof(int));
	
			FD_CLR(fileno(stdin), &temps);
			fgets(buf, sizeof (buf), stdin);
			strtok(buf, "\n"); // New Line Remove
			
			tokens = str_split(buf, ' ', bufSize); // split ' '

			if( *bufSize == 4 ){ // load hostname port pathname ex) load localhost 10000 /a.html
					char *command = *(tokens+0),
							*hostName = *(tokens+1),
							*port = *(tokens+2),
							*pathName = *(tokens+3);
					if( (new_sock = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0){
						perror("Error : [Client Socket] doesn't create in talk");
						exit(1);
					}

					if( strcmp(command,  GETCMD) != 0 ){
						fprintf(stderr,"Error : [Client Socket] Not supported command %s", command);
						exit(1);
					}else{
						sprintf(request, 
							 "GET %s HTTP/1.0\r\nHost: %s\r\nUser-agent: HW2/1.0\r\nConnection: close\r\n\r\n", 
							 pathName,
							 hostName);
					}

					if (isdigit(argv[1][0])) {
						static struct servent s;
						servp = &s;
						s.s_port = htons((u_short)atoi(port));
					} else if ((servp = getservbyname(port, "tcp")) == 0) {
						fprintf(stderr,"Error : [Client Socket] %s: unknown service\n",port);
						exit(1);
					}

					if( (hostp = gethostbyname(*(tokens+1))) == 0){
						perror("Error : [Client Socket] unknown host\n");
						exit(1);
					}

					memset((void *) &client, 0, sizeof(client));
					client.sin_family = PF_INET;
					memcpy((void *) &client.sin_addr, hostp->h_addr, hostp->h_length);
					client.sin_port = servp->s_port;

					if(connect(new_sock, (struct sockaddr *) &client, sizeof(client)) < 0){
						(void) close(new_sock);
						printf("Error : [Client Socket] Connect\n");
						exit(1);
					}		
					FD_SET(new_sock, &reads);
					if (write(new_sock, request, strlen(request)) < 0) {
						perror("Error : [Client Socket]  write( ) function\n");
						exit(1);
					}
					while(TRUE){
						temps = reads;
						nfound = select(FD_SETSIZE, &temps, 0, 0, &timeout);
						if (nfound < 0) {
							if (errno == EINTR) {
								printf("Warn : [Client Socket] Interrupted system call\n");
								continue;
							}
							perror("Error :  [Client Socket] select( ) function\n");
							exit(1);
						}
						if (FD_ISSET(new_sock, &temps)) {
							bytesread = read(new_sock, buf, sizeof buf );
							buf[bytesread] = '\0';
							printf("%s\n", buf);
							break;
						}
					}
					FD_CLR(new_sock, &reads);
					if (close(new_sock)) 
						perror("Error : [Client Socket] close( ) function\n");
			}else if( *bufSize == 1 ){ // quit
					char *command = *(tokens+0);
					if ( strcmp(command, QUITCMD) == 0){
						break;	
					}else{
						printf("Error : [Client Socket] Not Exist Command Type\n");
					}
			}else { // error checking
					printf("Error : [Client Socket] Not Exist Command Type\n");
			}

			if (tokens){ // return pointer memory
				int i;
				for (i = 0; *(tokens + i); i++)
					free(*(tokens + i));
				free(tokens);
			}
			free(bufSize);
			PROMPT();
		}else if(FD_ISSET(request_sock, &temps)){
			// Accept (From Client) start
			client_len = sizeof(remote);
			fromserver_sock = accept(request_sock, (struct sockaddr *) &remote, &client_len);
			if(fromserver_sock < 0){
				perror("Error : [Server Socket]  Doesn't accept");
				exit(1);
			}// Accept (From Client) end
			printf("Connection : Host IP %s, Port %d, socket %d\n", 
					inet_ntoa(remote.sin_addr), 
					ntohs(remote.sin_port), 
					fromserver_sock);
			FD_SET(fromserver_sock, &reads);
			if (fromserver_sock > maxfd)
				maxfd = fromserver_sock;
			FD_CLR(request_sock, &temps);
		}
		
		for (fd=0; fd <= maxfd ; fd++) {
			if (FD_ISSET(fd, &temps)) {
				bytesread = read(fd, buf, sizeof buf - 1);

				if (bytesread<0) {
					perror("Error : [Server Socket] read\n");
				}
				if (bytesread <= 0) {
					//printf("Server: end of file on %d\n",fd);
					FD_CLR(fd, &reads);
					if (close(fd)) perror("close");
						continue;
				}
				buf[bytesread] = '\0';
				printf("%s\n", buf);
				
				// ~ Requset Message Control				
				int *bufSize = (int *) malloc(1*sizeof(int));
				char **requestMessage = str_split(buf, '\r', bufSize),
						**requestLine = str_split(*(requestMessage+0), ' ', bufSize);
				char fileName[] = ".";
				strcat(fileName, *(requestLine+1));
				
				if ( access(fileName, R_OK) == -1 ){
					printf("Server Error : No such file %s!\n", fileName);
					sprintf(response, 
						 "HTTP/1.0 404 NOT FOUND\r\nConnection: close\r\nContent-Length: 0\r\nContent-Type: text/html\r\n\r\n");
				}else{
					FILE *pFile = NULL;					
					int filesize ;
					char *sourceFile;

					pFile = fopen(fileName, "r" );
					fseek(pFile, 0L, SEEK_END);
					filesize  = ftell(pFile);
					rewind (pFile);

					if( pFile != NULL ){	
						sourceFile = malloc(sizeof(char*) * filesize);
						fread(sourceFile, sizeof(char), filesize, pFile);
						fclose( pFile );
					}
		
					printf("finish  %d %d \n", strlen(sourceFile), filesize);
					sprintf(response, 
							 "HTTP/1.0 200 OK\r\nConnection: close\r\nContent-Length: %d\r\nContent-Type: text/html\r\n\r\n%s", strlen(sourceFile), sourceFile);
				}

				if (write(fd, response, strlen(response)) < 0) {
					perror("Error :  [Server Socket] write( ) function \n");
					exit(1);
				}
				if (requestMessage){ // return pointer memory
					int i;
					for (i = 0; *(requestMessage + i); i++)
						free(*(requestMessage + i));
					free(requestMessage);
				}
				if (requestLine){ // return pointer memory
					int i;
					for (i = 0; *(requestLine + i); i++)
						free(*(requestLine + i));
					free(requestLine);
				}
				free(bufSize);
				PROMPT();
			}//FD_ISSET end
		}//fd for statment end
		
	}// while End
	return 0;
}// Main End

char** str_split(char* a_str, const char a_delim, int *size)
{
    char** result    = 0;
    size_t count     = 0;
    char* tmp        = a_str;
    char* last_comma = 0;
    char delim[2];
    delim[0] = a_delim;
    delim[1] = 0;

    /* Count how many elements will be extracted. */
    while (*tmp)
    {
        if (a_delim == *tmp)
        {
            count++;
            last_comma = tmp;
        }
        tmp++;
    }

    /* Add space for trailing token. */
    count += last_comma < (a_str + strlen(a_str) - 1);
	
	*size = count;
    /* Add space for terminating null string so caller
       knows where the list of returned strings ends. */
    count++;
	
    result = malloc(sizeof(char*) * count);

    if (result)
    {
        size_t idx  = 0;
        char* token = strtok(a_str, delim);

        while (token)
        {
            assert(idx < count);
            *(result + idx++) = strdup(token);
            token = strtok(0, delim);
        }
        assert(idx == count - 1);
        *(result + idx) = 0;
    }

    return result;
}