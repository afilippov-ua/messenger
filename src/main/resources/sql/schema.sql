DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS MESSAGES;
DROP TABLE IF EXISTS CONTACTS;

CREATE TABLE USERS (
    ID INTEGER NOT NULL AUTO_INCREMENT,
    EMAIL VARCHAR (500),
    PASSWORD VARCHAR(50),
    NAME VARCHAR(500),
    PRIMARY KEY (ID)
);

CREATE TABLE MESSAGES (
    ID INTEGER NOT NULL AUTO_INCREMENT,
    MESSAGE_DATE DATETIME,
    SENDER_ID INTEGER,
    RECEIVER_ID INTEGER,
    TEXT VARCHAR,
    SEEN BOOLEAN,
    PRIMARY KEY (ID)
);

CREATE TABLE CONTACTS (
    ID INTEGER NOT NULL AUTO_INCREMENT,
    OWNER_ID INTEGER,
    CONTACT_ID INTEGER,
    CONTACT_NAME VARCHAR(500),
    PRIMARY KEY (ID)
);