#include "RaceCondition.h"

void addFile(){
	FILE *readf = NULL, *writef = NULL;
	int i;

	printf("Race Condition Start\n");
	for(i=0; i< 100; i++){
		int value;
		value = readFile(readf);
		writeFile(writef, value);
	}
	printf("Race Condition End\n");
}

int readFile(FILE *file){
	int value;
	if( (file = fopen(FILE_NAME, "r")) == NULL){
		return 0;
	}
	while(!feof(file)){
		fscanf(file, "%d", &value);
	}
	fclose(file);
	return value;
}

void writeFile(FILE *file, int value){
	
	if( (file = fopen(FILE_NAME, "a")) == NULL){
		return;
	}
	fprintf(file, "\n%d", value+1);
	fclose(file);
}