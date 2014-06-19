import sys
import os
import glob

def lineCount(fileName):
	inputFile = open(fileName, 'r')
	lCount = 0
	while 1: 
		line = inputFile.readline()
		line = line.strip()
		if not line : break
		lCount+=1
	return lCount

args = sys.argv[1:]
totalCount = 0

for fileName in (args) :
	mlCount = lineCount(fileName)
	totalCount += mlCount
	print("%s : %d lines " %(os.path.basename(fileName), mlCount))

print("Total %d lines. " %(totalCount) )