use strict;
use warnings;

#올바른 Command를 입력할때까지 무한 loop
my ($command, $string, $path) = @ARGV;
my @commands = ('');

until((defined $command and $command eq 'grep') and defined $string and defined $path ){
	print "Command line argument is not valid.\nFormat \" grep \'string\' \'path\' \". Please re-enter.\n";
	$command = <STDIN>;
	chomp($command);
	
	@commands = split / +/, $command;
	my $commandCount= scalar @commands;

	#Command가 올바른지 올바르지 않은지 판단.
	$command = $commands[0];
	if($commands[0] ne 'grep' or $commandCount != 3){
		print "Command is invalid.\n";
		$command = '';
	}else{
		$string = $commands[1];
		$path = $commands[2];
	}
}

my $regExp = '^.*\.txt$'; #.txt확장자가 붙은 파일인지 경로인지 판단.
if( $path =~ /$regExp/ ){
	#.txt 확장자 존재 (파일이라면) 파일 존재여부 판단,
	#존재하지 않는다면 프로그램 종료
	if(!-e "$path"){
		print "Not exist file [ $path ]\n";	
		exit;
	}

	open(FILE, "$path"); #Input File 지정
	my @lines = <FILE>; #Input File을 한줄씩 읽어서 저장
	foreach my $line (@lines){
		if($line =~ /$string/){
			print "$line";
		}
	}
	close(FILE);
}else{# .txt 확장자가 붙지 않았다면 무조건 경로로 판단한다.

	opendir( DIR, "$path") || die "Error in opening dir $path\n";

	while( my $fileName = readdir(DIR) ){
		my $regExp = '^.*\.txt$';
		if( $fileName =~ /$regExp/ ){	
			
			my $filePath = "$path\\$fileName";
			
			open(FILE, "$filePath");
			#print "Log : Success load input file [ $filePath] \n";
			my @lines = <FILE>;
			
			print "$filePath\n";
			foreach my $line (@lines) {
				if($line =~ /$string/){
					print "$line";
				}
			}
			print "\n";
			close(FILE);
		}
	}

	closedir(DIR);
}

#print "Log : Program Finish";