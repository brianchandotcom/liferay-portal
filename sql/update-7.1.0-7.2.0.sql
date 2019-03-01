alter table Layout add parentPlid LONG;
alter table Layout add leftPlid LONG;
alter table Layout add rightPlid LONG;
alter table Layout add system BOOLEAN;

alter table Layout add classNameId LONG;
alter table Layout add classPK LONG;
alter table Layout add publishDate DATE null;

COMMIT_TRANSACTION;

update Layout set system = FALSE;