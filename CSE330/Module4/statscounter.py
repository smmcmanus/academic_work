import sys, os
import urllib2
import re 
import operator

hitsDic = {}
batsDic = {}
r = {}
try:
	year = sys.argv[1]
except IndexError:
	print "Please input a year as a command line argument: 1930, 1940, 1941, 1942, 1943, 1944"
	exit()
url = 'http://classes.engineering.wustl.edu/cse330/content/cardinals/cardinals-' + year + '.txt'
try:
	data = urllib2.urlopen(url)
except urllib2.HTTPError:
	print "Not a valid year"
	exit()
for line in data:
	namepat = "^[A-Z]{1}[a-z]+\s[A-Z]{1}\w+"
	batspat = "batted\s(?P<bats>\d*)"
	hitspat = "with\s(?P<hits>\d*)\shits"
	bats = re.search(batspat, line)
	
	hits = re.search(hitspat, line)
	
	name = re.search(namepat, line)
	
	if name is not None:
		bats = re.findall("\d+", str(bats.groups()))
		hits = re.findall("\d+", str(hits.groups()))
		if name.group(0) in hitsDic:
			hitsDic[name.group(0)] += int(hits[0])
			batsDic[name.group(0)] += int(bats[0])
		else:
			hitsDic[name.group(0)] = int(hits[0])
			batsDic[name.group(0)] = int(bats[0])

for key in hitsDic.keys():
	avg = float(hitsDic[key])/float(batsDic[key])
	avg = round(avg, 3)
	r[key] = avg
sortedreturn = sorted(r.items(), key=operator.itemgetter(1))

for i in range(len(sortedreturn)):
	a = sortedreturn[len(sortedreturn) - i - 1]
	b = str.format('{0:.3f}', a[1])
	print a[0], ": ", b
	
