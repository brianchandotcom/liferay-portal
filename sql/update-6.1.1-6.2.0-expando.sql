alter table ExpandoRow add modifiedDate DATE null;

COMMIT_TRANSACTION;

update ExpandoRow set modifiedDate = CURRENT_TIMESTAMP;