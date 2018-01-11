import glob
import os
import sys
import csv
from ftplib import FTP

import csv

# Ligar ao FTP e à pasta processed
ftp = FTP('ftp.dexcell.com')
ftp.login(user='122788', passwd='171064')
print(ftp.getwelcome())
ftp.cwd('/processed')
print('A pasta actual: ', ftp.pwd())

#cria lista com todos o ficheiros da pasta e depois escolher o mais recent
data = []
ftp.retrlines("LIST", (data.append))
words = data[-1].split(None, 8)
filename = words[-1].lstrip()

ftp.quit()
