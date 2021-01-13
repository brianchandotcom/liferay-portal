alter table Region add uuid_ VARCHAR(75) null;
alter table Region add defaultLanguageId VARCHAR(75) null;
alter table Region add companyId LONG;
alter table Region add userId LONG;
alter table Region add userName VARCHAR(75) null;
alter table Region add createDate DATE null;
alter table Region add modifiedDate DATE null;
alter table Region add position DOUBLE;
alter table Region add lastPublishDate DATE null;

create table RegionLocalization (
	mvccVersion LONG default 0 not null,
	regionLocalizationId LONG not null primary key,
	companyId LONG,
	regionId LONG,
	languageId VARCHAR(75) null,
	title VARCHAR(75) null
);

create unique index IX_A149763D on RegionLocalization (regionId, languageId[$COLUMN_LENGTH:75$]);

COMMIT_TRANSACTION;