CREATE TABLE LEAMON_PROPERTY(
	ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100, INCREMENT BY 1) PRIMARY KEY,
	PROPERTYNAME	VARCHAR(100) NOT NULL,
	PROPERTYVALUE	VARCHAR(500) DEFAULT NULL,
	DESCRIPTION		VARCHAR(500) DEFAULT NULL,
	
	CREATEDDATE		TIMESTAMP,
	LASTUPDATED		TIMESTAMP,
	ISENABLE		BOOLEAN DEFAULT TRUE
);

INSERT INTO LEAMON_PROPERTY (PROPERTYNAME, PROPERTYVALUE, DESCRIPTION)
VALUES('REPORTTD','D:\WORKSPACES\workspace-lemon\workspace-sts-3.8.4.RELEASE\Leamon-ERP\report\Blank_A5_invoice.jrxml','Report url. This report will print trade discount(TD) as well');

INSERT INTO LEAMON_PROPERTY (PROPERTYNAME, PROPERTYVALUE, DESCRIPTION) 
VALUES('REPORTWITHOUTD','D:\WORKSPACES\workspace-lemon\workspace-sts-3.8.4.RELEASE\Leamon-ERP\report\Blank_A5_invoice-withoutTD.jrxml','Report url. This report will print without trade discount(TD) as well');