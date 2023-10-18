create table PermissionPropagation (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	permissionPropagationId LONG not null,
	groupId LONG,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	propagate BOOLEAN,
	primary key (permissionPropagationId, ctCollectionId)
);

create unique index IX_72E2E894 on PermissionPropagation (groupId, companyId, classNameId, classPK, ctCollectionId);

COMMIT_TRANSACTION;