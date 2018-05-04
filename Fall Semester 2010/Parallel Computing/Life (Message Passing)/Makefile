PROCS := 1
BOARD := boards/board.txt
TIME := 5
DISPLAY := 1

all: main.o util.o
	mpicc -o laird_morehart_life main.o util.o

main.o: main.c util.h
	mpicc -c main.c

util.o: util.c util.h
	mpicc -c util.c

clean:
	rm *.o laird_morehart_life

run: all
	mpirun -np ${PROCS} -hostfile lamHosts ./laird_morehart_life ${BOARD} ${TIME} ${DISPLAY}

