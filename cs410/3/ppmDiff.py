import sys

file1 = open(sys.argv[1],'r')
file2 = open(sys.argv[2],'r')

data1=file1.read().replace('\n', ' ')
data2=file2.read().replace('\n', ' ')
data1 = data1.split()
data2 = data2.split()


diff = open('diff.ppm','w')
diff.write("P3\n")
diff.write(data1[1] + ' '+ data1[2] + ' ' + data1[3] + '\n')

for a,b in zip(data1[4:], data2[4:]):
	diff.write(str(abs(int(a) - int (b))) + ' ')