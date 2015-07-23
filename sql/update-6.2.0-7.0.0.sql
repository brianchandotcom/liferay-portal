alter table AnnouncementsFlag add companyId LONG;

alter table AssetEntries_AssetCategories add companyId LONG;

create index IX_38A65B55 on AssetEntries_AssetCategories (companyId);

alter table AssetEntries_AssetTags add companyId LONG;

create index IX_112337B8 on AssetEntries_AssetTags (companyId);

alter table AssetEntry add listable BOOLEAN;

alter table AssetTag add uuid_ VARCHAR(75);

COMMIT_TRANSACTION;

update AssetEntry set listable = TRUE;

drop table AssetTagProperty;

alter table AssetTagStats add companyId LONG;

alter table BlogsEntry add subtitle STRING null;
alter table BlogsEntry add coverImageCaption STRING null;
alter table BlogsEntry add coverImageFileEntryId LONG;
alter table BlogsEntry add coverImageURL STRING null;
alter table BlogsEntry add smallImageFileEntryId LONG;

alter table BrowserTracker add companyId LONG;

alter table ClusterGroup add companyId LONG;

alter table Country add companyId LONG;

alter table DLFileEntryMetadata add companyId LONG;

alter table DLFileEntryMetadata drop column fileEntryTypeId;

create index IX_E69431B7 on DLFileEntryMetadata (uuid_, companyId);

drop index IX_F8E90438 on DLFileEntryMetadata;

alter table DLFileEntryTypes_DLFolders add companyId LONG;

create index IX_2E64D9F9 on DLFileEntryTypes_DLFolders (companyId);

alter table DLFolder add restrictionType INTEGER;

update DLFolder set restrictionType = 1 where overrideFileEntryTypes = 1;

alter table DLFolder drop column overrideFileEntryTypes;

alter table DLSyncEvent add companyId LONG;

create table ExportImportConfiguration (
	mvccVersion LONG default 0,
	exportImportConfigurationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(200) null,
	description STRING null,
	type_ INTEGER,
	settings_ TEXT null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

alter table Group_ add groupKey STRING;

update Group_ set groupKey = name;

alter table Group_ add inheritContent BOOLEAN;

alter table Groups_Orgs add companyId LONG;

create index IX_8BFD4548 on Groups_Orgs (companyId);

alter table Groups_Roles add companyId LONG;

create index IX_557D8550 on Groups_Roles (companyId);

alter table Groups_UserGroups add companyId LONG;

create index IX_676FC818 on Groups_UserGroups (companyId);

alter table Image add companyId LONG;

alter table Layout drop column iconImage;

alter table LayoutRevision drop column iconImage;

alter table LayoutSet drop column logo;

alter table LayoutSetBranch drop column logo;

alter table ListType add companyId LONG;

alter table Marketplace_Module add companyId LONG;

create index IX_896A375A on Marketplace_Module (uuid_, companyId);

alter table MBStatsUser add companyId LONG;

alter table Organization_ add logoId LONG;

alter table OrgGroupRole add companyId LONG;

alter table OrgLabor add companyId LONG;

alter table PasswordPolicyRel add companyId LONG;

alter table PasswordTracker add companyId LONG;

alter table PortletPreferences add companyId LONG;

alter table RatingsEntry add uuid_ VARCHAR(75) null;

alter table RatingsStats add companyId LONG;

alter table Region add companyId LONG;

insert into Region (regionId, countryId, regionCode, name, active_) values (33001, 33, 'AT-1', 'Burgenland', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33002, 33, 'AT-2', 'Kärnten', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33003, 33, 'AT-3', 'Niederösterreich', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33004, 33, 'AT-4', 'Oberösterreich', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33005, 33, 'AT-5', 'Salzburg', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33006, 33, 'AT-6', 'Steiermark', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33007, 33, 'AT-7', 'Tirol', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33008, 33, 'AT-8', 'Vorarlberg', TRUE);
insert into Region (regionId, countryId, regionCode, name, active_) values (33009, 33, 'AT-9', 'Vienna', TRUE);

update Region set regionCode = 'BB' where regionId = 4004 and regionCode = 'BR';
update Region set name = 'Monza e Brianza', regionCode = 'MB' where regionId = 8060 and regionCode = 'MZ';

alter table ResourceBlockPermission add companyId LONG;

alter table ResourcePermission add primKeyId LONG;
alter table ResourcePermission add viewActionId BOOLEAN;

alter table SCFrameworkVersi_SCProductVers add companyId LONG;

create index IX_630CC727 on SCFrameworkVersi_SCProductVers (companyId);

alter table SCLicense add companyId LONG;

alter table SCLicenses_SCProductEntries add companyId LONG;

create index IX_2EE8A074 on SCLicenses_SCProductEntries (companyId);

alter table ServiceComponent add companyId LONG;

alter table Subscription add groupId LONG;

alter table Team add uuid_ VARCHAR(75);

alter table TrashVersion add companyId LONG;

alter table UserGroupGroupRole add companyId LONG;

alter table UserGroupRole add companyId LONG;

alter table UserGroups_Teams add companyId LONG;

create index IX_2AC5356C on UserGroups_Teams (companyId);

alter table UserIdMapper add companyId LONG;

alter table UserNotificationEvent add deliveryType INTEGER;
alter table UserNotificationEvent add actionRequired BOOLEAN;

alter table Users_Groups add companyId LONG;

create index IX_3499B657 on Users_Groups (companyId);

alter table Users_Orgs add companyId LONG;

create index IX_5FBB883C on Users_Orgs (companyId);

alter table Users_Roles add companyId LONG;

create index IX_F987A0DC on Users_Roles (companyId);

alter table Users_Teams add companyId LONG;

create index IX_799F8283 on Users_Teams (companyId);

alter table Users_UserGroups add companyId LONG;

create index IX_BB65040C on Users_UserGroups (companyId);

alter table UserTrackerPath add companyId LONG;

alter table WikiPageResource add companyId LONG;

create index IX_13319367 on WikiPageResource (uuid_, companyId);