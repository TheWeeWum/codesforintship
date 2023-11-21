import csv
import os
from algos import *
import matplotlib.pyplot as plt

#############################################################################
# GETS INITIAL DICTIONARY TO USE FOR CALCULATIONS
#############################################################################
path = "C:/Users/Liam Csiffary/PycharmProjects/CSC_148/Stocks/TickerData"
# path = "C:/Users/liamc/PycharmProjects/CSC_148/Stocks/TickerData"

dir_list = os.listdir(path)

# [ticker: {date, open, high, low, close}]
tickers = []
for file in dir_list:
    file_loc = f"{path}/{file}"
    dic = {}
    with open(file_loc, 'r') as f:
        if file == 'ticket_data.py' or file == 'all_tickers.txt':
            continue
        dic['name'] = file
        dic['date'] = []
        dic['open'] = []
        dic['high'] = []
        dic['low'] = []
        dic['close'] = []
        reader = csv.reader(f)
        i = 0
        for row in reader:
            if i == 0:
                i = 1
                continue
            parts = row
            dic['date'].append(parts[0])
            dic['open'].append(float(parts[1]))
            dic['high'].append(float(parts[2]))
            dic['low'].append(float(parts[3]))
            dic['close'].append(float(parts[4]))
    if len(dic['date']) == 0:
        continue
    else:
        tickers.append(dic)

# print(tickers)

#############################################################################
# INITIAL CONDITIONS
#############################################################################
initial_money = 1000000
money = initial_money
num_buy = 25
owned_stocks = {}


############
# conditions
############
def ave(lst):
    return sum(lst) / len(lst)


def double_ave(lst, num=100):
    i = 0
    total = 0
    new_ave = []
    while i < len(lst):
        total += lst[i]
        if i % num == 0:
            new_ave.append(total / num)
            total = 0
        i += 1
    new_ave.append(total / (i % num))
    return new_ave


rudi = RudiAlgo(initial_money, num_buy, tickers)
reversed_rudi = ReversedRudiAlgo(initial_money, num_buy, tickers)
# cont = Control(initial_money, tickers)
sim_anneal = SimulatedAnnealing(initial_money, tickers, 5, 200, num_buy, 2, 2, 2, 2)

rud_prof = rudi.calculate_profit()
rev_rud_prof = reversed_rudi.calculate_profit()
# control_prof = cont.calculate_profit()
sim_prof = sim_anneal.calculate_profit()

print(f"Rudimentary algorithm {ave(rud_prof)}")
print(f"Reverse rud algorithm {ave(rev_rud_prof)}")
# print(f"Control ___ algorithm {ave(control_prof)}")
print(f"Simu anneal algorith {ave(sim_prof)}")
print("PLOTTING...")

num = 100
ticker_num = range(len(double_ave(sim_prof, num)))
plt.plot(ticker_num, double_ave(rud_prof, num), color='red')
plt.plot(ticker_num, double_ave(rev_rud_prof, num), color='blue')
# plt.plot(ticker_num, double_ave(control_prof, num), color='black')
plt.plot(ticker_num, double_ave(sim_prof, num), color='orange')

plt.show()
