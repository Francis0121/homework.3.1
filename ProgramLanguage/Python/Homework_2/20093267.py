# -*- coding: cp949 -*-
import glob
import os

directoryPath = raw_input("Please enter directory path\n")
directoryPath = "%s\*.txt" %directoryPath

files = glob.glob(directoryPath) 

outputFile = open("all_files.txt", 'w')
for fileName in (files):
    inputFile = open(fileName, 'r') # 'r' 읽기 'w' 쓰기 'a' 추가

    wordCount = 0
    lineCount = 0
    while 1: 
        line = inputFile.readline()
        line = line.strip()
        if not line: break
        lineCount+=1
        outputFile.write(line)
        words = line.split(' ')
        for word in (words):
            word = word.strip() #앞 뒤 공백제거
            if not word: continue # word에 데이터가 있는지 확인
            wordCount+=1

    print("FileName : %s, Word : %d, Line : %d" %(os.path.basename(fileName),wordCount,lineCount) )
    inputFile.close()

outputFile.close()
