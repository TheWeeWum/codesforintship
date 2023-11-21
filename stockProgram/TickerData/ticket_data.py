# Importing the yfinance package
import yfinance as yf

# Set the start and end date
start_date = '2020-01-01'
end_date = '2022-01-01'

# Set the ticker
tickers = []
with open('C:/Users/Liam Csiffary/PycharmProjects/CSC_148/Stocks/TickerData/all_tickers.txt') as td:
    for line in td:
        tickers.append(line.strip())
print(tickers)

# Get the data
for ticker in tickers:
    data = yf.download(ticker, start_date, end_date)
    data["Date"] = data.index
    data.to_csv(ticker+".csv")
