--  *********************************************************************
--  Rollback 1 Change(s) Script
--  *********************************************************************
--  Change Log: migrations.xml
--  Ran at: 8/11/21, 11:46 am
--  Against: punitdh@localhost@jdbc:mysql://localhost:3306/DropBookmarks
--  Liquibase version: 4.4.2
--  *********************************************************************

--  Lock Database
UPDATE DATABASECHANGELOGLOCK SET `LOCKED` = 1, LOCKEDBY = '2001:8003:34fc:c900:8d8f:1afd:8887:b821%en0 (2001:8003:34fc:c900:8d8f:1afd:8887:b821%en0)', LOCKGRANTED = '2021-11-08 11:46:11.335' WHERE ID = 1 AND `LOCKED` = 0;

--  Rolling Back ChangeSet: migrations.xml::3::Punit
DELETE FROM users WHERE id=1;

DELETE FROM DATABASECHANGELOG WHERE ID = '3' AND AUTHOR = 'Punit' AND FILENAME = 'migrations.xml';

--  Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET `LOCKED` = 0, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

