file = open('diff.ppm','r')

data=file.read().replace('\n', ' ')
data = data.split()


diff = open('diff_enhanced.ppm','w')
diff.write("P3\n")
diff.write(data[1] + ' '+ data[2] + ' ' + data[3] + '\n')

for a in data[4:]:
	diff.write(str(min(255,int(a)**2)) + ' ')