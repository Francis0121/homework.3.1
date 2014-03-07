use strict;
use warnings;

my ($fileName) = @ARGV;
#file 명이 입력 되지 않았으면 file명 입력하라고 요청.
until(defined $fileName){
	print "Command line argument is not valid.\nFormat is\'file name\'. Please re-enter.\n";
	$fileName = <STDIN>;
	chomp($fileName);
}
#file 존재 유무 체크
if(!-e "$fileName"){
	print "The file does not exist. [ $fileName ]\n";	
	exit;
}
#file 존재한 경우 file에 대한 명령어 실행
my $command = '';
while(1){	
	print "Input Command : ";
	$command = <STDIN>;
	chomp($command);

	if($command eq 'word-count' or $command eq 'count' ){
		open(INPUT, "$fileName");
		my @lines = <INPUT>;

		my %wordHash;
		foreach my $line (@lines){
			my @words = split / +/, $line;
			foreach my $word (@words) {
				if(exists $wordHash{$word} eq 1){
					$wordHash{$word} += 1;
				}else{
					$wordHash{$word} = 1;
				}
			}
		}
		my @keys = sort(keys %wordHash);
		foreach my $key (@keys) {
			print "$key $wordHash{$key}\n";
		}
		print "\n[Finished]\n";
		close(INPUT);
	}elsif($command eq 'word-replace' or $command eq 'replace'){
		my $replaceCommand = '';
		my @words = ();
		my @replaceWords = ();
	
		while(1){
			print "word-replace > ";
			$replaceCommand = <STDIN>;
			chomp($replaceCommand);

			if($replaceCommand eq 'edit'){
				print "Input word : ";
				my $word = <STDIN>;
				chomp($word);
				push(@words, $word);
				
				print "Input Replace word : ";
				my $replaceWord = <STDIN>;
				chomp($replaceWord);
				push(@replaceWords, $replaceWord);
				
			}elsif($replaceCommand eq 'print'){
				open(INPUT, "$fileName");
				my @lines = <INPUT>;

				my $loop = 0;				
				foreach my $word (@words) {
					foreach my $line (@lines) {
						$line =~ s/$word/$replaceWords[$loop]/g;
					}
					$loop+=1;
				}
				close(INPUT);

				print "Input Newfile name : ";
				my $newFileName = <STDIN>;
				
				open(OUTPUT, ">$newFileName");
				foreach my $line (@lines) {
					print "$line\n";
					print OUTPUT "$line\n";
				}
				close(OUTPUT);
				last;
			}else{
				print "[edit, print] can be input. Please re-enter command\n";
			}
		}
	}elsif($command eq 'quit' or $command eq 'exit'){
		exit;
	}else{
		print "[word-count, count, word-replace, replace, quit, exit] can be input. Please re-enter command\n";
	}
}

#print "Log : Program Finish";