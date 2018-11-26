import glob
import os
import sys
# C:\Users\crist\Documents\Luis\VMU-C_BR0720027001N_VAR_2017-11-28-14-05-30_S.csv

import csv


import pandas as pd
dados = ['Record Type','Product type','Item S/N','Item Label','Com Port','Modbus ID','Timestamp(epoch)','Timestamp(RFC3339)','kWh','kWh (-)','VL-NSYS','VL1-N','VL2-N','VL3-N','VL-LSYS','VL1-L2','VL2-L3','VL3-L3','A L1','A L2','A L3','kW sys','kW L1','kW L2','kW L3','kvar sys','kvar L1','kvar L2','kvar L3','kVA sys','kVA L1','kVA L2','kVA L3','PF sys','PF L1','PF L2','PF L3','Phase sequence','Hz','THD A L1','THD A L2','THD A L3','THD V L1-N','THD V L2-N','THD V L3-N','W dmd','W dmd Max','kvarh','kvarh (-)','kvarh (C)','kvarh (L)','Totalizer 1','Totalizer 2','Totalizer 3','kWh L1','kWh L2','kWh L3','An','Hour meter kWh','A sys','kvarh L1','kvarh L2','kvarh L3','kvarh (-) L1','kvarh (-) L2','kvarh (-) L3','kWh (-) L1','kWh (-) L2','kWh (-) L3','kVAh L','kVAh L1','kVAh L2','kVAh L3','Hour meter kWh (-)','var dmd','VA dmd']
# Read the CSV into a pandas data frame (df)
#   With a df you can do many things
#   most important: visualize data with Seaborn
df = pd.read_csv('C:\\Users\\crist\\Documents\\Luis\\VMU-C_BR0720027001N_VAR_2017-11-28-14-05-30_S.csv',delimiter=';',names=dados)

df.drop(df.index[[0]], inplace=True)

print (df)
# df.drop([0])
