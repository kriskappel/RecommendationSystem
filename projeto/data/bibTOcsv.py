papersIn  = open('biblioteca.bib', 'r')
papersOut = open('biblioteca.csv', 'w')

title = ""
i=0

output = "paperId,title\n"

for line in papersIn:
	
	if line[:5] == 'title':
		title = line[10:-4] 
		title = title.replace(',', ' ')
		title = title.replace(';', ' ')

		i = i +1
		output = output + str(i) + "," + title + '\n'
		#print title
	

papersOut.write(output)

papersIn.close()
papersOut.close()

userIn  = open('ratings.csv', 'r')
userOut = open('usuarios.csv', 'w')
j=0
flag_first = False

substring=''

output = ""

for line in userIn:

	if not flag_first:
		substring = substring+line
		#print substring
		flag_first = True
	else:
		j+=1
		first = 0
		second = 0
		start = ""
		ending = ""

		for i in range(0, len(line)):
			if(line[i] == ',' and first == 0):
				first = i
				start = line[:first + 1] 
			elif(line[i] == ',' and second == 0):
				second = i
				ending = line[second:]
				break

		if(first+2 == second):
			substring = line[first + 1:second]
		else:
			substring = line[first + 1:first +4]
		substring = start + substring + ending

		
		for i in range(0, len(substring)):
			if(substring[i] == ',' and substring[i+1] == ','):
				substring = substring[:i] + substring[i+1:]
				break


		#if(j==20):
		#	break
		#print line + " " + substring

	output = output+substring

userOut.write(output)