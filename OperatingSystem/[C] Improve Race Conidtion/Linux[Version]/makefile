improve.exe : improve.o RaceCondition.o
	gcc -lm -o improve.exe improve.o RaceCondition.o

improve.o : improve.c
	gcc -c improve.c

RaceCondition.o : RaceCondition.c RaceCondition.h
	gcc -c RaceCondition.c

clean :
	rm -f *.exe 
