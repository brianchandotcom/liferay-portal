alter table Layout add headId LONG;
alter table Layout add head BOOLEAN;
alter table Layout add parentPlid LONG;
alter table Layout add classNameId LONG;
alter table Layout add classPK LONG;
alter table Layout add system_ BOOLEAN;
alter table Layout add publishDate DATE null;

COMMIT_TRANSACTION;

update Layout set headId = plid, head = FALSE, system_ = FALSE;

insert into Counter (name, currentId)
	select
		'com.liferay.portal.kernel.model.Layout' as name,
		max(plid) as currentId
	from Layout;

alter table LayoutSet add headId LONG;
alter table LayoutSet add head BOOLEAN;

COMMIT_TRANSACTION;

update LayoutSet set headId = layoutSetId, head = FALSE;

insert into Counter (name, currentId)
	select
		'com.liferay.portal.kernel.model.LayoutSet' as name,
		max(layoutSetId) as currentId
	from LayoutSet;

create table LayoutSetVersion (
	layoutSetVersionId LONG not null primary key,
	version INTEGER,
	layoutSetId LONG,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	privateLayout BOOLEAN,
	logoId LONG,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	css TEXT null,
	pageCount INTEGER,
	settings_ TEXT null,
	layoutSetPrototypeUuid VARCHAR(75) null,
	layoutSetPrototypeLinkEnabled BOOLEAN
);

COMMIT_TRANSACTION;

create table LayoutVersion (
	layoutVersionId LONG not null primary key,
	version INTEGER,
	uuid_ VARCHAR(75) null,
	plid LONG,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentPlid LONG,
	privateLayout BOOLEAN,
	layoutId LONG,
	parentLayoutId LONG,
	classNameId LONG,
	classPK LONG,
	name STRING null,
	title STRING null,
	description STRING null,
	keywords STRING null,
	robots STRING null,
	type_ VARCHAR(75) null,
	typeSettings TEXT null,
	hidden_ BOOLEAN,
	system_ BOOLEAN,
	friendlyURL VARCHAR(255) null,
	iconImageId LONG,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	css TEXT null,
	priority INTEGER,
	layoutPrototypeUuid VARCHAR(75) null,
	layoutPrototypeLinkEnabled BOOLEAN,
	sourcePrototypeLayoutUuid VARCHAR(75) null,
	publishDate DATE null,
	lastPublishDate DATE null
);

COMMIT_TRANSACTION;