create table AssetCategory (
	uuid_ VARCHAR(75) null,
	categoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCategoryId LONG,
	leftCategoryId LONG,
	rightCategoryId LONG,
	name VARCHAR(75) null,
	title STRING null,
	vocabularyId LONG
);

create table AssetCategoryProperty (
	categoryPropertyId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	categoryId LONG,
	key_ VARCHAR(75) null,
	value VARCHAR(75) null
);

create table AssetEntries_AssetCategories (
	entryId LONG not null,
	categoryId LONG not null,
	primary key (entryId, categoryId)
);

create table AssetEntries_AssetTags (
	entryId LONG not null,
	tagId LONG not null,
	primary key (entryId, tagId)
);

create table AssetEntry (
	entryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	visible BOOLEAN,
	startDate DATE null,
	endDate DATE null,
	publishDate DATE null,
	expirationDate DATE null,
	mimeType VARCHAR(75) null,
	title VARCHAR(255) null,
	description STRING null,
	summary STRING null,
	url STRING null,
	height INTEGER,
	width INTEGER,
	priority DOUBLE,
	viewCount INTEGER
);

create table AssetTag (
	tagId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	assetCount INTEGER
);

create table AssetTagProperty (
	tagPropertyId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	tagId LONG,
	key_ VARCHAR(75) null,
	value VARCHAR(255) null
);

create table AssetTagStats (
	tagStatsId LONG not null primary key,
	tagId LONG,
	classNameId LONG,
	assetCount INTEGER
);

create table AssetVocabulary (
	uuid_ VARCHAR(75) null,
	vocabularyId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	title STRING null,
	description STRING null,
	settings_ STRING null
);

alter table BlogsEntry add allowPingbacks BOOLEAN;
alter table BlogsEntry add status INTEGER;
alter table BlogsEntry add statusByUserId LONG;
alter table BlogsEntry add statusByUserName VARCHAR(75);
alter table BlogsEntry add statusDate DATE;

COMMIT_TRANSACTION;

update BlogsEntry set allowPingbacks = TRUE;
update BlogsEntry set status = 0 where draft = FALSE;
update BlogsEntry set status = 2 where draft = TRUE;
update BlogsEntry set statusByUserId = userId where draft = FALSE;
update BlogsEntry set statusByUserName = userName where draft = FALSE;
update BlogsEntry set statusDate = createDate where draft = FALSE;

alter table DLFileEntry add pendingVersion VARCHAR(75) null;

alter table DLFileShortcut add status INTEGER;
alter table DLFileShortcut add statusByUserId LONG;
alter table DLFileShortcut add statusByUserName VARCHAR(75);
alter table DLFileShortcut add statusDate DATE;

COMMIT_TRANSACTION;

update DLFileShortcut set status = 0;
update DLFileShortcut set statusByUserId = userId;
update DLFileShortcut set statusByUserName = userId;
update DLFileShortcut set statusDate = createDate;

drop index IX_6C5E6512 on DLFileVersion;

alter table DLFileVersion add status INTEGER;
alter table DLFileVersion add statusByUserId LONG;
alter table DLFileVersion add statusByUserName VARCHAR(75);
alter table DLFileVersion add statusDate DATE;

COMMIT_TRANSACTION;

update DLFileVersion set status = 0;
update DLFileVersion set statusByUserId = userId;
update DLFileVersion set statusByUserName = userId;
update DLFileVersion set statusDate = createDate;

alter table JournalArticle add status INTEGER;
alter table JournalArticle add statusByUserId LONG;
alter table JournalArticle add statusByUserName VARCHAR(75);
alter table JournalArticle add statusDate DATE;

COMMIT_TRANSACTION;

update JournalArticle set status = 0 where approved = TRUE;
update JournalArticle set status = 2 where approved = FALSE;
update JournalArticle set statusByUserId = approvedByUserId;
update JournalArticle set statusByUserName = approvedByUserName;
update JournalArticle set statusDate = approvedDate where expired = FALSE;
update JournalArticle set statusDate = expirationDate where expired = TRUE;

alter table Layout add layoutPrototypeId LONG;

create table LayoutPrototype (
	layoutPrototypeId LONG not null primary key,
	companyId LONG,
	name STRING null,
	description STRING null,
	settings_ STRING null,
	active_ BOOLEAN
);

alter table LayoutSet add layoutSetPrototypeId LONG;

create table LayoutSetPrototype (
	layoutSetPrototypeId LONG not null primary key,
	companyId LONG,
	name STRING null,
	description STRING null,
	settings_ STRING null,
	active_ BOOLEAN
);

