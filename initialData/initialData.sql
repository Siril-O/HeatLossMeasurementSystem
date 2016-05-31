INSERT INTO house (id,lat ,lng, country,city,street,houseNumber, pipeSystem) VALUES (1,50.441659,30.522922,"Ukraine","Kyiv","Stadium street","3", "VERTICAL_SINGLE");
INSERT INTO house (id,lat ,lng, country,city,street,houseNumber, pipeSystem) VALUES (2,50.45073449,30.50514936, "Ukraine","Kyiv","Olesia Honchara","47", "HORIZONTAL_DOUBLE");
INSERT INTO house (id,lat ,lng, country,city,street,houseNumber, pipeSystem) VALUES (3,50.43974134,30.49716711,"Ukraine","Kyiv","Lva Tolstoho","55", "VERTICAL_DOUBLE");

INSERT INTO sensor_model (id, MODEL_TYPE,maker,`name`,absoluteAccuracy, maxTemperature, minTemperature, maxFlowRate, minFlowRate)
 VALUES (1,"TEMP","Taxes Instruments","LM95235-Q1 ",0.001,160,-50,NULL,NULL);
INSERT INTO sensor_model (id, MODEL_TYPE,maker,`name`,absoluteAccuracy, maxTemperature, minTemperature, maxFlowRate, minFlowRate)
 VALUES (2,"TEMP","Taxes Instruments","LM95231 Dual Remote and Local Temperature Sensor with TruTherm Technology and SMBus Interface",0.002,180,-30,NULL,NULL);
INSERT INTO sensor_model (id, MODEL_TYPE,maker,`name`,absoluteAccuracy, maxTemperature, minTemperature, maxFlowRate, minFlowRate)
 VALUES (3,"TEMP","Taxas Instruments","TMP461 - High-Accuracy Remote and Local Temperature Sensor with Pin-Programmable Bus Address",0.005,140,-25,NULL,NULL);
INSERT INTO sensor_model (id, MODEL_TYPE,maker,`name`,absoluteAccuracy, maxTemperature, minTemperature, maxFlowRate, minFlowRate)
 VALUES (4,"FLOW","Keyence","FD-Q10C",1,NULL,NULL,500,0.01);


INSERT INTO user (id,USER_TYPE, email,name,password,ROLE) VALUES (1,'EMPL','kiril@g.com','Kiril Kovalchuk',
'$2a$04$WT5b20WJsmuqBWrKMw8cv.RrQwB2p4ZSboLxCCDI1DkMV9.fkkNUa','ROLE_EMPLOYEE');
INSERT INTO user (id,USER_TYPE, email,name,password,ROLE,APARTMENT_ID) VALUES (1,'EMPL','customer@g.com','Customer',
'$2a$04$gv4WFgM1aLPrfOfce/qn9uj8hvTxgsyAdOemAVKCqc72V4aLAaVzS','ROLE_CUSTOMER',1);