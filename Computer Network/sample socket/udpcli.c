#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <errno.h>

int main(int argc, char *argv[])
{
   int sockid, retcode;
   struct hostent *hostp;

   struct sockaddr_in my_addr, server_addr;
   char msg[12];
   if(argc != 4)
   {
	printf("%s myport serveraddr serverport\n", argv[0]);
        return 0;
   }
   
   printf("Client: creating socket\n");
      if ( (sockid = socket(AF_INET, SOCK_DGRAM, 0)) < 0) 
      { printf("Client: socket failed: %d\n",errno); exit(0); }

   printf("Client: binding my local socket\n");
   memset((char *) &my_addr, 0, sizeof(my_addr));
   my_addr.sin_family = AF_INET;
   my_addr.sin_addr.s_addr = htonl(INADDR_ANY);
   my_addr.sin_port = htons(atoi(argv[1]));

   if ( (bind(sockid, (struct sockaddr *) &my_addr, 
              sizeof(my_addr)) < 0) )
      { printf("Client: bind fail: %d\n",errno); exit(0); }   

    if ((hostp = gethostbyname(argv[2])) == 0) {
        fprintf(stderr,"%s: unknown host\n",argv[2]);
        exit(1);
    }


   printf("Client: creating addr structure for server\n");
   bzero((char *) &server_addr, sizeof(server_addr));
   server_addr.sin_family = AF_INET;
   server_addr.sin_addr.s_addr = inet_addr(argv[2]);
	memcpy((void *) &server_addr.sin_addr, hostp->h_addr, hostp->h_length);
   server_addr.sin_port = htons((u_short)atoi(argv[3]));

   printf("Client: initializing message and sending\n");
   sprintf(msg,  "Hello world");
   retcode = sendto(sockid,msg,12,0,(struct sockaddr *) &server_addr,
 		    sizeof(server_addr));
   if (retcode <= -1)
     {printf("client: sendto failed: %d\n",errno); exit(0); }   

   printf("Client: Successful\n");
   /* close socket */
   close(sockid);
 }


