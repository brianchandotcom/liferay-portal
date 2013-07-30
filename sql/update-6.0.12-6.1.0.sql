alter table AssetCategory add description STRING null;

alter table AssetEntry add classTypeId LONG;
alter table AssetEntry add layoutUuid VARCHAR(75) null;

update AssetEntry set classUuid = (select uuid_ from JournalArticleResource where AssetEntry.classPK = JournalArticleResource.resourcePrimKey) where visible = TRUE and classNameId = (select classNameId from ClassName_ where value = 'com.liferay.portlet.journal.model.JournalArticle');

alter table BlogsEntry add description STRING null;
alter table BlogsEntry add smallImage BOOLEAN;
alter table BlogsEntry add smallImageId LONG;
alter table BlogsEntry add smallImageURL STRING null;

alter table BookmarksEntry add resourceBlockId LONG;
alter table BookmarksEntry add description STRING null;

COMMIT_TRANSACTION;

update BookmarksEntry set description = comments;

alter table BookmarksEntry drop column comments;

alter table BookmarksFolder add resourceBlockId LONG;

alter table CalEvent add location STRING null;

update ClassName_ set value = 'com.liferay.portal.model.UserPersonalSite' where value = 'com.liferay.portal.model.UserPersonalCommunity';

alter table Company add active_ BOOLEAN;

COMMIT_TRANSACTION;

update Company set active_ = TRUE;

alter table Country add zipRequired BOOLEAN;

COMMIT_TRANSACTION;

update Country set zipRequired = TRUE;

create table DDLRecord (
	uuid_ VARCHAR(75) null,
	recordId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	versionUserId LONG,
	versionUserName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	DDMStorageId LONG,
	recordSetId LONG,
	version VARCHAR(75) null,
	displayIndex INTEGER
);

create table DDLRecordSet (
	uuid_ VARCHAR(75) null,
	recordSetId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	DDMStructureId LONG,
	recordSetKey VARCHAR(75) null,
	name STRING null,
	description STRING null,
	minDisplayRows INTEGER,
	scope INTEGER
);

