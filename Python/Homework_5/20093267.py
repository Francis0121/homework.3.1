# -*- coding: utf-8 -*-
# --- import ---
import sys
import re

# --- Main ---
path = args = sys.argv[1:]
while 1:
    path = raw_input("Please enter file name or full file name\n\tinput.txt \n\tC:\\input.txt\n")
    path = path.strip()
    if bool(re.search('.*\.txt', path)):
        break

while 1:
    command = raw_input("Please enter command.\n\t1. word-count, count, wc \n\t2. word-replace, replace, wr \n\t3. quit, exit\n")
    command = command.strip()
    if ( command == 'word-count' or command == 'count' or command == 'wc') :
        print "----------------------------"
        inputFile = open(path, 'r')
    
        wordHashs = { }
        while 1:
            line = inputFile.readline()
            line = line.strip()
            if not line: break

            words = line.split(' ')
            for word in words:
                word = word.strip()
                if word not in wordHashs :
                    wordHashs[word] = 1
                else:
                    wordHashs[word]+=1

        for wordHash in wordHashs:
            print "%s %d" %(wordHash, wordHashs[wordHash])        
        inputFile.close()
        print "----------------------------"
    elif ( command == 'word-replace' or command == 'replace' or command == 'wr'):
        print "----------------------------"
        beforeWords = []
        afterWords = []
        while 1:
            replaceCommand = raw_input("Please enter Command.\n\t1. edit,e \n\t2. print p \n\t3. quit,exit \nword-replace > ")
            replaceCommand.strip()
       
            
            if( replaceCommand == 'edit' or replaceCommand == 'e'):
                beforeWord = raw_input("Input word : ")
                beforeWord = beforeWord.strip()

                afterWord = raw_input("Input replace word : ")
                afterWord = afterWord.strip()

                beforeWords.append(beforeWord)
                afterWords.append(afterWord)
            elif( replaceCommand == 'print' or replaceCommand == 'p'):
                inputFile = open(path, 'r')
                outputFile = open('output.txt', 'w')
                while 1:
                    line = inputFile.readline()
                    line = line.strip()
                    if not line: break

                    loop = 0
                    for beforeWord in beforeWords:
                        regExp = "%s" %beforeWord
                        if bool(re.search(regExp, line)):
                            line = re.sub(regExp, afterWords[loop], line)
                        loop+=1
                    
                    outputFile.write("%s\n"%line)
                    
                beforeWords = []
                afterWords = []
                outputFile.close()
                print "Remove replace word"
                print "----------------------------"
            elif( replaceCommand == 'quit' or replaceCommand == 'exit'):
                print "----------------------------"
                break
            else:
                continue
            
        print "----------------------------"
    elif ( command == 'quit' or command == 'exit') :
        print "Bye!"
        break
    else :
        continue
