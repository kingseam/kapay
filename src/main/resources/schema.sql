 DROP TABLE IF EXISTS TODO;
 DROP TABLE IF EXISTS TODO_REF;
 DROP TABLE IF EXISTS TODO_ACCUM;

 CREATE TABLE TODO (
	ID INT PRIMARY KEY AUTO_INCREMENT,
	CONTENTS  VARCHAR(4000),
	STATUS_TYPE VARCHAR(2),
	REG_DTS TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	MOD_DTS TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (ID)
 );

  CREATE TABLE REF_TODO (
	ID NUMBER(20),
	REF_ID NUMBER(20),
	REG_DTS TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	MOD_DTS TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (ID,REF_ID)
 );

  CREATE TABLE TODO_ACCUM (
	CNT NUMBER(20) DEFAULT 0,
	REG_DTS TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	MOD_DTS TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 );

 INSERT INTO TODO_ACCUM (CNT) VALUES (0);