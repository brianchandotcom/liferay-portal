alter table Group_ add uuid_ VARCHAR(75) null;
alter table Group_ add treePath STRING null;
alter table Group_ add manualMembership BOOLEAN;
alter table Group_ add membershipRestriction INTEGER;
alter table Group_ add remoteStagingGroupCount INTEGER;

COMMIT_TRANSACTION;

update Group_ set manualMembership = TRUE;
update Group_ set membershipRestriction = 0;
update Group_ set site = FALSE where name = 'Control Panel';
update Group_ set site = TRUE where friendlyURL = '/global';