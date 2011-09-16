alter table AssetCategory add description STRING null;

alter table AssetEntry add classTypeId LONG;
alter table AssetEntry add layoutUuid VARCHAR(75) null;

update AssetEntry set classUuid = (select uuid_ from JournalArticleResource where AssetEntry.classPK = JournalArticleResource.resourcePrimKey) where visible = TRUE and classNameId = (select classNameId from ClassName_ where value = 'com.liferay.portlet.journal.model.JournalArticle');

alter table BlogsEntry add description STRING null;
alter table BlogsEntry add smallImage BOOLEAN;
alter table BlogsEntry add smallImageId VARCHAR(75) null;
alter table BlogsEntry add smallImageURL STRING null;

alter table BookmarksEntry add resourceBlockId LONG;
alter table BookmarksEntry add description VARCHAR(75) null;

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
	storageType VARCHAR(75) null
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
	language VARCHAR(75) null,
	script TEXT null
);

create table DLContent (
	contentId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	portletId VARCHAR(75) null,
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
alter table DLFileEntry add smallImageId bigint;
alter table DLFileEntry add largeImageId bigint;
alter table DLFileEntry add custom1ImageId bigint;
alter table DLFileEntry add custom2ImageId bigint;

COMMIT_TRANSACTION;

update DLFileEntry set repositoryId = groupId;

alter table DLFileShortcut add repositoryId LONG;

COMMIT_TRANSACTION;

update DLFileShortcut set repositoryId = groupId;

alter table DLFileVersion add repositoryId LONG;
alter table DLFileVersion add fileEntryTypeId LONG;
alter table DLFileVersion add smallImageId bigint;
alter table DLFileVersion add largeImageId bigint;
alter table DLFileVersion add custom1ImageId bigint;
alter table DLFileVersion add custom2ImageId bigint;

COMMIT_TRANSACTION;

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
	repositoryId LONG,
	parentFolderId LONG,
	event VARCHAR(75) null,
	type_ VARCHAR(75) null
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

alter table Layout add createDate DATE null;
alter table Layout add modifiedDate DATE null;
alter table Layout add keywords STRING null;
alter table Layout add robots STRING null;
alter table Layout add layoutPrototypeUuid VARCHAR(75) null;
alter table Layout add layoutPrototypeLinkEnabled BOOLEAN null;
alter table Layout add templateLayoutUuid VARCHAR(75) null;
alter table Layout drop column layoutPrototypeId;
alter table Layout drop column dlFolderId;

update Layout set createDate = CURRENT_TIMESTAMP;
update Layout set modifiedDate = CURRENT_TIMESTAMP;

COMMIT_TRANSACTION;

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

alter table PollsVote add companyId LONG;
alter table PollsVote add userName VARCHAR(75) null;
alter table PollsVote add createDate DATE null;
alter table PollsVote add modifiedDate DATE null;

create table Repository (
	repositoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
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

create table ResourceTypePermission (
	resourceTypePermissionId LONG not null primary key,
	companyId LONG,
	groupId LONG,
	name VARCHAR(75) null,
	roleId LONG,
	actionIds LONG
);

update Role_ set name = 'Site Administrator' where name = 'Community Administrator';
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

alter table UserGroup add publicLayoutSetPrototypeId LONG;
alter table UserGroup add privateLayoutSetPrototypeId LONG;

alter table UserNotificationEvent add archived BOOLEAN;

alter table WorkflowDefinitionLink add classPK LONG;
alter table WorkflowDefinitionLink add typePK LONG;