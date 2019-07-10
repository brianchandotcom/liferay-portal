DROP_TABLE_IF_EXISTS(LayoutVersion);

alter table Layout drop column headId;
alter table Layout drop column head;

COMMIT_TRANSACTION;