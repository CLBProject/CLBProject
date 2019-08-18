import glob
import os
import sys
import codecs, csv
from ftplib import FTP
import configparser
import json

FTP_HOST = 'ftp.mobinteg.org'

class AnalyzerRegistry:
    
    def __init__(self, recortType,productType, itemSn, itemLabel, comPort, modBusId,
        date, time, kWh, kWhNeg, viNsys, vlin, vl2n, vl3n, vilSys, vl1l2, vl2l3, vl3l1,
        al1, al2, al3, kWsys, kwl1, kwl2, kwl3, kvarsys, kvarl1, kvarl2, kvarl3, kVasys, 
        kval1, kval2, kval3, pfSys, pfL1, pfL2, pfL3, phaseSequence, hZ):
        
        self.recortType = recortType
        self.productType = productType
        self.itemSn = itemSn
        self.itemLabel = itemLabel
        self.comPort = comPort
        self.modBusId = modBusId
        self.date = date
        self.time = time
        self.kWh = kWh
        self.kWhNeg = kWhNeg
        self.viNsys = viNsys
        self.vlin = vlin
        self.vl2n = vl2n
        self.vl3n = vl3n
        self.vilSys = vilSys
        self.vl1l2 = vl1l2
        self.vl2l3 = vl2l3
        self.vl3l1 = vl3l1
        self.al1 = al1
        self.al2 = al2
        self.al3 = al3
        self.kWsys = kWsys
        self.kwl1 = kwl1
        self.kwl2 = kwl2
        self.kwl3 = kwl3
        self.kvarsys = kvarsys
        self.kvarl1 = kvarl1
        self.kvarl2 = kvarl2
        self.kvarl3 = kvarl3
        self.kVasys = kVasys
        self.kval1 = kval1
        self.kval2 = kval2
        self.kval3 = kval3
        self.pfSys = pfSys
        self.pfL1 = pfL1
        self.pfL2 = pfL2
        self.pfL3 = pfL3
        self.phaseSequence = phaseSequence
        self.hZ = hZ
    
    def toJson(self):
        return self.kwl1

def processData(more_data):
    dataProcessed = more_data.decode("utf-8")
    
    for eachlineCommas in dataProcessed.splitlines():
        c = eachlineCommas.split(";")
        
        recortType = c[0]
        
        if recortType != 'AC' or len(c) < 39:
            continue

        productType = c[1]
        itemSn = c[2]
        itemLabel = c[3]
        comPort = c[4]
        modBusId = c[5]
        date = c[6]
        time = c[7]
        kWh = c[8]
        kWhNeg = c[9]
        viNsys = c[10]
        vlin = c[11]
        vl2n = c[12]
        vl3n = c[13]
        vilSys = c[14]
        vl1l2 = c[15]
        vl2l3 = c[16]
        vl3l1 = c[17]
        al1 = c[18]
        al2 = c[19]
        al3 = c[20]
        kWsys = c[21]
        kwl1 = c[22]
        kwl2 = c[23]  
        kwl3 = c[24]
        kvarsys = c[25]
        kvarl1 = c[26]
        kvarl2 = c[27]
        kvarl3 = c[28]
        kVasys = c[29]
        kval1 = c[30]
        kval2 = c[31]
        kval3 = c[32]
        pfSys = c[33]
        pfL1 = c[34]
        pfL2 = c[35]
        pfL3 = c[36]
        phaseSequence = c[37]
        hZ = c[38]
        
        analyzerReg = AnalyzerRegistry(recortType,productType, itemSn, itemLabel, comPort, modBusId,
        date, time, kWh, kWhNeg, viNsys, vlin, vl2n, vl3n, vilSys, vl1l2, vl2l3, vl3l1,
        al1, al2, al3, kWsys, kwl1, kwl2, kwl3, kvarsys, kvarl1, kvarl2, kvarl3, kVasys, 
        kval1, kval2, kval3, pfSys, pfL1, pfL2, pfL3, phaseSequence, hZ)
        
        print('Value of kWh: ', analyzerReg.kwl1)

def processUserFtp(userStr, passStr):
    
    # Ligar ao FTP e à pasta processed
    ftp = FTP(FTP_HOST)
    ftp.login(userStr,passStr)
    print(ftp.getwelcome())
    ftp.cwd("/")
    dirs = []
    ftp.retrlines("LIST", (dirs.append))

    for dir in dirs[3:]:
        currentDir = dir.split(None, 8)[8]
        ftp.cwd(currentDir)
        data = []
        
        ftp.retrlines("LIST", (data.append))
        words = data[-1].split(None, 8)
        filename = words[-1].lstrip()
        
        if len(data) > 2 :
            print (currentDir, '- Ficheiro mais recente: ', filename)
            ftp.retrbinary('RETR '+filename, processData)
        else: 
            print (currentDir , '- No File to Process on this School')
        
        ftp.cwd("..")
        
    ftp.quit()
    return;

config = configparser.ConfigParser()
config.read('ftp_users.properties')

for each_section in config.sections():
    for (each_key, each_val) in config.items(each_section):
        processUserFtp(each_key, each_val)

