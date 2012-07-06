alter table AssetEntry add classUuid VARCHAR(75) null;

alter table DLFileEntry add extension VARCHAR(75) null;

alter table DLFileVersion add extension VARCHAR(75) null;
alter table DLFileVersion add title VARCHAR(255) null;
alter table DLFileVersion add changeLog STRING null;
alter table DLFileVersion add extraSettings TEXT null;

COMMIT_TRANSACTION;

update DLFileVersion set changeLog = description;

alter table Layout add uuid_ VARCHAR(75) null;

alter table MBMessage add rootMessageId LONG;

COMMIT_TRANSACTION;

update MBMessage set rootMessageId = (select rootMessageId from MBThread where MBThread.threadId = MBMessage.threadId);

drop table SocialEquityAssetEntry;

create table SocialEquityAssetEntry (
	equityAssetEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	assetEntryId LONG,
	informationK DOUBLE,
	informationB DOUBLE
);

drop table SocialEquityHistory;

create table SocialEquityHistory (
	equityHistoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	personalEquity INTEGER
);

drop table SocialEquityLog;

create table SocialEquityLog (
	equityLogId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	assetEntryId LONG,
	actionId VARCHAR(75) null,
	actionDate INTEGER,
	active_ BOOLEAN,
	expiration INTEGER,
	type_ INTEGER,
	value INTEGER
);

drop table SocialEquitySetting;

create table SocialEquitySetting (
	equitySettingId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	classNameId LONG,
	actionId VARCHAR(75) null,
	dailyLimit INTEGER,
	lifespan INTEGER,
	type_ INTEGER,
	uniqueEntry BOOLEAN,
	value INTEGER
);

drop table SocialEquityUser;

create table SocialEquityUser (
	equityUserId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	contributionK DOUBLE,
	contributionB DOUBLE,
	participationK DOUBLE,
	participationB DOUBLE,
	rank INTEGER
);

alter table User_ add facebookId LONG;

alter table WikiPageResource add uuid_ VARCHAR(75) null;