create table DDLRecordVersion (
	recordVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	DDMStorageId LONG,
	recordSetId LONG,
	recordId LONG,
	version VARCHAR(75) null,
	displayIndex INTEGER,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table DDMContent (
	uuid_ VARCHAR(75) null,
	contentId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	xml TEXT null
);

create table DDMStorageLink (
	uuid_ VARCHAR(75) null,
	storageLinkId LONG not null primary key,
	classNameId LONG,
	classPK LONG,
	structureId LONG
);

create table DDMStructure (
	uuid_ VARCHAR(75) null,
	structureId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	structureKey VARCHAR(75) null,
	name STRING null,
	description STRING null,
	xsd TEXT null,
	storageType VARCHAR(75) null,
	type_ INTEGER
);

create table DDMStructureLink (
	structureLinkId LONG not null primary key,
	classNameId LONG,
	classPK LONG,
	structureId LONG
);

create table DDMTemplate (
	uuid_ VARCHAR(75) null,
	templateId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	structureId LONG,
	name STRING null,
	description STRING null,
	type_ VARCHAR(75) null,
	mode_ VARCHAR(75) null,
	language VARCHAR(75) null,
	script TEXT null
);

create table DLContent (
	contentId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	repositoryId LONG,
	path_ VARCHAR(255) null,
	version VARCHAR(75) null,
	data_ BLOB,
	size_ LONG
);

create table DLFileEntryMetadata (
	uuid_ VARCHAR(75) null,
	fileEntryMetadataId LONG not null primary key,
	DDMStorageId LONG,
	DDMStructureId LONG,
	fileEntryTypeId LONG,
	fileEntryId LONG,
	fileVersionId LONG
);

create table DLFileEntryType (
	uuid_ VARCHAR(75) null,
	fileEntryTypeId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null
);

create table DLFileEntryTypes_DDMStructures (
	fileEntryTypeId LONG not null,
	structureId LONG not null,
	primary key (fileEntryTypeId, structureId)
);

create table DLFileEntryTypes_DLFolders (
	fileEntryTypeId LONG not null,
	folderId LONG not null,
	primary key (fileEntryTypeId, folderId)
);

alter table DLFileEntry add repositoryId LONG;
alter table DLFileEntry add fileEntryTypeId LONG;
alter table DLFileEntry add smallImageId LONG;
alter table DLFileEntry add largeImageId LONG;
alter table DLFileEntry add custom1ImageId LONG;
alter table DLFileEntry add custom2ImageId LONG;

COMMIT_TRANSACTION;

update DLFileEntry set repositoryId = groupId;

alter table DLFileShortcut add repositoryId LONG;

COMMIT_TRANSACTION;

update DLFileShortcut set repositoryId = groupId;

alter table DLFileVersion add modifiedDate DATE null;
alter table DLFileVersion add repositoryId LONG;
alter table DLFileVersion add folderId LONG;
alter table DLFileVersion add fileEntryTypeId LONG;

COMMIT_TRANSACTION;

update DLFileVersion set modifiedDate = statusDate;
update DLFileVersion set repositoryId = groupId;

alter table DLFolder add repositoryId LONG;
alter table DLFolder add mountPoint BOOLEAN;
alter table DLFolder add defaultFileEntryTypeId LONG;
alter table DLFolder add overrideFileEntryTypes BOOLEAN;

COMMIT_TRANSACTION;

update DLFolder set repositoryId = groupId;
update DLFolder set mountPoint = FALSE;

create table DLSync (
	syncId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	fileId LONG,
	fileUuid VARCHAR(75) null,
	repositoryId LONG,
	parentFolderId LONG,
	name VARCHAR(255) null,
	event VARCHAR(75) null,
	type_ VARCHAR(75) null,
	version VARCHAR(75) null
);

alter table Group_ add site BOOLEAN;

update Group_ set name = 'User Personal Site' where name = 'User Personal Community';
update Group_ set type_ = 3 where classNameId = (select classNameId from ClassName_ where value = 'com.liferay.portal.model.Organization');

alter table JournalArticle add classNameId LONG null;
alter table JournalArticle add classPK LONG null;
alter table JournalArticle add layoutUuid VARCHAR(75) null;

COMMIT_TRANSACTION;

update JournalArticle set classNameId = 0;
update JournalArticle set classPK = 0;

drop index IX_FAD05595 on Layout;

alter table Layout drop column layoutPrototypeId;
alter table Layout drop column dlFolderId;

alter table Layout add createDate DATE null;
alter table Layout add modifiedDate DATE null;
alter table Layout add keywords STRING null;
alter table Layout add robots STRING null;
alter table Layout add layoutPrototypeUuid VARCHAR(75) null;
alter table Layout add layoutPrototypeLinkEnabled BOOLEAN null;
alter table Layout add sourcePrototypeLayoutUuid VARCHAR(75) null;

COMMIT_TRANSACTION;

update Layout set createDate = CURRENT_TIMESTAMP;
update Layout set modifiedDate = CURRENT_TIMESTAMP;

create table LayoutBranch (
	LayoutBranchId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	layoutSetBranchId LONG,
	plid LONG,
	name VARCHAR(75) null,
	description STRING null,
	master BOOLEAN
);

alter table LayoutPrototype add uuid_ VARCHAR(75) null;

create table LayoutRevision (
	layoutRevisionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	layoutSetBranchId LONG,
	layoutBranchId LONG,
	parentLayoutRevisionId LONG,
	head BOOLEAN,
	major BOOLEAN,
	plid LONG,
	privateLayout BOOLEAN,
	name STRING null,
	title STRING null,
	description STRING null,
	keywords STRING null,
	robots STRING null,
	typeSettings TEXT null,
	iconImage BOOLEAN,
	iconImageId LONG,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	wapThemeId VARCHAR(75) null,
	wapColorSchemeId VARCHAR(75) null,
	css STRING null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

alter table LayoutSet drop column layoutSetPrototypeId;

alter table LayoutSet add createDate DATE null;
alter table LayoutSet add modifiedDate DATE null;
alter table LayoutSet add layoutSetPrototypeUuid VARCHAR(75) null;
alter table LayoutSet add layoutSetPrototypeLinkEnabled BOOLEAN null;

COMMIT_TRANSACTION;

update LayoutSet set createDate = CURRENT_TIMESTAMP;
update LayoutSet set modifiedDate = CURRENT_TIMESTAMP;

create table LayoutSetBranch (
	layoutSetBranchId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	privateLayout BOOLEAN,
	name VARCHAR(75) null,
	description STRING null,
	master BOOLEAN
);

alter table LayoutSetPrototype add uuid_ VARCHAR(75) null;
alter table LayoutSetPrototype add createDate DATE null;
alter table LayoutSetPrototype add modifiedDate DATE null;

COMMIT_TRANSACTION;

update LayoutSetPrototype set createDate = CURRENT_TIMESTAMP;
update LayoutSetPrototype set modifiedDate = CURRENT_TIMESTAMP;

alter table MBCategory add displayStyle VARCHAR(75) null;

COMMIT_TRANSACTION;

update MBCategory set displayStyle = 'default';

alter table MBMessage add format VARCHAR(75) null;
alter table MBMessage add answer BOOLEAN;

COMMIT_TRANSACTION;

update MBMessage set format = 'bbcode';

alter table MBThread add question BOOLEAN;

create table MBThreadFlag (
	threadFlagId LONG not null primary key,
	userId LONG,
	modifiedDate DATE null,
	threadId LONG
);

create table MDRAction (
	uuid_ VARCHAR(75) null,
	actionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	ruleGroupInstanceId LONG,
	name STRING null,
	description STRING null,
	type_ VARCHAR(255) null,
	typeSettings TEXT null
);

create table MDRRule (
	uuid_ VARCHAR(75) null,
	ruleId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	ruleGroupId LONG,
	name STRING null,
	description STRING null,
	type_ VARCHAR(255) null,
	typeSettings TEXT null
);

create table MDRRuleGroup (
	uuid_ VARCHAR(75) null,
	ruleGroupId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null
);

create table MDRRuleGroupInstance (
	uuid_ VARCHAR(75) null,
	ruleGroupInstanceId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	ruleGroupId LONG,
	priority INTEGER
);

alter table Organization_ drop column leftOrganizationId;
alter table Organization_ drop column rightOrganizationId;

alter table Organization_ add treePath STRING null;

alter table PollsVote add companyId LONG;
alter table PollsVote add userName VARCHAR(75) null;
alter table PollsVote add createDate DATE null;
alter table PollsVote add modifiedDate DATE null;

create table Repository (
	uuid_ VARCHAR(75) null,
	repositoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	name VARCHAR(75) null,
	description STRING null,
	portletId VARCHAR(75) null,
	typeSettings TEXT null,
	dlFolderId LONG
);

create table RepositoryEntry (
	uuid_ VARCHAR(75) null,
	repositoryEntryId LONG not null primary key,
	groupId LONG,
	repositoryId LONG,
	mappedId VARCHAR(75) null
);

create table ResourceBlock (
	resourceBlockId LONG not null primary key,
	companyId LONG,
	groupId LONG,
	name VARCHAR(75) null,
	permissionsHash VARCHAR(75) null,
	referenceCount LONG
);

create table ResourceBlockPermission (
	resourceBlockPermissionId LONG not null primary key,
	resourceBlockId LONG,
	roleId LONG,
	actionIds LONG
);

drop index IX_8D83D0CE on ResourcePermission;
drop index IX_4A1F4402 on ResourcePermission;

create table ResourceTypePermission (
	resourceTypePermissionId LONG not null primary key,
	companyId LONG,
	groupId LONG,
	name VARCHAR(75) null,
	roleId LONG,
	actionIds LONG
);

create table SocialActivityAchievement (
	activityAchievementId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate LONG,
	name VARCHAR(75) null,
	firstInGroup BOOLEAN
);

create table SocialActivityCounter (
	activityCounterId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	name VARCHAR(75) null,
	ownerType INTEGER,
	currentValue INTEGER,
	totalValue INTEGER,
	graceValue INTEGER,
	startPeriod INTEGER,
	endPeriod INTEGER
);

create table SocialActivityLimit (
	activityLimitId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	classNameId LONG,
	classPK LONG,
	activityType INTEGER,
	activityCounterName VARCHAR(75) null,
	value VARCHAR(75) null
);

create table SocialActivitySetting (
	activitySettingId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	classNameId LONG,
	activityType INTEGER,
	name VARCHAR(75) null,
	value VARCHAR(1024) null
);

update Role_ set name = 'Site Administrator' where name = 'Community Administrator';
update Role_ set name = 'Site Content Reviewer' where name = 'Community Content Reviewer';
update Role_ set name = 'Site Member' where name = 'Community Member';
update Role_ set name = 'Site Owner' where name = 'Community Owner';
update Role_ set name = 'Organization User' where name = 'Organization Member';

alter table User_ add emailAddressVerified BOOLEAN;
alter table User_ add status int;

COMMIT_TRANSACTION;

update User_ set emailAddressVerified = TRUE;
update User_ set status = 0;
update User_ set status = 5 where active_ = FALSE;

alter table User_ drop column active_;

alter table UserGroup add addedByLDAPImport BOOLEAN;

alter table UserNotificationEvent add archived BOOLEAN;

alter table WorkflowDefinitionLink add classPK LONG;
alter table WorkflowDefinitionLink add typePK LONG;

COMMIT_TRANSACTION;

update WorkflowDefinitionLink set classPK = 0;
update WorkflowDefinitionLink set typePK = 0;

drop table QUARTZ_BLOB_TRIGGERS;
drop table QUARTZ_CALENDARS;
drop table QUARTZ_CRON_TRIGGERS;
drop table QUARTZ_FIRED_TRIGGERS;
drop table QUARTZ_JOB_DETAILS;
drop table QUARTZ_JOB_LISTENERS;
drop table QUARTZ_LOCKS;
drop table QUARTZ_PAUSED_TRIGGER_GRPS;
drop table QUARTZ_SCHEDULER_STATE;
drop table QUARTZ_SIMPLE_TRIGGERS;
drop table QUARTZ_TRIGGERS;
drop table QUARTZ_TRIGGER_LISTENERS;

create table QUARTZ_BLOB_TRIGGERS (
	SCHED_NAME VARCHAR(120) not null,
	TRIGGER_NAME VARCHAR(200) not null,
	TRIGGER_GROUP VARCHAR(200) not null,
	BLOB_DATA SBLOB null,
	primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create table QUARTZ_CALENDARS (
	SCHED_NAME VARCHAR(120) not null,
	CALENDAR_NAME VARCHAR(200) not null,
	CALENDAR SBLOB not null,
	primary key (SCHED_NAME,CALENDAR_NAME)
);

create table QUARTZ_CRON_TRIGGERS (
	SCHED_NAME VARCHAR(120) not null,
	TRIGGER_NAME VARCHAR(200) not null,
	TRIGGER_GROUP VARCHAR(200) not null,
	CRON_EXPRESSION VARCHAR(200) not null,
	TIME_ZONE_ID VARCHAR(80),
	primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create table QUARTZ_FIRED_TRIGGERS (
	SCHED_NAME VARCHAR(120) not null,
	ENTRY_ID VARCHAR(95) not null,
	TRIGGER_NAME VARCHAR(200) not null,
	TRIGGER_GROUP VARCHAR(200) not null,
	INSTANCE_NAME VARCHAR(200) not null,
	FIRED_TIME LONG not null,
	PRIORITY INTEGER not null,
	STATE VARCHAR(16) not null,
	JOB_NAME VARCHAR(200) null,
	JOB_GROUP VARCHAR(200) null,
	IS_NONCONCURRENT BOOLEAN NULL,
	REQUESTS_RECOVERY BOOLEAN NULL,
	primary key (SCHED_NAME, ENTRY_ID)
);

create table QUARTZ_JOB_DETAILS (
	SCHED_NAME VARCHAR(120) not null,
	JOB_NAME VARCHAR(200) not null,
	JOB_GROUP VARCHAR(200) not null,
	DESCRIPTION VARCHAR(250) null,
	JOB_CLASS_NAME VARCHAR(250) not null,
	IS_DURABLE BOOLEAN not null,
	IS_NONCONCURRENT BOOLEAN not null,
	IS_UPDATE_DATA BOOLEAN not null,
	REQUESTS_RECOVERY BOOLEAN not null,
	JOB_DATA SBLOB null,
	primary key (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

create table QUARTZ_LOCKS (
	SCHED_NAME VARCHAR(120) not null,
	LOCK_NAME VARCHAR(40) not null ,
	primary key (SCHED_NAME, LOCK_NAME)
);

create table QUARTZ_PAUSED_TRIGGER_GRPS (
	SCHED_NAME VARCHAR(120) not null,
	TRIGGER_GROUP VARCHAR(200) not null,
	primary key (SCHED_NAME, TRIGGER_GROUP)
);

create table QUARTZ_SCHEDULER_STATE (
	SCHED_NAME VARCHAR(120) not null,
	INSTANCE_NAME VARCHAR(200) not null,
	LAST_CHECKIN_TIME LONG not null,
	CHECKIN_INTERVAL LONG not null,
	primary key (SCHED_NAME, INSTANCE_NAME)
);

create table QUARTZ_SIMPLE_TRIGGERS (
	SCHED_NAME VARCHAR(120) not null,
	TRIGGER_NAME VARCHAR(200) not null,
	TRIGGER_GROUP VARCHAR(200) not null,
	REPEAT_COUNT LONG not null,
	REPEAT_INTERVAL LONG not null,
	TIMES_TRIGGERED LONG not null,
	primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create table QUARTZ_SIMPROP_TRIGGERS (
	SCHED_NAME VARCHAR(120) not null,
	TRIGGER_NAME VARCHAR(200) not null,
	TRIGGER_GROUP VARCHAR(200) not null,
	STR_PROP_1 VARCHAR(512) null,
	STR_PROP_2 VARCHAR(512) null,
	STR_PROP_3 VARCHAR(512) null,
	INT_PROP_1 INTEGER null,
	INT_PROP_2 INTEGER null,
	LONG_PROP_1 LONG null,
	LONG_PROP_2 LONG null,
	DEC_PROP_1 NUMERIC(13,4) null,
	DEC_PROP_2 NUMERIC(13,4) null,
	BOOL_PROP_1 BOOLEAN null,
	BOOL_PROP_2 BOOLEAN null,
	primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

create table QUARTZ_TRIGGERS (
	SCHED_NAME VARCHAR(120) not null,
	TRIGGER_NAME VARCHAR(200) not null,
	TRIGGER_GROUP VARCHAR(200) not null,
	JOB_NAME VARCHAR(200) not null,
	JOB_GROUP VARCHAR(200) not null,
	DESCRIPTION VARCHAR(250) null,
	NEXT_FIRE_TIME LONG null,
	PREV_FIRE_TIME LONG null,
	PRIORITY INTEGER null,
	TRIGGER_STATE VARCHAR(16) not null,
	TRIGGER_TYPE VARCHAR(8) not null,
	START_TIME LONG not null,
	END_TIME LONG null,
	CALENDAR_NAME VARCHAR(200) null,
	MISFIRE_INSTR INTEGER null,
	JOB_DATA SBLOB null,
	primary key  (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

COMMIT_TRANSACTION;

create index IX_88328984 on QUARTZ_JOB_DETAILS (SCHED_NAME, JOB_GROUP);
create index IX_779BCA37 on QUARTZ_JOB_DETAILS (SCHED_NAME, REQUESTS_RECOVERY);

create index IX_BE3835E5 on QUARTZ_FIRED_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
create index IX_4BD722BM on QUARTZ_FIRED_TRIGGERS (SCHED_NAME, TRIGGER_GROUP);
create index IX_204D31E8 on QUARTZ_FIRED_TRIGGERS (SCHED_NAME, INSTANCE_NAME);
create index IX_339E078M on QUARTZ_FIRED_TRIGGERS (SCHED_NAME, INSTANCE_NAME, REQUESTS_RECOVERY);
create index IX_5005E3AF on QUARTZ_FIRED_TRIGGERS (SCHED_NAME, JOB_NAME, JOB_GROUP);
create index IX_BC2F03B0 on QUARTZ_FIRED_TRIGGERS (SCHED_NAME, JOB_GROUP);

create index IX_186442A4 on QUARTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_STATE);
create index IX_1BA1F9DC on QUARTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP);
create index IX_91CA7CCE on QUARTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP, NEXT_FIRE_TIME, TRIGGER_STATE, MISFIRE_INSTR);
create index IX_D219AFDE on QUARTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP, TRIGGER_STATE);
create index IX_A85822A0 on QUARTZ_TRIGGERS (SCHED_NAME, JOB_NAME, JOB_GROUP);
create index IX_8AA50BE1 on QUARTZ_TRIGGERS (SCHED_NAME, JOB_GROUP);
create index IX_EEFE382A on QUARTZ_TRIGGERS (SCHED_NAME, NEXT_FIRE_TIME);
create index IX_F026CF4C on QUARTZ_TRIGGERS (SCHED_NAME, NEXT_FIRE_TIME, TRIGGER_STATE);
create index IX_F2DD7C7E on QUARTZ_TRIGGERS (SCHED_NAME, NEXT_FIRE_TIME, TRIGGER_STATE, MISFIRE_INSTR);
create index IX_1F92813C on QUARTZ_TRIGGERS (SCHED_NAME, NEXT_FIRE_TIME, MISFIRE_INSTR);
create index IX_99108B6E on QUARTZ_TRIGGERS (SCHED_NAME, TRIGGER_STATE);
create index IX_CD7132D0 on QUARTZ_TRIGGERS (SCHED_NAME, CALENDAR_NAME);