DROP TABLE ANALYZER_REGISTRY_EXTRA_INFO;
DROP TABLE ANALYZER_REGISTRY;
DROP TABLE ANALYZER;
DROP TABLE DATA_LOGGER;
DROP TABLE USERSYSTEM;
DROP TABLE BUILDING;

CREATE SCHEMA clb;

CREATE TABLE BUILDING
(
 	buildingId bigint not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
 	name varchar(255),
 	CONSTRAINT BUILDING_PK PRIMARY KEY (buildingId)
);
  
CREATE TABLE USERSYSTEM
(
 	userId varchar(255),
 	name varchar(255),
 	username varchar(255),
 	password varchar(255),
 	address varchar(255),
 	buildingId bigint,
 	CONSTRAINT USERS_PK PRIMARY KEY (userId),
 	FOREIGN KEY (buildingId) REFERENCES BUILDING (buildingId)
);
 
CREATE TABLE DATA_LOGGER
(
 	dataLoggerId bigint not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
 	name varchar(255),
 	userId varchar(255),
 	CONSTRAINT DATA_LOGGER_PK PRIMARY KEY (dataLoggerId),
 	FOREIGN KEY (userId) REFERENCES USERSYSTEM (userId)
);
 
CREATE TABLE ANALYZER
(
 	analyzerId bigint not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
 	name varchar(255),
 	dataLoggerId bigint,
 	CONSTRAINT ANALYZER_PK PRIMARY KEY (analyzerId),
 	FOREIGN KEY (dataLoggerId) REFERENCES DATA_LOGGER (dataLoggerId)
);

CREATE TABLE ANALYZER_REGISTRY
(
	regId bigint not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	currentDate date,
	currentTime varchar(255),
	temperature float,
	recortType varchar(255),
	productType varchar(255),
	itemLabel varchar(255),
	comPort int,
	modBusId bigint,
	epochFormat timestamp,
	rfc3339Format timestamp,
	kWh float,
	vlNsys float,
	vl1n float,
	vl2n float,
	vl3n float,
	vlLsys float,
	vl1L2 float,
	vl2L3 float,
	vl3L1 float,
	aL1 float,
	aL2 float,
	aL3 float,
	kWsys float,
	kWL1 float,
	kWL2 float,
	kWL3 float,
	kVarSys float,
	kVarL1 float,
	kVarL2 float,
	kVarL3 float,
	kVAsys float,
	kVAL1 float,
	kVAL2 float,
	kVAL3 float,
	pfSys float,
	pfL1 float,
	pfL2 float,
	pfL3 float,
	hZ float,
	thdAL1 float,
	thdAL2 float,
	thdAL3 float,
	thdVL1N float,
	thdVL2N float,
	thdVL3N float,
	kVarh float,
	kWhL1 float,
	kWhL2 float,
	kWhL3 float,
	aN float,
	hourMeterKwh float,
	aSys float,
	kVahL float,
	kVahL1 float,
	kVahL2 float,
	kVahL3 float,
	hourMeterKwhNegative float,
	varDmd float,
	vaDmd float,
	analyzerId bigint,
	CONSTRAINT ANALYZER_REGISTRY_pk PRIMARY KEY (regId),
	FOREIGN KEY (analyzerId) REFERENCES ANALYZER (analyzerId)
);

CREATE TABLE ANALYZER_REGISTRY_EXTRA_INFO(
	regId bigint not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	kWhNegative float,
	phaseSequence float,
	wDmd float,
	wDmdMax float,
	kVarhNegative float,
	kVarhC float,
	kVarhL float,
	totalizer1 float,
	totalizer2 float,
	totalizer3 float,
	kVarhL1 float,
	kVarhL2 float,
	kVarhL3 float,
	kVarhL1Negative float,
	kVarhL2Negative float,
	kVarhL3Negative float,
	kWhL1Negative float,
	kWhL2Negative float,
	kWhL3Negative float,
	analyzerRegistryId bigint,
	CONSTRAINT ANALYZER_REGISTRY_extra_info_pk PRIMARY KEY (regId),
	FOREIGN KEY (analyzerRegistryId) REFERENCES ANALYZER_REGISTRY (regId)
 );
 
 CREATE INDEX ANALYZER_REGISTRY_DESC ON ANALYZER_REGISTRY (CURRENTDATE);
