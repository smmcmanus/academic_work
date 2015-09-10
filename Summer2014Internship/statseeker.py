#!/usr/bin/python
# -*- coding: utf-8 -*-
"""
Created on Wed Nov 13 19:52:34 2013

@author: jroman
"""

#>>> time.ctime(int("1284101485"))
#'Fri Sep 10 16:51:25 2010'
#>>> time.strftime("%D %H:%M", time.localtime(int("1284101485")))
#'09/10/10 16:51'

import sys
import time
import datetime
import math

def is_number(s):
    try:
        float(s)
        return True
    except ValueError:
        return False

minutesPerDay = 24 * 60 * 60
rx = []
tx = []
for cell in range (22):
    rx.append(0)
    tx.append(0)

listLength = 10**100

files = sys.argv
for argIndex in range(1, len(sys.argv)):
    #print sys.argv[argIndex]
    #print argIndex
    try:
        infile = open(sys.argv[argIndex],"r")
        # ignore the first line
    except IOError:
        print "File can not be found"
    dat = infile.readline()
    for line in range(2):
        dat = infile.readline()
        list = dat.split(',')
        listLen = len(list)
        if listLen < listLength:
		listLength = listLen
        #print listLength
        Start = eval(list[7])
        dayTime = Start
        Interval = eval(list[14])
        # print eval(list[14])
        # print time.ctime(Start), Interval, Count
        
        if ( argIndex == 1 ):
            for cell in range(22, listLength):
                # print cell, list[cell]
                if ( is_number(list[cell]) ):
                    value = eval(list[cell])
                else:
                    value = 0
                if ( line == 1):
                    rx.append(value)
                else:
                    tx.append(value)
        else:
            cell = 22
            while cell < listLength:
                if ( is_number(list[cell]) ):
                    value = eval(list[cell])
                else:
                    value = 0 
                if ( line == 1):
                    rx[cell] += value
                else:
                    tx[cell] += value
                # print cell, rx[cell], tx[cell]
                cell += 1
    infile.close
     
cell = 22
dayTime = Start
print "Date\tRX Average\tRX Peak\tTX Average\tTX Peak\tRX90\tTX90"
while ( cell < listLength ):
    dayStart = dayTime
    rxdayAve = 0
    txdayAve = 0
    rxdayPeak = 0
    txdayPeak = 0
    rx90per = 0
    tx90per = 0
    dayI = 0
    if ( (listLength - cell) < minutesPerDay/Interval ):
        endRange = listLength - cell
    else:
        endRange = minutesPerDay/Interval
    dayTXList = []
    dayRXList = []
    for i in range(0, endRange):
        dayI += 1
        rxvalue = rx[cell]
        dayRXList.append(rxvalue)
        rxdayAve += rxvalue
        if (rxvalue > rxdayPeak):
            rxdayPeak = rxvalue
        txvalue = tx[cell]
        dayTXList.append(txvalue)
        txdayAve += txvalue
        if (txvalue > txdayPeak):
            txdayPeak = txvalue
        cell += 1
        dayTime += Interval
    rxdayAve = rxdayAve / dayI
    txdayAve = txdayAve / dayI
    dayRXList.sort()
    dayTXList.sort()
    for j in range(0, int(len(dayRXList) * 0.5)):
        rx90per += dayRXList[j]
    for j in range(0, int(len(dayTXList) * 0.5)):
        tx90per += dayTXList[j]
    rx90per = dayRXList[int(len(dayRXList) * 0.9)]
    tx90per = dayTXList[int(len(dayTXList) * 0.9)]
    dt = datetime.datetime.fromtimestamp(int(dayStart))
    print dt.strftime("%Y/%m/%d %H:%M"), '\t', rxdayAve,'\t', rxdayPeak, '\t',txdayAve, '\t', txdayPeak, '\t', rx90per, '\t', tx90per
    
