alter table DLFileEntry drop column versionUserId;
alter table DLFileEntry drop column versionUserName;

alter table DLFileEntry add classNameId LONG;
alter table DLFileEntry add classPK LONG;
alter table DLFileEntry add treePath STRING null;
alter table DLFileEntry add manualCheckInRequired BOOLEAN;

COMMIT_TRANSACTION;

update DLFileEntry set classNameId = 0;
update DLFileEntry set classPK = 0;
update DLFileEntry set manualCheckInRequired = FALSE;

alter table DLFileRank add active_ BOOLEAN;

COMMIT_TRANSACTION;

update DLFileRank set active_ = TRUE;

alter table DLFileShortcut add treePath STRING null;
alter table DLFileShortcut add active_ BOOLEAN;

COMMIT_TRANSACTION;

update DLFileShortcut set active_ = TRUE;

alter table DLFileVersion add treePath STRING null;
alter table DLFileVersion add checksum VARCHAR(75) null;

alter table DLFolder add treePath STRING null;
alter table DLFolder add hidden_ BOOLEAN;
alter table DLFolder add status INTEGER;
alter table DLFolder add statusByUserId LONG;
alter table DLFolder add statusByUserName VARCHAR(75) null;
alter table DLFolder add statusDate DATE null;

COMMIT_TRANSACTION;

update DLFolder set hidden_ = FALSE;
update DLFolder set status = 0;
update DLFolder set statusByUserId = userId;
update DLFolder set statusByUserName = userName;
update DLFolder set statusDate = modifiedDate;

drop table DLSync;

create table DLSyncEvent (
	syncEventId LONG not null primary key,
	modifiedTime LONG,
	event VARCHAR(75) null,
	type_ VARCHAR(75) null,
	typePK LONG
);