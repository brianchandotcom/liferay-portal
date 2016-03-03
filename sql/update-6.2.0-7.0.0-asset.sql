alter table AssetEntry add listable BOOLEAN;

alter table AssetTag add uuid_ VARCHAR(75);

COMMIT_TRANSACTION;

update AssetEntry set listable = TRUE;

drop table AssetTagProperty;