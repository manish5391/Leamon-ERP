ALTER TABLE PAYMENT_RECEIVED_INFO ADD ADJUSTEDPAYMENT VARCHAR(100) DEFAULT NULL;
ALTER TABLE PAYMENT_RECEIVED_INFO ADD REMAININGAMOUNT VARCHAR(100) DEFAULT NULL;
ALTER TABLE PAYMENT_RECEIVED_INFO ADD STATUS VARCHAR(100) DEFAULT NULL;
ALTER TABLE PAYMENT_RECEIVED_INFO ADD REMARK VARCHAR(100) DEFAULT NULL;
ALTER TABLE PAYMENT_RECEIVED_INFO ADD TYPE VARCHAR(100) DEFAULT NULL;
ALTER TABLE PAYMENT_RECEIVED_INFO ADD RECEIVEDDATE VARCHAR(100) DEFAULT NULL;
COMMIT;

--DROP columns
ALTER TABLE PAYMENT_RECEIVED_INFO DROP RECEIVEDATE ;
ALTER TABLE PAYMENT_RECEIVED_INFO DROP BREMARK ;
ALTER TABLE PAYMENT_RECEIVED_INFO DROP WREMARK;

COMMIT;