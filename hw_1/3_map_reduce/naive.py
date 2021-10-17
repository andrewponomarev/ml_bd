import pandas as pd

df = pd.read_csv('data/AB_NYC_2019.csv')
df = df.dropna(subset=['price'])
mean = df.price.mean()
var = df.price.var(ddof=0)

with open('out/mean.txt', 'a') as f:
	f.write(str(mean))

with open('out/var.txt', 'a') as f:
	f.write(str(var))