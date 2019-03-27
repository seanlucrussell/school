import sys

file1 = open(sys.argv[1],'r')
file2 = open(sys.argv[2],'r')

data1=file1.read().replace('\n', '')
data2=file2.read().replace('\n', '')
for a,b in zip(data1.split(), data2.split()):
	try:
		if abs(int(a) - int(b)) > 1:
			print (a, 'changed to', b)
	except:
		continue