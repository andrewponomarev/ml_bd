#! /usr/bin/python3
import sys
import csv

PRICE_COLUMN_ID = 9

count, mean, mean_of_squares = 0, 0, 0

for line in csv.reader(sys.stdin):
    try:
        price = float(line[PRICE_COLUMN_ID])
    except ValueError:
        continue
    mean = (count * mean + price) / (count + 1)
    mean_of_squares = (count * mean_of_squares + price ** 2) / (count + 1)
    count += 1

var = mean_of_squares - mean ** 2

print(str(count), str(mean), str(var), sep="\t")
