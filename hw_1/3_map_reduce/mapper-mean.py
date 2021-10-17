#! /usr/bin/python3
import sys
import csv

PRICE_COLUMN_ID = 9

count, mean = 0, 0

for line in csv.reader(sys.stdin):
    try:
        price = float(line[PRICE_COLUMN_ID])
    except ValueError:
        continue
    mean = (count * mean + price) / (count + 1)
    count += 1

print(str(count), str(mean), sep="\t")
