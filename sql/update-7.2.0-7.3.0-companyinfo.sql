create table CompanyInfo (
	mvccVersion LONG default 0 not null,
	companyInfoId LONG not null primary key,
	companyId LONG,
	key_ TEXT null
);

create unique index IX_85C63FD7 on CompanyInfo (companyId);

COMMIT_TRANSACTION;