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
	fopen_s(&file, FILE_NAME, "r");
	if(file == NULL){
		return 0;
	}
	while(!feof(file)){
		fscanf_s(file, "%d", &value);
	}
	fclose(file);
	return value;
}

void writeFile(FILE *file, int value){
	fopen_s(&file, FILE_NAME, "a");
	if(file == NULL){
		return;
	}
	fprintf(file, "\n%d", value+1);
	fclose(file);
}