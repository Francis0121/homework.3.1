# -*- coding: cp949 -*-
inputFile = open("input.txt", 'r') # 'r' 읽기 'w' 쓰기 'a' 추
outputFile = open("output.txt", 'w')

while 1: 
    line = inputFile.readline()
    if not line: break
    
    words = line.split(' ')
    for word in (words):
        word = word.lstrip().rstrip() #앞 뒤 공백제거
        if not word: continue # word에 데이터가 있는지 확인
        outputFile.write("%s\n" % word)

inputFile.close()
outputFile.close()
