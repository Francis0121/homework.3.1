use strict;
use warnings;

print "Please insert directory path\n";
my $directoryPath = <STDIN>;
chomp($directoryPath);	# This line mean remove any newline character from the end of a string
						# http://perlmeme.org/howtos/perlfunc/chomp_function.html

#print "Log : Success directory insert [ $directoryPath ]\n";

opendir( DIR, "$directoryPath") || die "Error in opening dir $directoryPath\n";

while( my $fileName = readdir(DIR) ){
	my $regExp = '^.*\.txt$';
	if( $fileName =~ /$regExp/ ){	
		
		my $filePath = "$directoryPath\\$fileName";
		open(INPUT, "$filePath");
		if(!-e INPUT){
			print "Not exist file [ $filePath ]\n";
			exit;
		}
		#print "Log : Success load input file [ $filePath] \n\n";
		
		my @lines = <INPUT>;
		my $lineCount = scalar @lines;
		my $wordCount = 0;

		foreach my $line (@lines) {
			my @words = split / +/, $line;
			$wordCount += scalar @words;
		}
		# 디렉토리명을 입력하면 해당 디렉토리에 있는 각 텍스트 파일(*.txt)에 대해 <파일명, 라인수, 단어수>를 출력하시오.
		print "FileName: $fileName LineCount: $lineCount WordCount: $wordCount\n";
		#모든 텍스트 파일을 1개의 파일로 만드는 프로그램을 작성하시오.(출력 파일명: all_files.txt)
		my $outfileName = 'all_files.txt';
		open(OUTPUT, ">>$outfileName");
		if(!-r OUTPUT){
			print "Don\'t write file [ $filePath ]\n";
			exit;
		}
		foreach my $line (@lines) {
			print OUTPUT "$line";
		}
	
		close(INPUT);
		close(OUTPUT);
	}
}
closedir(DIR);
#print "Log : Program Finish";