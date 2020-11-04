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