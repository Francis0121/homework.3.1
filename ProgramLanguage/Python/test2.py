
def sum_many(*args):
	sum = 0
	for i in args:
		sum = sum+i
		print i, sum
	return sum

sum_many(1, 3, 5)