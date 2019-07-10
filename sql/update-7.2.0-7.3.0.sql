DROP_TABLE_IF_EXISTS(LayoutVersion);

DROP_TABLE_IF_EXISTS(LayoutSetVersion);

alter table Layout drop column headId;
alter table Layout drop column head;

alter table LayoutSet drop column headId;
alter table LayoutSet drop column head;

COMMIT_TRANSACTION;