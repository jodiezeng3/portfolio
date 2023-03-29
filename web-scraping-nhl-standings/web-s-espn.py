from selenium import webdriver
from bs4 import BeautifulSoup
import pandas as pd
from selenium.webdriver.chrome.service import Service #this and next import I used different thing from stackoverflow(see readme) 
from webdriver_manager.chrome import ChromeDriverManager

#configure webdriver to use the Chrome browser - set the path to chromedriver
driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()))

#code to open the url
teams = []
games_played = []
wins = []
losses = []
ot_losses = []
points = []
reg_wins = []
reg_ot_wins = []
so_wins = []
so_losses = []
driver.get("https://www.espn.com/nhl/standings/_/group/league")

#extract data from website using the class from the div tags
content = driver.page_source
soup = BeautifulSoup(content, "html.parser") #used stackoverflow 2 to remove warning from this line

#get all the team abbreviations
for ab in soup.findAll('span', attrs={'class':'dn show-mobile'}):
    team=ab.find('abbr')
    teams.append(team.get_text())

#want to next get all the stats for the teams in order of the team to match up with the teams list
for i in range(len(teams)):
    for tr in soup.findAll('tr', style=False, attrs={'data-idx':str(i)}):
        stats = tr.findAll('span', attrs={'class':'stat-cell'})
        #0 - GP, 1 - W, 2 - L, 3 - OTL, 4 - Pts, 5 - RW, 6 - ROW, 7 - SOW, 8 - SOL, 9 - HOME, 10 - AWAY, 11 - GF, 12 - GA, 13 - DIFF
        games_played.append(stats[0].get_text())
        wins.append(stats[1].get_text())
        losses.append(stats[2].get_text())
        ot_losses.append(stats[3].get_text())
        points.append(stats[4].get_text())
        reg_wins.append(stats[5].get_text())
        reg_ot_wins.append(stats[6].get_text())
        so_wins.append(stats[7].get_text())
        so_losses.append(stats[8].get_text())
        #print(stats)
        #k = 0
        #for s in stats:
        #    print(str(k) + ": " + s.get_text())
        #    k += 1

#output to a csv file
df = pd.DataFrame({'Team Name':teams, 'Games Played':games_played, 'Wins':wins, 'Losses':losses, 'OT Losses':ot_losses, 'Regulation Wins':reg_wins, 'Reg OT Wins':reg_ot_wins, 'SO Wins':so_wins, 'SO Losses':so_losses, 'Points':points, }) 
df.to_csv('nhl_standings.csv', index=False, encoding='utf-8')