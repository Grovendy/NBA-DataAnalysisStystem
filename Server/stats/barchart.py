# -*- coding:utf-8 -*-



import numpy as np
import matplotlib.pyplot as plt

f = file('stats/BarChart.txt')

temp = f.readline().split(';')
a = temp[0]
b = temp[1]
season = temp[2]
regular = temp[3].replace('\n', '')

temp = f.readline().split(';')
labels = []
for item in temp:
    labels.append(item)

data = np.loadtxt(f, delimiter=";", dtype=float)
if isinstance(data[0], float):
    max_data = max(data[0], data[1])
else:
    max_data = max(max(data[0]), max(data[1]))

ind = np.arange(len(labels))
width = 0.33

fig, ax = plt.subplots()
rects1 = ax.bar(ind+0.33, data[0], width, color='#ED5565', edgecolor='white')
rects2 = ax.bar(ind+0.33+width, data[1], width, color='#48CFAD', edgecolor='white')

if max_data > 10:
    plt.axis([0, len(labels)+0.5, 0, max_data+5])
else:
    plt.axis([0, len(labels)+0.5, 0, max_data+1])


ax.legend((rects1[0], rects2[0]), (a, b))
ax.set_xticks(ind+width+0.33)
ax.set_xticklabels(labels)


def autolabel(rects):
    # attach some text labels
    for rect in rects:
        height = rect.get_height()
        ax.text(rect.get_x()+rect.get_width()/2., 1.02*height, '%.2f' % height,
                ha='center', va='bottom')
autolabel(rects1)
autolabel(rects2)

plt.savefig('stats/BarChart.png')
plt.savefig('stats/BarChart_' + a + '_' + b + '_' + season + '_' + regular + '.png')