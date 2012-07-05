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

alter table DLFileVersion add description STRING null;
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