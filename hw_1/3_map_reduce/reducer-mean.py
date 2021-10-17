#! /usr/bin/python3
import sys

count, mean = 0, 0,

for line in sys.stdin:
    count_j, mean_j = map(float, line.split("\t"))

    mean = (count * mean + count_j * mean_j) / (count + count_j)
    count += count_j

print(str(mean), sep="\t")