alter table MBMessage add allowPingbacks BOOLEAN;
alter table MBMessage add status INTEGER;
alter table MBMessage add statusByUserId LONG;
alter table MBMessage add statusByUserName VARCHAR(75);
alter table MBMessage add statusDate DATE;

COMMIT_TRANSACTION;

update MBMessage set allowPingbacks = TRUE;
update MBMessage set status = 0;
update MBMessage set statusByUserId = userId;
update MBMessage set statusByUserName = userId;
update MBMessage set statusDate = createDate;

alter table MBThread add status INTEGER;
alter table MBThread add statusByUserId LONG;
alter table MBThread add statusByUserName VARCHAR(75);
alter table MBThread add statusDate DATE;

COMMIT_TRANSACTION;

update MBThread set status = 0;

alter table ShoppingItem add groupId LONG;

alter table WikiPage add status INTEGER;
alter table WikiPage add statusByUserId LONG;
alter table WikiPage add statusByUserName VARCHAR(75);
alter table WikiPage add statusDate DATE;

COMMIT_TRANSACTION;

update WikiPage set status = 0;
update WikiPage set statusByUserId = userId;
update WikiPage set statusByUserName = userId;
update WikiPage set statusDate = createDate;

create table WorkflowDefinitionLink (
	workflowDefinitionLinkId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	workflowDefinitionName VARCHAR(75) null,
	workflowDefinitionVersion INTEGER
);

create table WorkflowInstanceLink (
	workflowInstanceLinkId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	workflowInstanceId LONG
);

COMMIT_TRANSACTION;

create table AssetLink (
	linkId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	entryId1 LONG,
	entryId2 LONG,
	type_ INTEGER,
	weight INTEGER
);

create table Team (
	teamId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	groupId LONG,
	name VARCHAR(75) null,
	description STRING null
);

create table Users_Teams (
	userId LONG not null,
	teamId LONG not null,
	primary key (userId, teamId)
);

COMMIT_TRANSACTION;

alter table AssetEntry add socialInformationEquity DOUBLE;

alter table Company add maxUsers INTEGER;

COMMIT_TRANSACTION;

update Company set maxUsers = 0;

update ExpandoTable set name = 'CUSTOM_FIELDS' where name = 'DEFAULT_TABLE';

alter table LayoutSet add settings_ TEXT null;

update MBMessage set categoryId = -1 where groupId = 0;

update MBThread set categoryId = -1 where groupId = 0;

alter table PasswordPolicy add minAlphanumeric INTEGER;
alter table PasswordPolicy add minLowerCase INTEGER;
alter table PasswordPolicy add minNumbers INTEGER;
alter table PasswordPolicy add minSymbols INTEGER;
alter table PasswordPolicy add minUpperCase INTEGER;
alter table PasswordPolicy add resetTicketMaxAge LONG;

COMMIT_TRANSACTION;

update PasswordPolicy set minAlphanumeric = 0;
update PasswordPolicy set minLowerCase = 0;
update PasswordPolicy set minNumbers = 0;
update PasswordPolicy set minSymbols = 0;
update PasswordPolicy set minUpperCase = 0;
update PasswordPolicy set resetTicketMaxAge = 86400;

create table SocialEquityAssetEntry (
	equityAssetEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	assetEntryId LONG,
	informationK DOUBLE,
	informationB DOUBLE
);

create table SocialEquityHistory (
	equityHistoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	personalEquity INTEGER
);

create table SocialEquityLog (
	equityLogId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	assetEntryId LONG,
	actionId VARCHAR(75) null,
	actionDate INTEGER,
	type_ INTEGER,
	value INTEGER,
	validity INTEGER,
	active_ BOOLEAN
);

create table SocialEquitySetting (
	equitySettingId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	classNameId LONG,
	actionId VARCHAR(75) null,
	type_ INTEGER,
	value INTEGER,
	validity INTEGER
);

create table SocialEquityUser (
	equityUserId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	contributionK DOUBLE,
	contributionB DOUBLE,
	participationK DOUBLE,
	participationB DOUBLE
);

create table Ticket (
	ticketId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	key_ VARCHAR(75) null,
	expirationDate DATE null
);

alter table User_ add socialContributionEquity DOUBLE;
alter table User_ add socialParticipationEquity DOUBLE;
alter table User_ add socialPersonalEquity DOUBLE;

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

create table ClusterGroup (
	clusterGroupId LONG not null primary key,
	clusterNodeIds VARCHAR(75) null,
	wholeCluster BOOLEAN
);

alter table User_ add digest VARCHAR(256) null;