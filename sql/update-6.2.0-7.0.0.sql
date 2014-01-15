create table ExportImportConfiguration (
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
	settings TEXT null
);

alter table Layout drop column iconImage;

alter table LayoutRevision drop column iconImage;

alter table LayoutSet drop column logo;

alter table LayoutSetBranch drop column logo;

alter table Organization_ add logoId LONG;