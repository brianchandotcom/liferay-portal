alter table JournalArticle add folderId LONG;
alter table JournalArticle add treePath STRING null;

COMMIT_TRANSACTION;

update JournalArticle set folderId = 0;
update JournalArticle set treePath = '/';

create table JournalFolder (
	uuid_ VARCHAR(75) null,
	folderId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentFolderId LONG,
	treePath STRING null,
	name VARCHAR(100) null,
	description STRING null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);