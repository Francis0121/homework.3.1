use strict;
use warnings;

#파일정보 입력받음.
print "Please enter file name\n";
my $fileName = <STDIN>;
chomp($fileName);

#입력한 파일이름이 올바르지 않은 경우.
if(not defined $fileName or $fileName eq ''){
	print "The file name is not valid.\n";
	exit;
}

if(!-e "$fileName"){
	print "The file does not exist. [ $fileName ]\n";	
	exit;
}

open(INPUT, "$fileName");
open(OUTPUT, ">output.txt");
my @lines = <INPUT>;
my @regExps = (	'([0-9]+년\s+[0-9]+월\s+[0-9]+일)',
				'([0-9]+시\s+[0-9]+분)',
				'([0-9]+병|[0-9]+개)');
my @before = (	'<DATE>', 
				'<TIME>',
				'<QUANTITY>');
my @after = (	'</DATE>', 
				'</TIME>',
				'</QUANTITY>' );
foreach my $line (@lines){
	
	my $loop = 0;
	foreach my $regExp (@regExps) {	
		if($line =~ /$regExp/){
			$line =~ s/$regExp/$before[$loop]$1$after[$loop]/g;
		}
		$loop += 1;
	}
	print OUTPUT "$line\n";
}

close(INPUT);
close(OUTPUT);

#print "Log : Finish Program";