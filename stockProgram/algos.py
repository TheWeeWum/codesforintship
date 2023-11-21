import random
import math


class RudiAlgo:
    def __init__(self, initial_money, num_buy, tickers):
        self.initial_money = initial_money
        self.num_buy = num_buy
        self.money = initial_money
        self.owned_stocks = 0
        self.tickers = tickers
    def rudimentary_buy(self, current_price, last_price, name) -> bool:
        if current_price < last_price:
            if self.money > current_price * self.num_buy:
                self.money -= current_price * self.num_buy
                self.owned_stocks += self.num_buy

    def rudimentary_sell(self, current_price, last_price, name) -> bool:
        if current_price > last_price:
            if self.owned_stocks > self.num_buy:
                self.money += current_price * self.num_buy
                self.owned_stocks -= self.num_buy

    def _net_worth(self, curr_price) -> float:
        return self.money + self.owned_stocks * curr_price

    def calculate_profit(self) -> list[float]:
        lst = []
        for ticker in self.tickers:
            self.money = self.initial_money
            last_price = ticker['open'][0]
            cur_p = last_price
            name = ticker['name']
            self.owned_stocks = 0
            for i in range(len(ticker['date'])):
                opens = ticker['open'][i]
                close = ticker['close'][i]

                cur_p = opens
                self.rudimentary_sell(cur_p, last_price, name)
                self.rudimentary_buy(cur_p, last_price, name)
                last_price = cur_p

                cur_p = close
                self.rudimentary_sell(cur_p, last_price, name)
                self.rudimentary_buy(cur_p, last_price, name)
                last_price = cur_p
            lst.append(self._net_worth(cur_p) - self.initial_money)
        return lst


class ReversedRudiAlgo(RudiAlgo):
    def rudimentary_buy(self, current_price, last_price, name) -> bool:
        if current_price > last_price:
            if self.money > current_price * self.num_buy:
                self.money -= current_price * self.num_buy
                self.owned_stocks += self.num_buy

    def rudimentary_sell(self, current_price, last_price, name) -> bool:
        if current_price < last_price:
            if self.owned_stocks > self.num_buy:
                self.money += current_price * self.num_buy
                self.owned_stocks -= self.num_buy


class Control:
    def __init__(self, initial_money, tickers):
        self.initial_money = initial_money
        self.tickers = tickers

    def calculate_profit(self) -> list[float]:
        lst = []
        for ticker in self.tickers:
            stocks = self.initial_money / ticker['open'][0]
            lst.append(stocks*ticker['close'][-1] - self.initial_money)
        return lst


