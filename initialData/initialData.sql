INSERT INTO house (country,city,street,houseNumber, pipeSystem) VALUES ("Ukraine","Kyiv","Stadium street","3", "VERTICAL_SINGLE");
INSERT INTO house (country,city,street,houseNumber, pipeSystem) VALUES ("Ukraine","Kyiv","Haidar street","124B", "VERTICAL_DOUBLE");
INSERT INTO house (country,city,street,houseNumber, pipeSystem) VALUES ("Ukraine","Kyiv","Dmitrivska street","56", "VERTICAL_DOUBLE");
INSERT INTO house (country,city,street,houseNumber, pipeSystem) VALUES ("Ukraine","Kyiv","Hnata Uri street","78C", "HORIZONTAL_DOUBLE");
INSERT INTO house (country,city,street,houseNumber, pipeSystem) VALUES ("Ukraine","Kyiv","Jeliabova street","12", "HORIZONTAL_DOUBLE");
INSERT INTO house (country,city,street,houseNumber, pipeSystem) VALUES ("Ukraine","Kyiv","Metalistiv street","134", "HORIZONTAL_SINGLE");


INSERT INTO sensor_model (MODEL_TYPE,maker,`name`,absoluteAccuracy, maxTemperature, minTemperature, maxFlowRate, minFlowRate)
 VALUES ("TEMP","Taxes Instruments","LM95235-Q1 ",0.001,160,-50,NULL,NULL);
INSERT INTO sensor_model (MODEL_TYPE,maker,`name`,absoluteAccuracy, maxTemperature, minTemperature, maxFlowRate, minFlowRate)
 VALUES ("TEMP","Taxes Instruments","LM95231 Dual Remote and Local Temperature Sensor with TruTherm Technology and SMBus Interface",0.002,180,-30,NULL,NULL);
INSERT INTO sensor_model (MODEL_TYPE,maker,`name`,absoluteAccuracy, maxTemperature, minTemperature, maxFlowRate, minFlowRate)
 VALUES ("TEMP","Taxas Instruments","TMP461 - High-Accuracy Remote and Local Temperature Sensor with Pin-Programmable Bus Address",0.005,140,-25,NULL,NULL);
INSERT INTO sensor_model (MODEL_TYPE,maker,`name`,absoluteAccuracy, maxTemperature, minTemperature, maxFlowRate, minFlowRate)
 VALUES ("FLOW","Keyence","FD-Q10C",1,NULL,NULL,500,0.01);