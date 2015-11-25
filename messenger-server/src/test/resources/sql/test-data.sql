DELETE FROM MESSAGES;
DELETE FROM CONTACTS;
DELETE FROM USERS;

INSERT INTO USERS
(ID, EMAIL, PASSWORD, NAME) VALUES
  (1, 'serdyukov@mail.com', '12345', 'aleksandr serdyukov'),
  (2, 'sirosh@mail.com', '12345', 'sergey  sirosh'),
  (3, 'filippov@mail.com', '12345', 'aleksandr filippov');

INSERT INTO CONTACTS
(ID, OWNERID, CONTACTID, CONTACTNAME) VALUES
  (1, 1, 3, 'Filippov'),
  (2, 3, 1, 'Serdyikov'),
  (3, 3, 2, 'Sirosh');

INSERT INTO MESSAGES
(ID, MESSAGEDATE, SENDERID, RECEIVERID, TEXT, SEEN) VALUES
  (1, PARSEDATETIME('2015/01/01 00:00:00', 'yyyy/MM/dd hh:mm:ss'), 3, 1, 'Hi!', TRUE),
  (2, PARSEDATETIME('2015/01/01 00:01:00', 'yyyy/MM/dd hh:mm:ss'), 3, 1, 'How are you?', TRUE),
  (3, PARSEDATETIME('2015/01/01 00:02:00', 'yyyy/MM/dd hh:mm:ss'), 3, 1, 'What are you doing now?', TRUE),
  (4, PARSEDATETIME('2015/01/01 00:03:00', 'yyyy/MM/dd hh:mm:ss'), 1, 3, 'Hi, man', TRUE),
  (5, PARSEDATETIME('2015/01/01 00:04:00', 'yyyy/MM/dd hh:mm:ss'), 1, 3, 'Fine! Watching TV. And you?', FALSE),
  (6, PARSEDATETIME('2015/01/01 00:05:00', 'yyyy/MM/dd hh:mm:ss'), 3, 1, 'new message 1', TRUE),
  (7, PARSEDATETIME('2015/01/01 00:06:00', 'yyyy/MM/dd hh:mm:ss'), 3, 1, 'new message 2', TRUE),
  (8, PARSEDATETIME('2015/01/01 00:07:00', 'yyyy/MM/dd hh:mm:ss'), 3, 1, 'new message 3', TRUE);