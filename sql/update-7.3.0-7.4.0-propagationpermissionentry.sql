create table PermissionPropagationEntry (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	permissionPropagationEntryId LONG not null,
	groupId LONG,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	propagation BOOLEAN,
	primary key (permissionPropagationEntryId, ctCollectionId)
);

create unique index IX_5D909E4C on PermissionPropagationEntry (groupId, companyId, classNameId, classPK, ctCollectionId);

COMMIT_TRANSACTION;