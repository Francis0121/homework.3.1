# -*- coding: cp949 -*-
# --- import ---
import sys
import re
import glob
import os

# --- Method ---
def checkSystemArgument(args):
    
    if ((not args) or (args[0] != "grep") or (len(args) != 3) or (not args[1]) or (not args[2])) :
        print("Please enter command : \n\tgrep 'statement' 'fileName' \n\tgrep 'statement' 'directory'")
        return 0
    return 1

def stringMatchFile(fileName, string) :
    inputFile = open(fileName, 'r')

    while 1:
        line = inputFile.readline()
        line = line.strip()
        if not line: break

        isPrint = 0

        regExp = "%s" %string
        if re.search(regExp, line):
            print(" ".join(line.split())) # 개행문자제거 
            
    inputFile.close()
                
# --- Main ---
# 정확한 grep 'statement' 'fileName' format이 들어올때 까지 입력받음
args = sys.argv[1:]

while 1:
    if not checkSystemArgument(args):
        temp = raw_input('')
        args = temp.split(' ')
    else:
        break

string = args[1]
path = args[2]

if bool(re.search('.*\.txt', path)) :
    stringMatchFile(path, string) 
else :
    path = "%s\*.txt" %path
    fileNames = glob.glob(path)
    for fileName in (fileNames):
        print ("-------------------------------")
        print ("%s" %os.path.basename(fileName))
        stringMatchFile(fileName, string)
        print ("-------------------------------\n")
