INSERT INTO house (id, country,city,street,houseNumber, pipeSystem) VALUES (1,"Ukraine","Kyiv","Stadium street","3", "VERTICAL_SINGLE");
INSERT INTO house (id,country,city,street,houseNumber, pipeSystem) VALUES (2, "Ukraine","Kyiv","Haidar street","124B", "VERTICAL_DOUBLE");
INSERT INTO house (id,country,city,street,houseNumber, pipeSystem) VALUES (3,"Ukraine","Kyiv","Dmitrivska street","56", "VERTICAL_DOUBLE");
INSERT INTO house (id,country,city,street,houseNumber, pipeSystem) VALUES (4,"Ukraine","Kyiv","Hnata Uri street","78C", "HORIZONTAL_DOUBLE");
INSERT INTO house (id,country,city,street,houseNumber, pipeSystem) VALUES (5,"Ukraine","Kyiv","Jeliabova street","12", "HORIZONTAL_DOUBLE");
INSERT INTO house (id,country,city,street,houseNumber, pipeSystem) VALUES (6,"Ukraine","Kyiv","Metalistiv street","134", "HORIZONTAL_SINGLE");


INSERT INTO sensor_model (id, MODEL_TYPE,maker,`name`,absoluteAccuracy, maxTemperature, minTemperature, maxFlowRate, minFlowRate)
 VALUES (1,"TEMP","Taxes Instruments","LM95235-Q1 ",0.001,160,-50,NULL,NULL);
INSERT INTO sensor_model (id, MODEL_TYPE,maker,`name`,absoluteAccuracy, maxTemperature, minTemperature, maxFlowRate, minFlowRate)
 VALUES (2,"TEMP","Taxes Instruments","LM95231 Dual Remote and Local Temperature Sensor with TruTherm Technology and SMBus Interface",0.002,180,-30,NULL,NULL);
INSERT INTO sensor_model (id, MODEL_TYPE,maker,`name`,absoluteAccuracy, maxTemperature, minTemperature, maxFlowRate, minFlowRate)
 VALUES (3,"TEMP","Taxas Instruments","TMP461 - High-Accuracy Remote and Local Temperature Sensor with Pin-Programmable Bus Address",0.005,140,-25,NULL,NULL);
INSERT INTO sensor_model (id, MODEL_TYPE,maker,`name`,absoluteAccuracy, maxTemperature, minTemperature, maxFlowRate, minFlowRate)
 VALUES (4,"FLOW","Keyence","FD-Q10C",1,NULL,NULL,500,0.01);
