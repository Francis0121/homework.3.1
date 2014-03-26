use strict;
use warnings;

my $inputfile = 'input.txt';
my $outputfile = 'output.txt';

if(!-e "$inputfile"){
	print "Not exist file [ $inputfile ]\n";
	exit;
}
open(INPUT, "$inputfile"); #Input File 지정

#print "Log : Success load input file\n";

if(!-e "$outputfile"){
	print "Not exist file [ $outputfile ]\n";
	exit;
}else{
	if(!-w "$outputfile"){
		print "Don\'t write this file [ $outputfile]\n";
		exit;
	}
}
# '>' 쓰기모드 '<' 읽기모드 '>>'덧붙여 쓰기 모드
open(OUTPUT, ">>$outputfile"); #Output File 지정

#print "Log : Success load output file\n";

my @lines = <INPUT>; #Input File을 한줄씩 읽어서 저장
foreach my $line (@lines){
	my @words = split / +/, $line;
	
	foreach my $word (@words){
		print OUTPUT "$word\n";
	}
}

#print "Log : Finish Program";

close(INPUT);
close(OUTPUT);