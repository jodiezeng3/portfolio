from selenium import webdriver
from bs4 import BeautifulSoup
import pandas as pd
from selenium.webdriver.chrome.service import Service #this and next import I used different thing from stackoverflow(see readme) 
from webdriver_manager.chrome import ChromeDriverManager

#configure webdriver to use the Chrome browser - set the path to chromedriver
driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()))

#code to open the url
products = []
ratings = []
prices = []
driver.get("https://www.flipkart.com/laptops-store?otracker=nmenu_sub_Electronics_0_Laptops")

#extract data from website using the class from the div tags
content = driver.page_source
soup = BeautifulSoup(content, "html.parser") #used stackoverflow 2 to remove warning from this line
for a in soup.findAll('div', attrs={'class':'_1kLt05'}):
    name=a.find('div', attrs={'class':'_1W9f5C'})
    products.append(name.get_text())
    rating = a.find('div', attrs={'class':'_3LWZlK'})
    ratings.append(rating.get_text())
    price = a.find('div', attrs={'class':'_30jeq3 UMT9wN'})
    prices.append(price.get_text())

#output to a csv file
df = pd.DataFrame({'Product Name':products,'Price':prices,'Rating':ratings}) 
df.to_csv('products.csv', index=False, encoding='utf-8')