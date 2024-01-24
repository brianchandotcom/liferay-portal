create index IX_FEAFC68A on Address (companyId, classNameId, classPK, listTypeId);
create index IX_923BD178 on Address (companyId, classNameId, classPK, mailing);
create index IX_9226DBB4 on Address (companyId, classNameId, classPK, primary_);
create index IX_5A2093E7 on Address (countryId);
create index IX_F5B50D63 on Address (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_C8E3E87D on Address (regionId);
create index IX_5BC8B0D4 on Address (userId);
create index IX_381E55DA on Address (uuid_[$COLUMN_LENGTH:75$]);

create index IX_37B0A8A2 on AnnouncementsDelivery (companyId);
create unique index IX_BA4413D5 on AnnouncementsDelivery (userId, type_[$COLUMN_LENGTH:75$]);

create index IX_14F06A6B on AnnouncementsEntry (classNameId, classPK, alert);
create index IX_94C04525 on AnnouncementsEntry (classNameId, classPK, companyId, alert);
create index IX_3F376E7C on AnnouncementsEntry (companyId);
create index IX_D49C2E66 on AnnouncementsEntry (userId);
create index IX_1AFBDE08 on AnnouncementsEntry (uuid_[$COLUMN_LENGTH:75$]);

create index IX_EF1F022A on AnnouncementsFlag (companyId);
create index IX_ED8CE4E8 on AnnouncementsFlag (entryId, userId, value);

create index IX_B69B6689 on AssetCategory (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_E639E2F6 on AssetCategory (groupId);
create index IX_24187A9F on AssetCategory (parentCategoryId, groupId);
create index IX_9DDD15EA on AssetCategory (parentCategoryId, name[$COLUMN_LENGTH:255$]);
create index IX_4D37BB00 on AssetCategory (uuid_[$COLUMN_LENGTH:75$]);
create index IX_962EDA40 on AssetCategory (vocabularyId, groupId, name[$COLUMN_LENGTH:255$]);
create index IX_3537E488 on AssetCategory (vocabularyId, name[$COLUMN_LENGTH:255$]);
create index IX_3334FA2A on AssetCategory (vocabularyId, parentCategoryId, groupId);
create unique index IX_8C99329D on AssetCategory (vocabularyId, parentCategoryId, name[$COLUMN_LENGTH:255$], ctCollectionId);

create index IX_112337B8 on AssetEntries_AssetTags (companyId);
create index IX_B2A61B55 on AssetEntries_AssetTags (tagId);

create unique index IX_7BF8337B on AssetEntry (classNameId, classPK, ctCollectionId);
create index IX_7306C60 on AssetEntry (companyId);
create index IX_75D42FF9 on AssetEntry (expirationDate);
create index IX_6418BB52 on AssetEntry (groupId, classNameId, publishDate, expirationDate);
create index IX_82C4BEF6 on AssetEntry (groupId, classNameId, visible);
create index IX_1EBA6821 on AssetEntry (groupId, classUuid[$COLUMN_LENGTH:75$]);
create index IX_FEC4A201 on AssetEntry (layoutUuid[$COLUMN_LENGTH:75$]);
create index IX_2E4E3885 on AssetEntry (publishDate);
create index IX_9029E15A on AssetEntry (visible);

create index IX_CAF671DB on AssetTag (name[$COLUMN_LENGTH:75$], groupId);
create index IX_562A3FC4 on AssetTag (uuid_[$COLUMN_LENGTH:75$]);

create index IX_B22D908C on AssetVocabulary (companyId);
create index IX_3C0C03A1 on AssetVocabulary (externalReferenceCode[$COLUMN_LENGTH:75$]);
create unique index IX_AE9F73AB on AssetVocabulary (groupId, name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_2F7F11EE on AssetVocabulary (groupId, visibilityType);
create index IX_55F58818 on AssetVocabulary (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_E7B95510 on BrowserTracker (userId);

create unique index IX_B27A301F on ClassName_ (value[$COLUMN_LENGTH:200$]);

create index IX_38EFE3FD on Company (logoId);
create index IX_12566EC2 on Company (mx[$COLUMN_LENGTH:200$]);
create unique index IX_EC00543C on Company (webId[$COLUMN_LENGTH:75$]);

create unique index IX_85C63FD7 on CompanyInfo (companyId);

create index IX_791914FA on Contact_ (classNameId, classPK);
create index IX_CE3F0B29 on Contact_ (userId, companyId);

create index IX_25D734CD on Country (active_);
create index IX_F9CD867E on Country (companyId, active_, billingAllowed);
create index IX_54E98CCD on Country (companyId, active_, shippingAllowed);
create unique index IX_7DA11A6F on Country (companyId, ctCollectionId, a2[$COLUMN_LENGTH:75$]);
create unique index IX_7DA11E30 on Country (companyId, ctCollectionId, a3[$COLUMN_LENGTH:75$]);
create unique index IX_B2A91789 on Country (companyId, ctCollectionId, name[$COLUMN_LENGTH:75$]);
create unique index IX_74AB3DC on Country (companyId, ctCollectionId, number_[$COLUMN_LENGTH:75$]);
create index IX_B59A9078 on Country (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_E22A5911 on CountryLocalization (countryId, languageId[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_4CB1B2B4 on DLFileEntry (companyId);
create index IX_E68FC539 on DLFileEntry (custom2ImageId, custom1ImageId, largeImageId, smallImageId);
create index IX_DBE629C9 on DLFileEntry (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_772ECDE7 on DLFileEntry (fileEntryTypeId);
create unique index IX_24247FF2 on DLFileEntry (folderId, groupId, ctCollectionId, fileName[$COLUMN_LENGTH:255$]);
create unique index IX_CAEFBFD6 on DLFileEntry (folderId, groupId, ctCollectionId, name[$COLUMN_LENGTH:255$]);
create unique index IX_DE7B15D1 on DLFileEntry (folderId, groupId, ctCollectionId, title[$COLUMN_LENGTH:255$]);
create index IX_F6E5E082 on DLFileEntry (folderId, groupId, fileEntryTypeId);
create index IX_E469833 on DLFileEntry (folderId, groupId, userId);
create index IX_8F6C75D0 on DLFileEntry (folderId, name[$COLUMN_LENGTH:255$]);
create index IX_57FFBBCA on DLFileEntry (folderId, repositoryId);
create index IX_43261870 on DLFileEntry (groupId, userId);
create index IX_D9492CF6 on DLFileEntry (mimeType[$COLUMN_LENGTH:75$]);
create index IX_9EE96CAD on DLFileEntry (repositoryId);
create index IX_64F0FE40 on DLFileEntry (uuid_[$COLUMN_LENGTH:75$]);

create index IX_4F40FE5E on DLFileEntryMetadata (fileEntryId);
create unique index IX_5DC2B977 on DLFileEntryMetadata (fileVersionId, DDMStructureId, ctCollectionId);
create index IX_D49AB5D1 on DLFileEntryMetadata (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_93ED0F06 on DLFileEntryType (groupId, ctCollectionId, dataDefinitionId);
create unique index IX_A5C4723D on DLFileEntryType (groupId, ctCollectionId, fileEntryTypeKey[$COLUMN_LENGTH:75$]);
create index IX_90724726 on DLFileEntryType (uuid_[$COLUMN_LENGTH:75$]);

create index IX_2E64D9F9 on DLFileEntryTypes_DLFolders (companyId);
create index IX_6E00A2EC on DLFileEntryTypes_DLFolders (folderId);

create index IX_4F6F93B2 on DLFileShortcut (status, companyId);
create index IX_71D97D98 on DLFileShortcut (status, groupId, folderId, active_);
create index IX_4B7247F6 on DLFileShortcut (toFileEntryId);
create index IX_4831EBE4 on DLFileShortcut (uuid_[$COLUMN_LENGTH:75$]);

create index IX_CF394FE on DLFileVersion (companyId, storeUUID[$COLUMN_LENGTH:255$]);
create unique index IX_10E504DF on DLFileVersion (fileEntryId, version[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_BC5541ED on DLFileVersion (groupId, folderId, version[$COLUMN_LENGTH:75$], title[$COLUMN_LENGTH:255$]);
create index IX_FFB3395C on DLFileVersion (mimeType[$COLUMN_LENGTH:75$]);
create index IX_6AA08268 on DLFileVersion (status, companyId);
create index IX_92309600 on DLFileVersion (status, displayDate);
create index IX_D50EAA41 on DLFileVersion (status, fileEntryId);
create index IX_799D5D47 on DLFileVersion (status, groupId, folderId);
create index IX_4BFABB9A on DLFileVersion (uuid_[$COLUMN_LENGTH:75$]);

create index IX_A74DB14C on DLFolder (companyId);
create index IX_C4410461 on DLFolder (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_F2EA1ACE on DLFolder (groupId);
create unique index IX_E8CB41E4 on DLFolder (parentFolderId, groupId, name[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_71D88798 on DLFolder (parentFolderId, groupId, status, hidden_);
create index IX_D97721CD on DLFolder (parentFolderId, groupId, status, mountPoint, hidden_);
create index IX_51556082 on DLFolder (parentFolderId, name[$COLUMN_LENGTH:255$]);
create index IX_56F3D47C on DLFolder (parentFolderId, repositoryId);
create index IX_6F63F140 on DLFolder (repositoryId, mountPoint);
create index IX_B199E2A6 on DLFolder (status, companyId);
create index IX_CBC408D8 on DLFolder (uuid_[$COLUMN_LENGTH:75$]);

create index IX_2A2CB130 on EmailAddress (companyId, classNameId, classPK, primary_);
create index IX_7B43CD8 on EmailAddress (userId);
create index IX_D24F3956 on EmailAddress (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_4A7D3605 on ExpandoColumn (tableId, name[$COLUMN_LENGTH:75$], ctCollectionId);

create unique index IX_F1A1F8BF on ExpandoRow (classPK, tableId, ctCollectionId);

create unique index IX_87D370E2 on ExpandoTable (companyId, classNameId, name[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_CAD04B0D on ExpandoValue (classPK, classNameId);
create unique index IX_F7AD05C3 on ExpandoValue (rowId_, columnId, ctCollectionId);
create unique index IX_DB301E6F on ExpandoValue (tableId, classPK, columnId, ctCollectionId);
create index IX_B71E92D5 on ExpandoValue (tableId, rowId_);

create index IX_1827A2E5 on ExportImportConfiguration (companyId);
create index IX_F8451AA8 on ExportImportConfiguration (groupId, status, type_);

create index IX_75017452 on Group_ (active_, type_);
create index IX_8257E37B on Group_ (classNameId, classPK);
create index IX_DDC91A87 on Group_ (companyId, active_);
create unique index IX_DBA56EF9 on Group_ (companyId, classNameId, ctCollectionId, classPK);
create index IX_ABE2D54 on Group_ (companyId, classNameId, parentGroupId);
create index IX_DF76A247 on Group_ (companyId, classNameId, site);
create unique index IX_3551EED4 on Group_ (companyId, ctCollectionId, friendlyURL[$COLUMN_LENGTH:255$]);
create unique index IX_42E6E774 on Group_ (companyId, ctCollectionId, groupKey[$COLUMN_LENGTH:150$]);
create index IX_5D75499E on Group_ (companyId, parentGroupId);
create index IX_B91488EC on Group_ (companyId, site, active_);
create index IX_7B216735 on Group_ (companyId, site, parentGroupId, inheritContent);
create index IX_CCBAA0D7 on Group_ (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_16218A38 on Group_ (liveGroupId);
create index IX_F981514E on Group_ (uuid_[$COLUMN_LENGTH:75$]);

create index IX_8BFD4548 on Groups_Orgs (companyId);
create index IX_6BBB7682 on Groups_Orgs (organizationId);

create index IX_557D8550 on Groups_Roles (companyId);
create index IX_3103EF3D on Groups_Roles (roleId);

create index IX_676FC818 on Groups_UserGroups (companyId);
create index IX_3B69160F on Groups_UserGroups (userGroupId);

create index IX_6A925A4D on Image (size_);

create index IX_B8E1E6E5 on Layout (classNameId, classPK);
create index IX_993CBA31 on Layout (groupId, masterLayoutPlid);
create unique index IX_502B1A93 on Layout (groupId, privateLayout, ctCollectionId, friendlyURL[$COLUMN_LENGTH:255$]);
create unique index IX_4FBF955A on Layout (groupId, privateLayout, ctCollectionId, layoutId);
create index IX_7DAA999F on Layout (groupId, privateLayout, parentLayoutId, hidden_);
create index IX_7399B71E on Layout (groupId, privateLayout, parentLayoutId, priority);
create index IX_8F78BAFA on Layout (groupId, privateLayout, parentLayoutId, system_);
create index IX_8CE8C0D9 on Layout (groupId, privateLayout, sourcePrototypeLayoutUuid[$COLUMN_LENGTH:75$]);
create index IX_A0364689 on Layout (groupId, privateLayout, status);
create index IX_1A1B61D2 on Layout (groupId, privateLayout, type_[$COLUMN_LENGTH:75$]);
create index IX_6EDC627B on Layout (groupId, type_[$COLUMN_LENGTH:75$]);
create index IX_23922F7D on Layout (iconImageId);
create index IX_667E3275 on Layout (layoutPrototypeUuid[$COLUMN_LENGTH:75$], companyId);
create index IX_1D4DCAA5 on Layout (parentPlid);
create index IX_3BC009C0 on Layout (privateLayout, iconImageId);
create index IX_39A18ECC on Layout (sourcePrototypeLayoutUuid[$COLUMN_LENGTH:75$]);
create index IX_D0822724 on Layout (uuid_[$COLUMN_LENGTH:75$]);

create index IX_B7546EDE on LayoutBranch (plid, layoutSetBranchId, master);
create unique index IX_6C2CC347 on LayoutBranch (plid, layoutSetBranchId, name[$COLUMN_LENGTH:75$]);

create index IX_EAB317C8 on LayoutFriendlyURL (companyId);
create index IX_C23A9814 on LayoutFriendlyURL (friendlyURL[$COLUMN_LENGTH:255$], companyId);
create unique index IX_7B314B62 on LayoutFriendlyURL (friendlyURL[$COLUMN_LENGTH:255$], groupId, languageId[$COLUMN_LENGTH:75$], ctCollectionId, privateLayout);
create index IX_D3B2D6DF on LayoutFriendlyURL (friendlyURL[$COLUMN_LENGTH:255$], plid);
create index IX_742EF04A on LayoutFriendlyURL (groupId);
create unique index IX_2069E0D0 on LayoutFriendlyURL (plid, languageId[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_9F80D54 on LayoutFriendlyURL (uuid_[$COLUMN_LENGTH:75$]);

create index IX_557A639F on LayoutPrototype (companyId, active_);
create index IX_CEF72136 on LayoutPrototype (uuid_[$COLUMN_LENGTH:75$]);

create index IX_3681C8D4 on LayoutRevision (layoutSetBranchId, status, head);
create index IX_27F4B32A on LayoutRevision (plid, head);
create index IX_DFD8E21E on LayoutRevision (plid, layoutSetBranchId, head, layoutBranchId);
create index IX_EE9E078A on LayoutRevision (plid, layoutSetBranchId, layoutBranchId);
create index IX_A5E8F80D on LayoutRevision (plid, layoutSetBranchId, parentLayoutRevisionId);
create index IX_81290E15 on LayoutRevision (plid, layoutSetBranchId, status);
create index IX_8EC3D2BC on LayoutRevision (plid, status);
create index IX_421223B1 on LayoutRevision (status);

create index IX_C629311 on LayoutSet (layoutSetPrototypeUuid[$COLUMN_LENGTH:75$], companyId);
create unique index IX_3486D629 on LayoutSet (privateLayout, groupId, ctCollectionId);
create index IX_1B698D9 on LayoutSet (privateLayout, logoId);

create index IX_CCF0DA29 on LayoutSetBranch (groupId, privateLayout, master);
create unique index IX_5FF18552 on LayoutSetBranch (groupId, privateLayout, name[$COLUMN_LENGTH:75$]);

create index IX_9178FC71 on LayoutSetPrototype (companyId, active_);
create index IX_C5D69B24 on LayoutSetPrototype (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_BF6DBF8A on ListType (companyId, type_[$COLUMN_LENGTH:75$], name[$COLUMN_LENGTH:75$]);

create index IX_C28C72EC on MembershipRequest (groupId, statusId);
create index IX_35AA8FA6 on MembershipRequest (groupId, userId, statusId);
create index IX_66D70879 on MembershipRequest (userId);

create index IX_6AF0D434 on OrgLabor (organizationId);

create unique index IX_F1E40A53 on Organization_ (companyId, name[$COLUMN_LENGTH:100$], ctCollectionId);
create index IX_4BCBAB21 on Organization_ (companyId, name[$COLUMN_LENGTH:100$], parentOrganizationId);
create index IX_418E4522 on Organization_ (companyId, parentOrganizationId);
create index IX_2D3C52CB on Organization_ (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_396D6B42 on Organization_ (uuid_[$COLUMN_LENGTH:75$]);

create index IX_2C1142E on PasswordPolicy (companyId, defaultPolicy);
create unique index IX_3FBFA9F4 on PasswordPolicy (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_51437A01 on PasswordPolicy (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_C3A17327 on PasswordPolicyRel (classNameId, classPK);
create index IX_CD25266E on PasswordPolicyRel (passwordPolicyId);

create index IX_326F75BD on PasswordTracker (userId);

create index IX_812CE07A on Phone (companyId, classNameId, classPK, primary_);
create index IX_F202B9CE on Phone (userId);
create index IX_EA6245A0 on Phone (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_7171B2E8 on PluginSetting (companyId, pluginId[$COLUMN_LENGTH:75$], pluginType[$COLUMN_LENGTH:75$]);

create unique index IX_D5E35599 on PortalPreferenceValue (portalPreferencesId, namespace[$COLUMN_LENGTH:255$], key_[$COLUMN_LENGTH:255$], index_);
create index IX_737DBC36 on PortalPreferenceValue (portalPreferencesId, namespace[$COLUMN_LENGTH:255$], key_[$COLUMN_LENGTH:255$], smallValue[$COLUMN_LENGTH:255$]);

create index IX_D1846D13 on PortalPreferences (ownerType, ownerId);

create unique index IX_12B5E51D on Portlet (companyId, portletId[$COLUMN_LENGTH:200$]);

create index IX_C6246ECD on PortletItem (groupId, classNameId, portletId[$COLUMN_LENGTH:200$], name[$COLUMN_LENGTH:75$]);

create unique index IX_A2EB0CCD on PortletPreferenceValue (name[$COLUMN_LENGTH:255$], portletPreferencesId, index_, ctCollectionId);
create index IX_91A4000C on PortletPreferenceValue (name[$COLUMN_LENGTH:255$], portletPreferencesId, smallValue[$COLUMN_LENGTH:255$]);
create index IX_EE8C5489 on PortletPreferenceValue (name[$COLUMN_LENGTH:255$], smallValue[$COLUMN_LENGTH:255$], companyId);

create index IX_69948606 on PortletPreferences (ownerId, ownerType, portletId[$COLUMN_LENGTH:200$], companyId);
create unique index IX_3BB8560B on PortletPreferences (ownerId, ownerType, portletId[$COLUMN_LENGTH:200$], plid, ctCollectionId);

create index IX_A1A8CB8B on RatingsEntry (classNameId, classPK, score);
create unique index IX_119FF2EF on RatingsEntry (classNameId, classPK, userId, ctCollectionId);
create index IX_C34DEAF2 on RatingsEntry (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_C286E0E2 on RatingsStats (classNameId, classPK, ctCollectionId);

create index IX_B91F79BD on RecentLayoutBranch (groupId);
create index IX_351E86E8 on RecentLayoutBranch (layoutBranchId);
create unique index IX_C27D6369 on RecentLayoutBranch (userId, layoutSetBranchId, plid);

create index IX_8D8A2724 on RecentLayoutRevision (groupId);
create index IX_DA0788DA on RecentLayoutRevision (layoutRevisionId);
create unique index IX_4C600BD0 on RecentLayoutRevision (userId, layoutSetBranchId, plid);

create index IX_711995A5 on RecentLayoutSetBranch (groupId);
create index IX_23FF0700 on RecentLayoutSetBranch (layoutSetBranchId);
create unique index IX_4654D204 on RecentLayoutSetBranch (userId, layoutSetId);

create index IX_2D9A426F on Region (active_);
create index IX_11FB3E42 on Region (countryId, active_);
create unique index IX_183BFDBA on Region (countryId, regionCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_48A89E9A on Region (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_982329B on RegionLocalization (regionId, languageId[$COLUMN_LENGTH:75$], ctCollectionId);

create unique index IX_8BD6BCA7 on Release_ (servletContextName[$COLUMN_LENGTH:75$]);

create unique index IX_97B21AA on Repository (groupId, name[$COLUMN_LENGTH:200$], portletId[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_74C17B04 on Repository (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_BC798E7 on RepositoryEntry (repositoryId, mappedId[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_B9B1506 on RepositoryEntry (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_EDB9986E on ResourceAction (name[$COLUMN_LENGTH:255$], actionId[$COLUMN_LENGTH:75$]);

create index IX_26284944 on ResourcePermission (companyId, primKey[$COLUMN_LENGTH:255$]);
create unique index IX_F2237D8E on ResourcePermission (companyId, scope, name[$COLUMN_LENGTH:255$], primKey[$COLUMN_LENGTH:255$], roleId, ctCollectionId);
create index IX_FA3E7EEB on ResourcePermission (companyId, scope, name[$COLUMN_LENGTH:255$], roleId, primKeyId, viewActionId);
create index IX_F6BAE86A on ResourcePermission (companyId, scope, primKey[$COLUMN_LENGTH:255$]);
create index IX_D5F1E2A2 on ResourcePermission (name[$COLUMN_LENGTH:255$]);
create index IX_A37A0588 on ResourcePermission (roleId);
create index IX_F4555981 on ResourcePermission (scope);

create unique index IX_CC85CC2C on Role_ (companyId, ctCollectionId, classNameId, classPK);
create unique index IX_D11C3796 on Role_ (companyId, ctCollectionId, name[$COLUMN_LENGTH:75$]);
create index IX_F436EC8E on Role_ (name[$COLUMN_LENGTH:75$]);
create index IX_A764467A on Role_ (subtype[$COLUMN_LENGTH:75$], type_);
create index IX_26DB26C5 on Role_ (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_4F0315B8 on ServiceComponent (buildNamespace[$COLUMN_LENGTH:75$], buildNumber);

create index IX_F542E9BC on SocialActivity (activitySetId);
create unique index IX_7E6A9AAD on SocialActivity (classNameId, classPK, groupId, userId, type_, receiverUserId, ctCollectionId, createDate);
create index IX_85370BF4 on SocialActivity (classNameId, classPK, mirrorActivityId);
create index IX_D0E9029E on SocialActivity (classNameId, classPK, type_);
create index IX_64B1BC66 on SocialActivity (companyId);
create index IX_2A2468 on SocialActivity (groupId);
create index IX_1271F25F on SocialActivity (mirrorActivityId);
create index IX_121CA3CB on SocialActivity (receiverUserId);
create index IX_3504B8BC on SocialActivity (userId);

create index IX_83E16F2F on SocialActivityAchievement (groupId, firstInGroup);
create index IX_8F6408F0 on SocialActivityAchievement (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_AABC18E9 on SocialActivityAchievement (groupId, userId, firstInGroup);
create unique index IX_5ED94F08 on SocialActivityAchievement (groupId, userId, name[$COLUMN_LENGTH:75$], ctCollectionId);

create unique index IX_E8A327E1 on SocialActivityCounter (classNameId, classPK, groupId, ownerType, name[$COLUMN_LENGTH:75$], ctCollectionId, endPeriod);
create unique index IX_4EE6EBA8 on SocialActivityCounter (classNameId, classPK, groupId, ownerType, name[$COLUMN_LENGTH:75$], ctCollectionId, startPeriod);

create unique index IX_12DA9D73 on SocialActivityLimit (classNameId, classPK, userId, groupId, activityType, activityCounterName[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_9E13F2DE on SocialActivitySet (groupId);
create index IX_5D1FA9E on SocialActivitySet (type_, classNameId, classPK);
create index IX_5B258A4 on SocialActivitySet (type_, classNameId, userId, classPK);
create index IX_ADDEF96B on SocialActivitySet (type_, classNameId, userId, groupId);

create index IX_25EEB8A0 on SocialActivitySetting (groupId, activityType, classNameId, name[$COLUMN_LENGTH:75$]);

create index IX_61171E99 on SocialRelation (companyId);
create index IX_5E1F07A2 on SocialRelation (type_, companyId);
create unique index IX_C31248D1 on SocialRelation (userId2, type_, userId1, ctCollectionId);
create index IX_BD7B682E on SocialRelation (userId2, userId1);
create index IX_F0CA24A5 on SocialRelation (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_30B64F87 on SocialRequest (classNameId, classPK, receiverUserId, type_, userId, ctCollectionId);
create index IX_A90FE5A0 on SocialRequest (companyId);
create index IX_3B45B8C9 on SocialRequest (status, classNameId, classPK, receiverUserId, type_);
create index IX_BBBDD26C on SocialRequest (status, classNameId, classPK, type_, userId);
create index IX_6ECAD9B7 on SocialRequest (status, receiverUserId);
create index IX_49D5872C on SocialRequest (uuid_[$COLUMN_LENGTH:75$]);

create index IX_FFCBB747 on SystemEvent (groupId, classNameId, classPK, type_);
create index IX_A19C89FF on SystemEvent (groupId, systemEventSetKey);

create index IX_93AB8545 on Team (companyId);
create unique index IX_D424D1E4 on Team (groupId, name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_7894C151 on Team (uuid_[$COLUMN_LENGTH:75$]);

create index IX_A3DCE03A on Ticket (classNameId, classPK, type_, companyId);
create index IX_B2468446 on Ticket (key_[$COLUMN_LENGTH:75$]);

create unique index IX_3F4FC96B on UserGroup (companyId, name[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_69771487 on UserGroup (companyId, parentUserGroupId);
create index IX_D65F8FE3 on UserGroup (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_5F1DD85A on UserGroup (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_A353F8EB on UserGroupGroupRole (userGroupId, roleId, groupId, ctCollectionId);

create unique index IX_E7D4B319 on UserGroupRole (userId, roleId, groupId, ctCollectionId);

create index IX_2AC5356C on UserGroups_Teams (companyId);
create index IX_7F187E63 on UserGroups_Teams (userGroupId);

create unique index IX_41A32E0D on UserIdMapper (type_[$COLUMN_LENGTH:75$], externalUserId[$COLUMN_LENGTH:75$]);
create unique index IX_2DE52B22 on UserIdMapper (type_[$COLUMN_LENGTH:75$], userId);

create unique index IX_8B6E3ACE on UserNotificationDelivery (userId, portletId[$COLUMN_LENGTH:200$], classNameId, notificationType, deliveryType);

create index IX_BF29100B on UserNotificationEvent (type_[$COLUMN_LENGTH:200$]);
create index IX_3BE9B7B1 on UserNotificationEvent (userId, delivered, deliveryType, archived, actionRequired);
create index IX_D8C49479 on UserNotificationEvent (userId, delivered, deliveryType, type_[$COLUMN_LENGTH:200$], archived);
create index IX_EBF87241 on UserNotificationEvent (userId, delivered, type_[$COLUMN_LENGTH:200$], timestamp);
create index IX_D60FB085 on UserNotificationEvent (userId, deliveryType, archived, actionRequired);
create index IX_ECD8CFEA on UserNotificationEvent (uuid_[$COLUMN_LENGTH:75$]);

create index IX_29BA1CF5 on UserTracker (companyId);
create index IX_46B0AE8E on UserTracker (sessionId[$COLUMN_LENGTH:200$]);
create index IX_E4EFBA8D on UserTracker (userId);

create index IX_14D8BCC0 on UserTrackerPath (userTrackerId);

create index IX_BCFDA257 on User_ (companyId, createDate, modifiedDate);
create unique index IX_77D89D58 on User_ (companyId, ctCollectionId, emailAddress[$COLUMN_LENGTH:254$]);
create unique index IX_6B7C3D77 on User_ (companyId, ctCollectionId, screenName[$COLUMN_LENGTH:75$]);
create index IX_1D731F03 on User_ (companyId, facebookId);
create index IX_B6E3AE1 on User_ (companyId, googleUserId[$COLUMN_LENGTH:75$]);
create index IX_EE8ABD19 on User_ (companyId, modifiedDate);
create index IX_89509087 on User_ (companyId, openId[$COLUMN_LENGTH:1024$]);
create index IX_AD7F7321 on User_ (companyId, status, type_);
create unique index IX_E902F853 on User_ (ctCollectionId, contactId);
create index IX_762F63C6 on User_ (emailAddress[$COLUMN_LENGTH:254$]);
create index IX_F76CE363 on User_ (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_A18034A4 on User_ (portraitId);
create index IX_E0422BDA on User_ (uuid_[$COLUMN_LENGTH:75$]);

create index IX_3499B657 on Users_Groups (companyId);
create index IX_F10B6C6B on Users_Groups (userId);

create index IX_5FBB883C on Users_Orgs (companyId);
create index IX_FB646CA6 on Users_Orgs (userId);

create index IX_F987A0DC on Users_Roles (companyId);
create index IX_C1A01806 on Users_Roles (userId);

create index IX_799F8283 on Users_Teams (companyId);
create index IX_A098EFBF on Users_Teams (userId);

create index IX_BB65040C on Users_UserGroups (companyId);
create index IX_66FF2503 on Users_UserGroups (userGroupId);

create unique index IX_76A64FBE on VirtualHost (hostname[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_4F1AD744 on VirtualHost (layoutSetId, companyId, defaultVirtualHost);
create index IX_774643D1 on VirtualHost (layoutSetId, hostname[$COLUMN_LENGTH:200$]);

create unique index IX_97DFA146 on WebDAVProps (classNameId, classPK);

create index IX_1AA07A6D on Website (companyId, classNameId, classPK, primary_);
create index IX_F75690BB on Website (userId);
create index IX_76F15D13 on Website (uuid_[$COLUMN_LENGTH:75$]);

create index IX_A0B53428 on WorkflowDefinitionLink (companyId, groupId, classPK, classNameId, typePK);
create index IX_A4DB1F0F on WorkflowDefinitionLink (companyId, workflowDefinitionName[$COLUMN_LENGTH:75$], workflowDefinitionVersion);

create index IX_415A7007 on WorkflowInstanceLink (groupId, companyId, classNameId, classPK);