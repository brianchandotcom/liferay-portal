alter table BookmarksEntry add groupId LONG;

create table BrowserTracker (
	browserTrackerId LONG not null primary key,
	userId LONG,
	browserKey LONG
);

update CalEvent set remindBy = '0' where remindBy = 'none';
update CalEvent set remindBy = '1' where remindBy = 'email';
update CalEvent set remindBy = '2' where remindBy = 'sms';
update CalEvent set remindBy = '3' where remindBy = 'aim';
update CalEvent set remindBy = '4' where remindBy = 'icq';
update CalEvent set remindBy = '5' where remindBy = 'msn';
update CalEvent set remindBy = '6' where remindBy = 'ym';

alter table Company add system BOOLEAN;

COMMIT_TRANSACTION;

update Company set system = FALSE;

alter table DLFileEntry add groupId LONG;

alter table DLFileRank add groupId LONG;

alter table DLFileShortcut add groupId LONG;

alter table DLFileVersion add groupId LONG;

update Group_ set name = classPK where classPK > 0 and name = '';

alter table IGImage add groupId LONG;

alter table JournalArticle add urlTitle VARCHAR(150) null;

COMMIT_TRANSACTION;

update JournalArticle set urlTitle = articleId;

alter table MBCategory add threadCount INTEGER;
alter table MBCategory add messageCount INTEGER;

alter table MBMessage add groupId LONG;
alter table MBMessage add classNameId LONG;
alter table MBMessage add classPK LONG;
alter table MBMessage add priority DOUBLE;

delete from MBMessageFlag where flag = 1;

COMMIT_TRANSACTION;

alter table MBMessageFlag add modifiedDate DATE null;
alter table MBMessageFlag add threadId LONG;

alter table MBThread add groupId LONG;

alter table Organization_ add leftOrganizationId LONG;
alter table Organization_ add rightOrganizationId LONG;

COMMIT_TRANSACTION;

update Organization_ set leftOrganizationId = 0, rightOrganizationId = 0;

update Region set regionCode = 'AB' where countryId = 1 and name = 'Alberta';

create table ResourceAction (
	resourceActionId LONG not null primary key,
	name VARCHAR(75) null,
	actionId VARCHAR(75) null,
	bitwiseValue LONG
);

create table ResourcePermission (
	resourcePermissionId LONG not null primary key,
	companyId LONG,
	name VARCHAR(255) null,
	scope INTEGER,
	primKey VARCHAR(255) null,
	roleId LONG,
	actionIds LONG
);

COMMIT_TRANSACTION;

create unique index IX_8D83D0CE on ResourcePermission (companyId, name, scope, primKey, roleId);

create table Shard (
	shardId LONG not null primary key,
	classNameId LONG,
	classPK LONG,
	name VARCHAR(75) null
);

alter table User_ add firstName VARCHAR(75) null;
alter table User_ add middleName VARCHAR(75) null;
alter table User_ add lastName VARCHAR(75) null;
alter table User_ add jobTitle VARCHAR(100) null;

alter table WikiPage add groupId LONG;