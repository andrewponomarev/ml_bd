# ml_bd

Поднять hadoop кластер 
```
docker-compose -f block_1/docker-hadoop/docker-compose.yml up
```
Кластер будет доступен по адресу http://localhost:9870/

Resource manager http://localhost:8089/cluster/apps/RUNNING

1. [Скрины поднятого кластера](/1_images)

2. [Команды](/2_commands)

3. Map-reduce

3.1. Поcчитать среднее  
```
cat 3_map_reduce/data/AB_NYC_2019.csv | python  3_map_reduce/mapper-mean.py  | python 3_map_reduce/reducer-mean.py
```
[mapper-mean.py](/3_map_reduce/mapper-mean.py)

[reducer-mean.py](/3_map_reduce/reducer-mean.py)

3.2. Поcчитать дисперсию  
```
cat 3_map_reduce/data/AB_NYC_2019.csv | python  3_map_reduce/mapper-mean.py  | python 3_map_reduce/reducer-mean.py
```
[mapper-var.py](/3_map_reduce/mapper-var.py)

[reducer-var.py](/3_map_reduce/reducer-var.py)


Наивный подсчет: [naive.py](/3_map_reduce/naive.py)

Результаты вычислений лежат в [out](/3_map_reduce/out) - первое значение - резудьтат map-reduce, второе - стандартных вычислений
