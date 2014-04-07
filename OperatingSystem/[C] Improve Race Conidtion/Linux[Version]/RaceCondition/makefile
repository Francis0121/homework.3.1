main.exe : main.o RaceCondition.o
	gcc -lm -o main.exe main.o RaceCondition.o

main.o : main.c
	gcc -c main.c

RaceCondition.o : RaceCondition.c RaceCondition.h
	gcc -c RaceCondition.c

clean :
	rm -f *.exe 