class SimulatedAnnealing:
    def __init__(self, initial_money, tickers, initial_temperature=1, iterations=500, num_buy=50,
                 buy_percent=1, sell_percent=1, buy_relative=1, sell_relative=1):
        self.initial_temperature = initial_temperature
        self.initial_money = initial_money
        self.num_buy = num_buy
        self.money = initial_money
        self.owned_stocks = 0
        self.tickers = tickers
        self.iterations = iterations

        self.b_p = buy_percent
        self.s_p = sell_percent
        self.b_r = buy_relative
        self.s_r = sell_relative
        self.max_buy_percent = 100
        self.max_sell_percent = 100
        self.max_buy_relative = 100
        self.max_sell_relative = 100

    def calculate_profit(self):
        best = float('-inf')
        old = 0
        buy_percent = self.b_p    # if stock is this much more than last -> buy
        sell_percent = self.s_p   # if stock is this much less than last -> sell
        buy_relative = self.b_r   # if stock has gone up this much since last buy -> buy
        sell_relative = self.s_r  # if stock has gone down this much since last sell -> sell
        best_params = (buy_percent, sell_percent, buy_relative, sell_relative)
        for i in range(self.iterations):
            if self.iterations == 1:
                t = self.initial_temperature
            else:
                t = self.initial_temperature * (1 - i / (self.iterations - 1))

            # SET VARS
            self.new_params()
            lst = self.calc_prof(250)
            result = sum(lst) / len(lst)
            # print(f"Profits: {result} Stats: {buy_percent, sell_percent, buy_relative, sell_relative}\n"
            #       f"{self.b_p, self.s_p, self.s_r, self.b_r}\n"
            #       f"Best: {best}")

            if result > best:
                best = result
                old = result
                buy_percent, sell_percent, buy_relative, sell_relative = self.b_p, self.s_p, self.b_r, self.s_r
                best_params = (buy_percent, sell_percent, buy_relative, sell_relative)
                print(f"Profits: {result} Stats: {buy_percent, sell_percent, buy_relative, sell_relative}\n"
                      f"{self.b_p, self.s_p, self.s_r, self.b_r}\n"
                      f"Best: {best}")

            elif self.accept(old, result, t, i):
                old = result
                buy_percent, sell_percent, buy_relative, sell_relative = self.b_p, self.s_p, self.b_r, self.s_r
            else:
                self.b_p, self.s_p, self.b_r, self.s_r= buy_percent, sell_percent, buy_relative, sell_relative

        print(best_params, best)
        self.b_p, self.s_p, self.b_r, self.s_r = best_params
        return self.calc_prof()

    def rudimentary_buy(self, current_price, last_price) -> bool:
        if current_price * self.b_p > last_price:
            if self.money > current_price * self.num_buy:
                self.money -= current_price * self.num_buy
                self.owned_stocks += self.num_buy
                return True
        return False

    def rudimentary_sell(self, current_price, last_price) -> bool:
        if current_price * self.s_p < last_price:
            if self.owned_stocks >= self.num_buy:
                self.money += current_price * self.num_buy
                self.owned_stocks -= self.num_buy
                return True
        return False

    def relative_buy(self, current_price, last_buy_price) -> bool:
        if current_price * self.b_r > last_buy_price:
            if self.money > current_price * self.num_buy:
                self.money -= current_price * self.num_buy
                self.owned_stocks += self.num_buy
                return True
        return False

    def relative_sell(self, current_price, last_sell_price) -> bool:
        if current_price * self.s_r < last_sell_price:
            if self.owned_stocks >= self.num_buy:
                self.money += current_price * self.num_buy
                self.owned_stocks -= self.num_buy
                return True
        return False

    def _net_worth(self, curr_price) -> float:
        return self.money + self.owned_stocks * curr_price

    def calc_prof(self, num=0) -> list[float]:
        lst = []
        if num != 0:
            tickers = self.tickers[:num]
        else:
            tickers = self.tickers

        for ticker in tickers:
            self.money = self.initial_money
            cur_p = ticker['open'][0]
            last_price, last_sell_price, last_buy_price = cur_p, cur_p, cur_p
            self.owned_stocks = 0

            for i in range(len(ticker['date'])):
                opens = ticker['open'][i]
                close = ticker['close'][i]

                # set current price
                cur_p = opens

                # buy/sell step
                sold = self.rudimentary_sell(cur_p, last_price)
                if sold:
                    last_sell_price = cur_p
                bought = self.rudimentary_buy(cur_p, last_price)
                if bought:
                    last_buy_price = cur_p

                sold = self.relative_sell(cur_p, last_sell_price)
                if sold:
                    last_sell_price = cur_p
                bought = self.relative_buy(cur_p, last_buy_price)
                if bought:
                    last_sell_price = cur_p

                # set current/last price
                last_price, cur_p = cur_p, close

                # buy/sell step
                sold = self.rudimentary_sell(cur_p, last_price)
                if sold:
                    last_sell_price = cur_p
                bought = self.rudimentary_buy(cur_p, last_price)
                if bought:
                    last_buy_price = cur_p

                sold = self.relative_sell(cur_p, last_sell_price)
                if sold:
                    last_sell_price = cur_p
                bought = self.relative_buy(cur_p, last_buy_price)
                if bought:
                    last_sell_price = cur_p

                # set last price
                last_price = cur_p

            lst.append(self._net_worth(cur_p) - self.initial_money)
        return lst

    def new_params(self):
        # TODO: THIS IS TEMPORARY SHOULD HAVE A BETTER METHOD
        # Which uses the last turns relative success to determine how to manipulate them
        self.b_p = 1 + ((random.random()-0.5)*2 / 100 * self.max_buy_percent)
        self.s_p = 1 + ((random.random()-0.5)*2 / 100 * self.max_sell_percent)
        self.b_r = 1 + ((random.random()-0.5)*2 / 100 * self.max_buy_relative)
        self.s_r = 1 + ((random.random()-0.5)*2 / 100 * self.max_sell_relative)

    @staticmethod
    def accept(old_score: float, new_score: float, temperature: float, seed: int
               ) -> bool:
        """If <new_score> is at least as high as <old_score>, return True.
        Otherwise, return True with probability
            exp((<new_score> - <old_score>) / <temperature>)
        unless <temperature> is 0, in which case, return False.
        """
        diff = new_score - old_score

        if diff >= 0:
            return True
        elif temperature == 0:
            return False

        rnd = random.Random(seed)
        return rnd.random() < math.exp(diff / temperature)

