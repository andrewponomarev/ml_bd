#! /usr/bin/python3
import sys

count, mean, var = 0, 0, 0

for line in sys.stdin:
    count_j, mean_j, var_j = map(float, line.split("\t"))

    mean = (count * mean + count_j * mean_j) / (count + count_j)
    var = (count * var + count_j * var_j) / (count + count_j) \
          + count * count_j * ((mean - mean_j) / (count + count_j)) ** 2
    count += count_j

print(str(var), sep="\t")