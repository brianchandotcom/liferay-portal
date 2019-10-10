alter table Layout add mLayoutPageTemplateEntryId LONG;

COMMIT_TRANSACTION;

update Layout set mLayoutPageTemplateEntryId = 0;