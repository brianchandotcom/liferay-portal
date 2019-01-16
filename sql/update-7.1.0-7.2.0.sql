alter table Layout add parentPlid LONG;
alter table Layout add leftPlid LONG;
alter table Layout add rightPlid LONG;
alter table Layout add system BOOLEAN;

COMMIT_TRANSACTION;

update Layout set system = FALSE;

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