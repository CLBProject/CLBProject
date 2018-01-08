import glob
import os
import sys
import configparser
from ftplib import FTP

def processUserFtp(userStr, passStr):
    # Ligar ao FTP e à pasta processed
    ftp = FTP('ftp.dexcell.com')
    ftp.login(userStr,passStr)
    print(ftp.getwelcome())
    ftp.cwd('/processed')
    
    #cria lista com todos o ficheiros da pasta e depois escolher o mais recent
    data = []
    ftp.retrlines("LIST", (data.append))
    words = data[-1].split(None, 8)
    filename = words[-1].lstrip()
    print(filename)
    
    print ('Ficheiro mais recente: ', filename)
    ftp.quit()
    return;

config = configparser.ConfigParser()
config.read('ftp_users.properties')

for each_section in config.sections():
    for (each_key, each_val) in config.items(each_section):
        processUserFtp(each_key, each_val)





# #copiar o ficheiro para o local que se quer
# local_filename = os.path.join(r"D:\Documentos\CBL\Teste", filename)
# lf = open(local_filename, "wb")
# try:
#    ftp.retrbinary("RETR " + filename, lf.write, 8*1024)
#    print(f'Ficheiro copiado com sucesso')
# except:
#    print("Algo errado não está certo")
# lf.close


