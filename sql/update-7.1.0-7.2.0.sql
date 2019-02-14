alter table Layout add parentPlid LONG;
alter table Layout add leftPlid LONG;
alter table Layout add rightPlid LONG;
alter table Layout add system_ BOOLEAN;
alter table Layout add headId LONG;
alter table Layout add head BOOLEAN;
alter table Layout add classNameId LONG;
alter table Layout add classPK LONG;
alter table Layout add system BOOLEAN;
alter table Layout add publishDate DATE null;

COMMIT_TRANSACTION;

update Layout set system_ = FALSE, head = TRUE, headId = -1 * plid;

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

insert into LayoutSetVersion

select
	layoutSetId as layoutSetVersionId,
	1 as version,
	layoutSetId,
	groupId,
	companyId,
	createDate,
	modifiedDate,
	privateLayout,
	logoId,
	themeId,
	colorSchemeId,
	css,
	pageCount,
	settings_,
	layoutSetPrototypeUuid,
	layoutSetPrototypeLinkEnabled
from LayoutSet;

insert into Counter (name, currentId)

select
	'com.liferay.portal.kernel.model.LayoutSet' as name,
	max(layoutSetId) as currentId
from LayoutSet;

insert into Counter (name, currentId)

select
	'com.liferay.portal.kernel.model.LayoutSetVersion' as name,
	max(layoutSetVersionId) as currentId
from LayoutSetVersion;

alter table LayoutSet add head BOOLEAN;
alter table LayoutSet add headId LONG;

drop index IX_48550691 on LayoutSet;

COMMIT_TRANSACTION;

update LayoutSet set headId = -1 * layoutSetId, head = TRUE;

create unique index IX_AAF037A5 on LayoutSet (groupId, privateLayout, head);
create unique index IX_28B7204D on LayoutSet (headId);

create unique index IX_7CDFE94F on LayoutSetVersion (groupId, privateLayout, version);
create index IX_1F8F53E4 on LayoutSetVersion (groupId, version);
create unique index IX_D88D5E2B on LayoutSetVersion (layoutSetId, version);
create index IX_61B651E9 on LayoutSetVersion (layoutSetPrototypeUuid[$COLUMN_LENGTH:75$], version);
create index IX_1D356397 on LayoutSetVersion (privateLayout, logoId, version);

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
	leftPlid LONG,
	rightPlid LONG,
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

insert into Counter (name, currentId)
	select
		'com.liferay.portal.kernel.model.Layout' as name,
		max(plid) as currentId
	from Layout;

insert into LayoutVersion (
	select
		plid as layoutVersionId,
		1 as version,
		uuid_,
		plid,
		groupId,
		companyId,
		userId,
		userName,
		createDate,
		modifiedDate,
		parentPlid,
		leftPlid,
		rightPlid,
		privateLayout,
		layoutId,
		parentLayoutId,
		classNameId LONG,
		classPK LONG,
		name,
		title,
		description,
		keywords,
		robots,
		type_,
		typeSettings,
		hidden_,
		system_,
		friendlyURL,
		iconImageId,
		themeId,
		colorSchemeId,
		css,
		priority,
		layoutPrototypeUuid,
		layoutPrototypeLinkEnabled,
		sourcePrototypeLayoutUuid,
		publishDate,
		lastPublishDate
	from
		Layout);

insert into Counter (name, currentId)
	select
		'com.liferay.portal.kernel.model.LayoutVersion' as name,
		max(layoutVersionId) as currentId
	from LayoutVersion;

COMMIT_TRANSACTION;

create unique index IX_C1143B45 on Layout (groupId, privateLayout, friendlyURL[$COLUMN_LENGTH:255$], head);
create unique index IX_D2DE1750 on Layout (groupId, privateLayout, layoutId, head);
create unique index IX_9D3FD85F on Layout (headId);
create unique index IX_92B7E1CB on Layout (uuid_[$COLUMN_LENGTH:75$], groupId, privateLayout, head);

create index IX_FBF6F939 on LayoutVersion (companyId, layoutPrototypeUuid[$COLUMN_LENGTH:75$], version);
create index IX_DEFEF354 on LayoutVersion (companyId, version);
create index IX_D0513E86 on LayoutVersion (groupId, leftPlid, rightPlid, privateLayout, version);
create unique index IX_6A5941DB on LayoutVersion (groupId, privateLayout, friendlyURL[$COLUMN_LENGTH:255$], version);
create unique index IX_B816A2A8 on LayoutVersion (groupId, privateLayout, layoutId, version);
create index IX_1A490C46 on LayoutVersion (groupId, privateLayout, parentLayoutId, priority, version);
create index IX_23BEB05E on LayoutVersion (groupId, privateLayout, parentLayoutId, version);
create index IX_C32CE133 on LayoutVersion (groupId, privateLayout, sourcePrototypeLayoutUuid[$COLUMN_LENGTH:75$], version);
create index IX_7E7D225A on LayoutVersion (groupId, privateLayout, type_[$COLUMN_LENGTH:75$], version);
create index IX_B9B3D961 on LayoutVersion (groupId, privateLayout, version);
create index IX_B3248089 on LayoutVersion (groupId, type_[$COLUMN_LENGTH:75$], version);
create index IX_F8CF8212 on LayoutVersion (groupId, version);
create index IX_182CF60F on LayoutVersion (iconImageId, version);
create index IX_B8044279 on LayoutVersion (layoutPrototypeUuid[$COLUMN_LENGTH:75$], version);
create index IX_1A7E559F on LayoutVersion (parentPlid, version);
create unique index IX_F852DF29 on LayoutVersion (plid, version);
create index IX_C5652CE4 on LayoutVersion (privateLayout, iconImageId, version);
create index IX_45E580A0 on LayoutVersion (sourcePrototypeLayoutUuid[$COLUMN_LENGTH:75$], version);
create index IX_E66E67A0 on LayoutVersion (uuid_[$COLUMN_LENGTH:75$], companyId, version);
create unique index IX_BD8C095 on LayoutVersion (uuid_[$COLUMN_LENGTH:75$], groupId, privateLayout, version);
create index IX_82FCA548 on LayoutVersion (uuid_[$COLUMN_LENGTH:75$], version);