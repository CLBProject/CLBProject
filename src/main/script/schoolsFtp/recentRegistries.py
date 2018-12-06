import glob
import os
import sys
import codecs, csv
import ftplib
import configparser
import socket
import json
import time

FTP_HOST = 'ftp.mobinteg.org'
SERVER_HOST = "localhost"
PORT = 6006
filesProcessed = []


class AnalyzerRegistry:
    
    def __init__(self, recortType, productType, itemSn, itemLabel, comPort, modBusId,
        date, time, kwh, kWhNeg, vlnsys, vl1n, vl2n, vl3n, vllsys, vl1l2, vl2l3, vl3l1,
        al1, al2, al3, kwsys, kwl1, kwl2, kwl3, kvarsys, kvarl1, kvarl2, kvarl3, kvasys,
        kval1, kval2, kval3, pfsys, pfl1, pfl2, pfl3, phaseSequence, hZ, buildingName):
        
        self.recortType = recortType
        self.productType = productType
        self.itemSn = itemSn
        self.itemLabel = itemLabel
        self.comPort = comPort
        self.modBusId = modBusId
        self.date = date
        self.time = time
        self.kwh = kwh
        self.kWhNeg = kWhNeg
        self.vlnsys = vlnsys
        self.vl1n = vl1n
        self.vl2n = vl2n
        self.vl3n = vl3n
        self.vllsys = vllsys
        self.vl1l2 = vl1l2
        self.vl2l3 = vl2l3
        self.vl3l1 = vl3l1
        self.al1 = al1
        self.al2 = al2
        self.al3 = al3
        self.kwsys = kwsys
        self.kwl1 = kwl1
        self.kwl2 = kwl2
        self.kwl3 = kwl3
        self.kvarsys = kvarsys
        self.kvarl1 = kvarl1
        self.kvarl2 = kvarl2
        self.kvarl3 = kvarl3
        self.kvasys = kvasys
        self.kval1 = kval1
        self.kval2 = kval2
        self.kval3 = kval3
        self.pfsys = pfsys
        self.pfl1 = pfl1
        self.pfl2 = pfl2
        self.pfl3 = pfl3
        self.phaseSequence = phaseSequence
        self.hZ = hZ
        self.buildingName = buildingName
    
    def toJson(self):
        return self.kwl1


def processData(line, filename, latestRegistry):
    
    c = line.split(";")
        
    recortType = c[0]
        
    if recortType != 'AC' or len(c) < 39:
        return
        
    productType = c[1]
    itemSn = c[2]
    itemLabel = c[3]
    comPort = c[4]
    modBusId = c[5]
    date = c[6]
    time = c[7]
    kwh = c[8]
    kWhNeg = c[9]
    vlnsys = c[10]
    vl1n = c[11]
    vl2n = c[12]
    vl3n = c[13]
    vllsys = c[14]
    vl1l2 = c[15]
    vl2l3 = c[16]
    vl3l1 = c[17]
    al1 = c[18]
    al2 = c[19]
    al3 = c[20]
    kwsys = c[21]
    kwl1 = c[22]
    kwl2 = c[23]  
    kwl3 = c[24]
    kvarsys = c[25]
    kvarl1 = c[26]
    kvarl2 = c[27]
    kvarl3 = c[28]
    kvasys = c[29]
    kval1 = c[30]
    kval2 = c[31]
    kval3 = c[32]
    pfsys = c[33]
    pfl1 = c[34]
    pfl2 = c[35]
    pfl3 = c[36]
    phaseSequence = c[37]
    hZ = c[38]
        
    analyzerReg = AnalyzerRegistry(recortType, productType, itemSn, itemLabel, comPort, modBusId,
    date, time, kwh, kWhNeg, vlnsys, vl1n, vl2n, vl3n, vllsys, vl1l2, vl2l3, vl3l1,
    al1, al2, al3, kwsys, kwl1, kwl2, kwl3, kvarsys, kvarl1, kvarl2, kvarl3, kvasys,
    kval1, kval2, kval3, pfsys, pfl1, pfl2, pfl3, phaseSequence, hZ, filename)
    
    # only send when date is smaller then latestRegistry
    if latestRegistry == '' or latestRegistry < date :
        print('Registry Persisted For: ' + filename + ' , at Date: ' + date + " , with latestRegistry " + latestRegistry)
        sock.send('*persistDataObject*\n'.encode('utf-8'))
        sock.send((json.dumps(analyzerReg.__dict__) + "\n").encode('utf-8'))


def processUserFtp(userftp, passwordftp):

    ftp = ftplib.FTP(FTP_HOST, timeout=100)
    ftp.login(userftp, passwordftp)
    print(ftp.getwelcome())
    ftp.cwd("/")
    dirs = []
    ftp.retrlines("LIST", (dirs.append))
    
    for dir in dirs[3:]:
        currentDir = dir.split(None, 8)[8]
        ftp.cwd(currentDir)
        print('Visiting Dir: ', currentDir)
        # cria lista com todos o ficheiros da pasta e depois escolher o mais recent
        data = []
          
        ftp.retrlines("LIST", (data.append))
            
        index = 0
        
        sock.send('*getLatestPersistedDate*\n'.encode('utf-8'))
        sock.send(('Analyzer For Building ' + currentDir + '\n').encode('utf-8'))

        latestPersistedDate = recv_basic()
        # print('Latest Date: ' + latestPersistedDate)
        
        for file in data:
            # Ignore the first two files
            if index > 1:
                    filename = file.split(None, 8)[-1].lstrip()
                    fileInArray = filename + "_" + currentDir
                    
                    try:
                        if fileInArray not in filesProcessed:
                            ftp.sendcmd("NOOP")
                            
                            # if index % 5 == 0:
                            #print('Downloading File: ' + filename)
                            ftp.retrlines('RETR ' + filename, lambda line: processData(line, currentDir, latestPersistedDate))
                            
                            filesProcessed.append(fileInArray)
                    except ftplib.all_errors as e:
                        print(e)
                        processUserFtp(userftp, passwordftp)
            
            index = index + 1
                
        ftp.cwd("..")

    ftp.quit()

    sock.send("*exitPersistDataObject*\n".encode('utf-8'))
    
    return;


def recv_basic():
    finalData = []
    
    while True:
        data = sock.recv(8192)
        total_data = data.decode('utf-8').split("\n")
        
        breaking = False
        
        for tdata in total_data:
            if tdata == "*end*":
               breaking = True
            else: 
                finalData.append(tdata)
            
        if breaking == True:
            break

    return finalData[0]


sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((SERVER_HOST, PORT))  

config = configparser.ConfigParser()
config.read('ftp_users.properties')

for each_section in config.sections():
    for (each_key, each_val) in config.items(each_section):
        processUserFtp(each_key, each_val)
