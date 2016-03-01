alter table User_ add ldapServerId LONG;

COMMIT_TRANSACTION;

update User_ set ldapServerId = -1;