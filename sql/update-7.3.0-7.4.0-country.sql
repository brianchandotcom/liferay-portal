update Country set number_ = 296 where a2 = 'KI' and number_ = 408;

alter table Country add uuid_ VARCHAR(75) null;
alter table Country add defaultLanguageId VARCHAR(75) null;
alter table Country add companyId LONG;
alter table Country add userId LONG;
alter table Country add userName VARCHAR(75) null;
alter table Country add createDate DATE null;
alter table Country add modifiedDate DATE null;
alter table Country add billingAllowed BOOLEAN;
alter table Country add groupFilterEnabled BOOLEAN;
alter table Country add position DOUBLE;
alter table Country add shippingAllowed BOOLEAN;
alter table Country add subjectToVAT BOOLEAN;
alter table Country add lastPublishDate DATE null;

update Country set billingAllowed = [$TRUE$];
update Country set groupFilterEnabled = [$FALSE$];
update Country set shippingAllowed = [$TRUE$];
update Country set subjectToVAT = [$FALSE$];

create table CountryLocalization (
	mvccVersion LONG default 0 not null,
	countryLocalizationId LONG not null primary key,
	companyId LONG,
	countryId LONG,
	languageId VARCHAR(75) null,
	title VARCHAR(75) null
);

create unique index IX_518948B3 on CountryLocalization (countryId, languageId[$COLUMN_LENGTH:75$]);
create index IX_B28C3033 on CountryLocalization (title[$COLUMN_LENGTH:75$]);

COMMIT_TRANSACTION;