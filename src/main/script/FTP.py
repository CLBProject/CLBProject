import glob
import os
import sys
from ftplib import FTP

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
print(filename)


print ('Ficheiro mais recente: ', filename)


# #copiar o ficheiro para o local que se quer
local_filename = os.path.join(r"D:\Documentos\CBL\Teste", filename)
lf = open(local_filename, "wb")
try:
    ftp.retrbinary("RETR " + filename, lf.write, 8*1024)
    print(f'Ficheiro copiado com sucesso')
except:
    print("Algo errado não está certo")
lf.close

ftp.quit()
