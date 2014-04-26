#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#define SEMPERM 0600
#define TRUE 1
#define FALSE 0

typedef struct union_semun {
	int val;
	struct semid_ds *buf;
	ushort *array;
} semun;

int initsem (key_t semkey, int n){
	int status = 0, semid;
	if ( (semid = semget (semkey, 1, SEMPERM | IPC_CREAT | IPC_EXCL) ) == -1){
		if ( (errno == EEXIST) )
			semid = semget (semkey, 1, 0);
	}else{
		semun arg;
		arg.val = n;
		status = semctl(semid, 0, SETVAL, arg);
	}
	
	if (semid == -1 || status == -1){
		perror("initsem failed");
		return (-1);
	}
	return (semid);
}

int p (int semid){
	struct sembuf p_buf;
	p_buf.sem_num = 0;
	p_buf.sem_op = -1;
	p_buf.sem_flg = SEM_UNDO;
	if ( (semop(semid, &p_buf, 1) ) == -1){
		perror ("p(semid) failed");
		exit(1);
	}
	return (0);
}

int v (int semid){
	struct sembuf v_buf;
	v_buf.sem_num = 0;
	v_buf.sem_op = 1;
	v_buf.sem_flg = SEM_UNDO;
	if ( (semop(semid, &v_buf, 1)) == -1){
		perror ("v(semid) failed");
		exit(1);
	}
	return (0);
}

void dining(key_t mutexKey, int semid1, int semid2, int chopstick1, int chopstick2){
	int status;
	int mutexId;
	semun arg;
	pid_t pid = getpid();

	if ( (mutexId = initsem(mutexKey,1) ) < 0)
		exit(1);

	printf("\nprocess %d before critical section\n", pid);
	p(mutexId);
	printf("process %d in critical section\n",pid);

	// ~ Critical Section Start
	status = semctl(semid1, 0, GETVAL, arg);
	printf("\nnumber of sempaphore ID[ %d ] : %d\n", semid1, status);
	status = semctl(semid2, 0, GETVAL, arg);
	printf("number of sempaphore ID[ %d ] : %d\n", semid2, status);

	p(semid1);
	printf("process %d Hold %d Chop Stick [ %d ] : [ %d ] \n", pid, chopstick1, chopstick1, chopstick2);
	printf("process %d Wait Chop Stick  [ %d ] : [ %d ]\n", pid, chopstick1, chopstick2);
	sleep(5);
	p(semid2);
	printf("process %d Hold %d Chop Stick [ %d ] : [ %d ]\n", pid, chopstick2, chopstick1, chopstick2);
	printf("process %d Eat Chop Stick  [ %d ] : [ %d ]\n", pid, chopstick1, chopstick2);
	v(semid1);
	v(semid2);
	printf("process %d Pull Chop Stick  [ %d ] : [ %d ]\n", pid, chopstick1, chopstick2);
	// ~ Critical Section End

	printf("process %d leaving critical section\n", pid);
	v(mutexId);
	printf("process %d exiting\n",pid);

	exit(0);
}

int main(){
	key_t mutexKey= 0x100;
	key_t semkey[] = { 0x200, 0x300, 0x400, 0x500,  0x600 };
	
	int semid[5];
	int i;
	for( i = 0; i < 5 ; i++){
		if ( (semid[i] = initsem(semkey[i],1) ) < 0)
			exit(1);
	}

	for( i = 0; i < 5; i++){
		if(fork() == 0){
			if( i == 4){
				dining(mutexKey,semid[4], semid[0], 4, 0); 
			}
			dining(mutexKey,semid[i], semid[i+1], i, i+1);
		}
	}		

	return 0;
}