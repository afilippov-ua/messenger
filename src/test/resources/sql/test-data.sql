DELETE FROM USERS;
DELETE FROM MESSAGES;
DELETE FROM CONTACTS;

INSERT INTO USERS (ID, EMAIL, PASSWORD, NAME, TOKEN) VALUES
  (1, 'serdyukov@mail.com', '12345', 'aleksandr serdyukov'),
  (2, 'sirosh@mail.com', '12345', 'sergey  sirosh'),
  (3, 'filippov@mail.com', '12345', 'aleksandr filippov'),
  (4, 'UpdateUser@mail.com', '12345', 'update user'),
  (5, 'DeleteUser@mail.com', '12345', 'delete user');

INSERT INTO MESSAGES (ID, MESSAGE_DATE, SENDER_ID, RECEIVER_ID, TEXT, SEEN) VALUES
  (1, PARSEDATETIME('2015/01/01 00:00:00', 'yyyy/MM/dd hh:mm:ss'), 3, 1, 'Hi!', TRUE),
  (2, PARSEDATETIME('2015/01/01 00:01:00', 'yyyy/MM/dd hh:mm:ss'), 3, 1, 'How are you?', TRUE),
  (3, PARSEDATETIME('2015/01/01 00:02:00', 'yyyy/MM/dd hh:mm:ss'), 3, 1, 'What are you doing now?', TRUE),
  (4, PARSEDATETIME('2015/01/01 00:03:00', 'yyyy/MM/dd hh:mm:ss'), 1, 3, 'Hi, man', TRUE),
  (5, PARSEDATETIME('2015/01/01 00:04:00', 'yyyy/MM/dd hh:mm:ss'), 1, 3, 'Fine! Watching TV. And you?', FALSE);

INSERT INTO CONTACTS (ID, OWNER_ID, CONTACT_ID, CONTACT_NAME) VALUES
  (1, 1, 3, 'Filippov'),
  (2, 3, 1, 'Serdyikov'),
  (3, 3, 2, 'Sirosh');