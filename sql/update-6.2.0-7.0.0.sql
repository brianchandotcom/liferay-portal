alter table Layout drop column iconImage;

alter table LayoutRevision drop column iconImage;

alter table LayoutSet drop column logo;

alter table LayoutSetBranch drop column logo;

alter table Organization_ add logoId LONG;