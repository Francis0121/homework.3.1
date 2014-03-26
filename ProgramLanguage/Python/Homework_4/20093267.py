# -*- coding: utf-8 -*-
# --- import ---
import re

while 1:
    path = raw_input("Please enter file name or full file name\n\tinput.txt \n\tC:\\input.txt\n")
    path = path.strip()
    if bool(re.search('.*\.txt', path)):
        break
    
regExps = [ '[0-9]+년\s+[0-9]+월\s+[0-9]+일', '[0-9]+시\s+[0-9]+분', '[0-9]+병|[0-9]+개' ]
befores = [ '<DATE>', '<TIME>', '<QUANTITY>']
afters = ['</DATE>', '</DATE>', '</QUANTITIY>']        

inputFile = open(path, 'r')
outputFile = open('ouput.txt', 'w')

while 1:
    line = inputFile.readline()
    line = line.strip()
    if not line: break
    
    loop = 0
    line = str(unicode(line.encode('utf-8').decode('utf-8')))
    for regExp in regExps:
        regExp = str(unicode(regExp.encode('utf-8').decode('utf-8')))
        if bool(re.search(regExp, line)):
            matches = re.findall(regExp, line)
            for match in matches:
                string = "%s%s%s" %(befores[loop],match,afters[loop])
                line = re.sub(match, string, line, 1)
            
        loop+=1

    print line 
    outputFile.write(line)
    
inputFile.close();
outputFile.close();
    
