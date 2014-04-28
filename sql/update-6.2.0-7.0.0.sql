create table ExportImportConfiguration (
	mvccVersion LONG default 0,
	exportImportConfigurationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	type_ INTEGER,
	settings_ TEXT null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

alter table JournalFolder add restrictionType INTEGER;

create table JournalFolders_DDMStructures (
	structureId LONG not null,
	folderId LONG not null,
	primary key (structureId, folderId)
);

alter table Layout drop column iconImage;

alter table LayoutRevision drop column iconImage;

alter table LayoutSet drop column logo;

alter table LayoutSetBranch drop column logo;

alter table Organization_ add logoId LONG;

alter table RatingsEntry add uuid_ VARCHAR(75) null;