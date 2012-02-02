alter table DLFileVersion add uuid_ VARCHAR(75) null;

alter table LayoutSetBranch add logo BOOLEAN;
alter table LayoutSetBranch add logoId LONG;
alter table LayoutSetBranch add themeId VARCHAR(75) null;
alter table LayoutSetBranch add colorSchemeId VARCHAR(75) null;
alter table LayoutSetBranch add wapThemeId VARCHAR(75) null;
alter table LayoutSetBranch add wapColorSchemeId VARCHAR(75) null;
alter table LayoutSetBranch add css STRING null;
alter table LayoutSetBranch add settings_ STRING null;
alter table LayoutSetBranch add layoutSetPrototypeUuid VARCHAR(75) null;
alter table LayoutSetBranch add layoutSetPrototypeLinkEnabled BOOLEAN;

COMMIT_TRANSACTION;

update LayoutSetBranch set logo = TRUE;
update LayoutSetBranch set logoId = 0;
update LayoutSetBranch set layoutSetPrototypeLinkEnabled = FALSE;