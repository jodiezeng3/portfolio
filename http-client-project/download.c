#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <errno.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
  //read and verify arguments
  
  if(argc != 5) {
    fprintf(stderr, "Usage: %s server decimal-port remote-file output-file\n", argv[0]);
    exit(1);
  }

  char *end;
  long int port = strtol(argv[2], &end, 10);
  if(port > 65535 || port < 1) {
    fprintf(stderr, "Invalid port '%s' (must be between 1 and 65535)\n", argv[2]);
    exit(1);
  }

  //name resolution on server to get its IP addr
  
  struct addrinfo *result;
  struct addrinfo hints;

  memset(&hints, 0, sizeof(hints));
  hints.ai_socktype = SOCK_STREAM;
  hints.ai_family = AF_INET;
  
  int s = getaddrinfo(argv[1], argv[2], &hints, &result);
  if(s != 0) {
    fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(s));
    exit(2);
  }

  //open connection to server
  
  int sfd;
  
  sfd = socket(result->ai_family, result->ai_socktype, result->ai_protocol);
  if(sfd == -1) {
    fprintf(stderr, "Could not connect\n");
    exit(3);
  }

  if(connect(sfd, result->ai_addr, result->ai_addrlen) == -1) {
    perror("connect");
    exit(3);
  }
  
  //construct request and send result
  
  int BUF_SIZE = 25000;
  ssize_t nread;
  char buf[BUF_SIZE];
  
  char request[1024];
  //when i just use malloc or the above, doesn't work
  strcat(request, "GET ");
  strcat(request, argv[3]);
  strcat(request, " HTTP/1.1\r\nHost: ");
  strcat(request, argv[1]);
  strcat(request, "\r\n\r\n");
 
  char header_name[strlen(argv[4])+strlen(".header")+1];
  //header_name = malloc(strlen(argv[4])+strlen(".header")+1);
  header_name[0] = '\0';
  strcat(header_name, argv[4]);
  strcat(header_name, ".header");
  
  FILE *fp_header, *fp_body;
  fp_header = fopen(header_name, "w+");
  fp_body = fopen(argv[4], "w+"); 

  snprintf(request, sizeof(request), "GET %s HTTP/1.1\r\nHost: %s\r\n\r\n", argv[3], argv[1]);
  size_t len = strlen(request)+1;
    
  if(write(sfd, request, len) != len) {
      fprintf(stderr, "partial/failed write\n");
      exit(4);
  }

  //read response from server
  nread = 0;
  int total = 0;
  do {
    nread = read(sfd, buf+total, BUF_SIZE);
    total += nread;
    
    if(nread == -1) {
      perror("read"); //need to retry read if get EINTR error
      exit(4);
    }
   } while(nread != 0 && total < BUF_SIZE);  

  //place server response into header file, get index of end of header
  int header_index = -1;
   
  for(int i = 0; i < BUF_SIZE; i++) {

    if(i < BUF_SIZE-3 && buf[i] == '\r' && buf[i+1] == '\n' && buf[i+2] == '\r' && buf[i+3] == '\n') {
	fputc(buf[i], fp_header);
	fputc(buf[i+1], fp_header);
	fputc(buf[i+2], fp_header);
	fputc(buf[i+3], fp_header);
  
	header_index = i+4;
	break;
      }

      fputc(buf[i], fp_header);
  }


  //place body in body file what about fwrite
  for(int i = header_index; i < total; i++) {
    fputc(buf[i], fp_body);
  }

  //close files
  fclose(fp_header);
  fclose(fp_body);

  freeaddrinfo(result);
  close(sfd);
  
  //get status code, return num
  if(buf[9] == '2')  {//first int of status code
    exit(0);
  }
  else {
    exit(5);
    }
}

