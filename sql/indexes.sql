create index IX_FEAFC68A on Address (companyId, classNameId, classPK, listTypeId);
create index IX_923BD178 on Address (companyId, classNameId, classPK, mailing);
create index IX_9226DBB4 on Address (companyId, classNameId, classPK, primary_);
create index IX_5A2093E7 on Address (countryId);
create index IX_517EE3CE on Address (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_C8E3E87D on Address (regionId);
create index IX_5BC8B0D4 on Address (userId);
create index IX_258E4AC5 on Address (uuid_[$COLUMN_LENGTH:75$]);

create index IX_37B0A8A2 on AnnouncementsDelivery (companyId);
create unique index IX_6A6C3D40 on AnnouncementsDelivery (userId, type_[$COLUMN_LENGTH:75$]);

create index IX_14F06A6B on AnnouncementsEntry (classNameId, classPK, alert);
create index IX_94C04525 on AnnouncementsEntry (classNameId, classPK, companyId, alert);
create index IX_3F376E7C on AnnouncementsEntry (companyId);
create index IX_D49C2E66 on AnnouncementsEntry (userId);
create index IX_7DD3EFF3 on AnnouncementsEntry (uuid_[$COLUMN_LENGTH:75$]);

create index IX_EF1F022A on AnnouncementsFlag (companyId);
create index IX_ED8CE4E8 on AnnouncementsFlag (entryId, userId, value);

create index IX_AF732DF4 on AssetCategory (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_E639E2F6 on AssetCategory (groupId);
create index IX_24187A9F on AssetCategory (parentCategoryId, groupId);
create index IX_75E73731 on AssetCategory (parentCategoryId, name[$COLUMN_LENGTH:255$]);
create index IX_EBB7A0EB on AssetCategory (uuid_[$COLUMN_LENGTH:75$]);
create index IX_1E04A49B on AssetCategory (vocabularyId, groupId, name[$COLUMN_LENGTH:255$]);
create index IX_7D825753 on AssetCategory (vocabularyId, name[$COLUMN_LENGTH:255$]);
create index IX_3334FA2A on AssetCategory (vocabularyId, parentCategoryId, groupId);
create unique index IX_F374685A on AssetCategory (vocabularyId, parentCategoryId, name[$COLUMN_LENGTH:255$], ctCollectionId);

create index IX_112337B8 on AssetEntries_AssetTags (companyId);
create index IX_B2A61B55 on AssetEntries_AssetTags (tagId);

create unique index IX_7BF8337B on AssetEntry (classNameId, classPK, ctCollectionId);
create index IX_7306C60 on AssetEntry (companyId);
create index IX_75D42FF9 on AssetEntry (expirationDate);
create index IX_6418BB52 on AssetEntry (groupId, classNameId, publishDate, expirationDate);
create index IX_82C4BEF6 on AssetEntry (groupId, classNameId, visible);
create index IX_E5D9738C on AssetEntry (groupId, classUuid[$COLUMN_LENGTH:75$]);
create index IX_7163FD6C on AssetEntry (layoutUuid[$COLUMN_LENGTH:75$]);
create index IX_2E4E3885 on AssetEntry (publishDate);
create index IX_9029E15A on AssetEntry (visible);

create index IX_6B9E01D0 on AssetTag (name[$COLUMN_LENGTH:75$], groupId);
create index IX_8B5C1BAF on AssetTag (uuid_[$COLUMN_LENGTH:75$]);

create index IX_B22D908C on AssetVocabulary (companyId);
create index IX_19CA4F0C on AssetVocabulary (externalReferenceCode[$COLUMN_LENGTH:75$]);
create unique index IX_A460FB16 on AssetVocabulary (groupId, name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_2F7F11EE on AssetVocabulary (groupId, visibilityType);
create index IX_DEE3F203 on AssetVocabulary (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_E7B95510 on BrowserTracker (userId);

create unique index IX_BB3F4F7C on ClassName_ (value[$COLUMN_LENGTH:200$]);

create index IX_38EFE3FD on Company (logoId);
create index IX_273BA4B9 on Company (mx[$COLUMN_LENGTH:200$]);
create unique index IX_B5134427 on Company (webId[$COLUMN_LENGTH:75$]);

create unique index IX_85C63FD7 on CompanyInfo (companyId);

create index IX_791914FA on Contact_ (classNameId, classPK);
create index IX_CE3F0B29 on Contact_ (userId, companyId);

create index IX_25D734CD on Country (active_);
create index IX_F9CD867E on Country (companyId, active_, billingAllowed);
create index IX_54E98CCD on Country (companyId, active_, shippingAllowed);
create unique index IX_70DF2DA on Country (companyId, ctCollectionId, a2[$COLUMN_LENGTH:75$]);
create unique index IX_513F8C1B on Country (companyId, ctCollectionId, a3[$COLUMN_LENGTH:75$]);
create unique index IX_E4BE5EF4 on Country (companyId, ctCollectionId, name[$COLUMN_LENGTH:75$]);
create unique index IX_EA1193C7 on Country (companyId, ctCollectionId, number_[$COLUMN_LENGTH:75$]);
create index IX_7B110A63 on Country (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_8779317C on CountryLocalization (countryId, languageId[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_4CB1B2B4 on DLFileEntry (companyId);
create index IX_E68FC539 on DLFileEntry (custom2ImageId, custom1ImageId, largeImageId, smallImageId);
create index IX_F4FBD134 on DLFileEntry (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_772ECDE7 on DLFileEntry (fileEntryTypeId);
create unique index IX_573E229 on DLFileEntry (folderId, groupId, ctCollectionId, fileName[$COLUMN_LENGTH:255$]);
create unique index IX_D39038C5 on DLFileEntry (folderId, groupId, ctCollectionId, name[$COLUMN_LENGTH:255$]);
create unique index IX_40F9FDAA on DLFileEntry (folderId, groupId, ctCollectionId, title[$COLUMN_LENGTH:255$]);
create index IX_F6E5E082 on DLFileEntry (folderId, groupId, fileEntryTypeId);
create index IX_E469833 on DLFileEntry (folderId, groupId, userId);
create index IX_E01EE30B on DLFileEntry (folderId, name[$COLUMN_LENGTH:255$]);
create index IX_57FFBBCA on DLFileEntry (folderId, repositoryId);
create index IX_43261870 on DLFileEntry (groupId, userId);
create index IX_64A27BE1 on DLFileEntry (mimeType[$COLUMN_LENGTH:75$]);
create index IX_9EE96CAD on DLFileEntry (repositoryId);
create index IX_1B6EC42B on DLFileEntry (uuid_[$COLUMN_LENGTH:75$]);

create index IX_4F40FE5E on DLFileEntryMetadata (fileEntryId);
create unique index IX_5DC2B977 on DLFileEntryMetadata (fileVersionId, DDMStructureId, ctCollectionId);
create index IX_96BA093C on DLFileEntryMetadata (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_93ED0F06 on DLFileEntryType (groupId, ctCollectionId, dataDefinitionId);
create unique index IX_DE3557A8 on DLFileEntryType (groupId, ctCollectionId, fileEntryTypeKey[$COLUMN_LENGTH:75$]);
create index IX_D2E89E11 on DLFileEntryType (uuid_[$COLUMN_LENGTH:75$]);

create index IX_2E64D9F9 on DLFileEntryTypes_DLFolders (companyId);
create index IX_6E00A2EC on DLFileEntryTypes_DLFolders (folderId);

create index IX_4F6F93B2 on DLFileShortcut (status, companyId);
create index IX_71D97D98 on DLFileShortcut (status, groupId, folderId, active_);
create index IX_4B7247F6 on DLFileShortcut (toFileEntryId);
create index IX_8BF477CF on DLFileShortcut (uuid_[$COLUMN_LENGTH:75$]);

create index IX_4A98AC9D on DLFileVersion (companyId, storeUUID[$COLUMN_LENGTH:255$]);
create unique index IX_6445EA4A on DLFileVersion (fileEntryId, version[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_EB8FF6F9 on DLFileVersion (groupId, folderId, version[$COLUMN_LENGTH:75$], title[$COLUMN_LENGTH:255$]);
create index IX_62805947 on DLFileVersion (mimeType[$COLUMN_LENGTH:75$]);
create index IX_6AA08268 on DLFileVersion (status, companyId);
create index IX_D50EAA41 on DLFileVersion (status, fileEntryId);
create index IX_799D5D47 on DLFileVersion (status, groupId, folderId);
create index IX_7EFE5085 on DLFileVersion (uuid_[$COLUMN_LENGTH:75$]);

create index IX_A74DB14C on DLFolder (companyId);
create index IX_E8376FCC on DLFolder (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_F2EA1ACE on DLFolder (groupId);
create unique index IX_AF887B73 on DLFolder (parentFolderId, groupId, name[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_71D88798 on DLFolder (parentFolderId, groupId, status, hidden_);
create index IX_D97721CD on DLFolder (parentFolderId, groupId, status, mountPoint, hidden_);
create index IX_A4C7FB99 on DLFolder (parentFolderId, name[$COLUMN_LENGTH:255$]);
create index IX_56F3D47C on DLFolder (parentFolderId, repositoryId);
create index IX_6F63F140 on DLFolder (repositoryId, mountPoint);
create index IX_B199E2A6 on DLFolder (status, companyId);
create index IX_4F2A92C3 on DLFolder (uuid_[$COLUMN_LENGTH:75$]);

create index IX_2A2CB130 on EmailAddress (companyId, classNameId, classPK, primary_);
create index IX_7B43CD8 on EmailAddress (userId);
create index IX_83869841 on EmailAddress (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_7900C70 on ExpandoColumn (tableId, name[$COLUMN_LENGTH:75$], ctCollectionId);

create unique index IX_F1A1F8BF on ExpandoRow (classPK, tableId, ctCollectionId);

create unique index IX_C59136CD on ExpandoTable (companyId, classNameId, name[$COLUMN_LENGTH:75$], ctCollectionId);

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
create unique index IX_9AA5F487 on Group_ (companyId, ctCollectionId, friendlyURL[$COLUMN_LENGTH:255$]);
create unique index IX_FCC3EF21 on Group_ (companyId, ctCollectionId, groupKey[$COLUMN_LENGTH:150$]);
create index IX_5D75499E on Group_ (companyId, parentGroupId);
create index IX_B91488EC on Group_ (companyId, site, active_);
create index IX_7B216735 on Group_ (companyId, site, parentGroupId, inheritContent);
create index IX_FE4C3542 on Group_ (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_16218A38 on Group_ (liveGroupId);
create index IX_FF50439 on Group_ (uuid_[$COLUMN_LENGTH:75$]);

create index IX_8BFD4548 on Groups_Orgs (companyId);
create index IX_6BBB7682 on Groups_Orgs (organizationId);

create index IX_557D8550 on Groups_Roles (companyId);
create index IX_3103EF3D on Groups_Roles (roleId);

create index IX_676FC818 on Groups_UserGroups (companyId);
create index IX_3B69160F on Groups_UserGroups (userGroupId);

create index IX_6A925A4D on Image (size_);

create index IX_B8E1E6E5 on Layout (classNameId, classPK);
create index IX_993CBA31 on Layout (groupId, masterLayoutPlid);
create unique index IX_C58BE628 on Layout (groupId, privateLayout, ctCollectionId, friendlyURL[$COLUMN_LENGTH:255$]);
create unique index IX_4FBF955A on Layout (groupId, privateLayout, ctCollectionId, layoutId);
create index IX_7DAA999F on Layout (groupId, privateLayout, parentLayoutId, hidden_);
create index IX_7399B71E on Layout (groupId, privateLayout, parentLayoutId, priority);
create index IX_8F78BAFA on Layout (groupId, privateLayout, parentLayoutId, system_);
create index IX_135C4044 on Layout (groupId, privateLayout, sourcePrototypeLayoutUuid[$COLUMN_LENGTH:75$]);
create index IX_A0364689 on Layout (groupId, privateLayout, status);
create index IX_EDC5AABD on Layout (groupId, privateLayout, type_[$COLUMN_LENGTH:75$]);
create index IX_A900BCE6 on Layout (groupId, type_[$COLUMN_LENGTH:75$]);
create index IX_23922F7D on Layout (iconImageId);
create index IX_7B63532A on Layout (layoutPrototypeUuid[$COLUMN_LENGTH:75$], companyId);
create index IX_1D4DCAA5 on Layout (parentPlid);
create index IX_3BC009C0 on Layout (privateLayout, iconImageId);
create index IX_A5DD96B7 on Layout (sourcePrototypeLayoutUuid[$COLUMN_LENGTH:75$]);
create index IX_3316930F on Layout (uuid_[$COLUMN_LENGTH:75$]);

create index IX_B7546EDE on LayoutBranch (plid, layoutSetBranchId, master);
create unique index IX_4444BFB2 on LayoutBranch (plid, layoutSetBranchId, name[$COLUMN_LENGTH:75$]);

create index IX_EAB317C8 on LayoutFriendlyURL (companyId);
create unique index IX_874A8DEA on LayoutFriendlyURL (friendlyURL[$COLUMN_LENGTH:255$], languageId[$COLUMN_LENGTH:75$], ctCollectionId, groupId, privateLayout);
create index IX_4D784752 on LayoutFriendlyURL (plid, friendlyURL[$COLUMN_LENGTH:255$]);
create unique index IX_3FCE63BB on LayoutFriendlyURL (plid, languageId[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_E94B813F on LayoutFriendlyURL (uuid_[$COLUMN_LENGTH:75$]);

create index IX_557A639F on LayoutPrototype (companyId, active_);
create index IX_B20BD021 on LayoutPrototype (uuid_[$COLUMN_LENGTH:75$]);

create index IX_3681C8D4 on LayoutRevision (layoutSetBranchId, status, head);
create index IX_27F4B32A on LayoutRevision (plid, head);
create index IX_DFD8E21E on LayoutRevision (plid, layoutSetBranchId, head, layoutBranchId);
create index IX_EE9E078A on LayoutRevision (plid, layoutSetBranchId, layoutBranchId);
create index IX_A5E8F80D on LayoutRevision (plid, layoutSetBranchId, parentLayoutRevisionId);
create index IX_81290E15 on LayoutRevision (plid, layoutSetBranchId, status);
create index IX_8EC3D2BC on LayoutRevision (plid, status);
create index IX_421223B1 on LayoutRevision (status);

create index IX_508D4DC6 on LayoutSet (layoutSetPrototypeUuid[$COLUMN_LENGTH:75$], companyId);
create unique index IX_3486D629 on LayoutSet (privateLayout, groupId, ctCollectionId);
create index IX_1B698D9 on LayoutSet (privateLayout, logoId);

create index IX_CCF0DA29 on LayoutSetBranch (groupId, privateLayout, master);
create unique index IX_22A70E3D on LayoutSetBranch (groupId, privateLayout, name[$COLUMN_LENGTH:75$]);

create index IX_9178FC71 on LayoutSetPrototype (companyId, active_);
create index IX_85A9070F on LayoutSetPrototype (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_81936F20 on ListType (companyId, type_[$COLUMN_LENGTH:75$], name[$COLUMN_LENGTH:75$]);

create index IX_C28C72EC on MembershipRequest (groupId, statusId);
create index IX_35AA8FA6 on MembershipRequest (groupId, userId, statusId);
create index IX_66D70879 on MembershipRequest (userId);

create index IX_6AF0D434 on OrgLabor (organizationId);

create unique index IX_696D9483 on Organization_ (companyId, name[$COLUMN_LENGTH:100$], ctCollectionId);
create index IX_3FB0D151 on Organization_ (companyId, name[$COLUMN_LENGTH:100$], parentOrganizationId);
create index IX_418E4522 on Organization_ (companyId, parentOrganizationId);
create index IX_D7D56536 on Organization_ (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_13A49C2D on Organization_ (uuid_[$COLUMN_LENGTH:75$]);

create index IX_2C1142E on PasswordPolicy (companyId, defaultPolicy);
create unique index IX_8AC68DDF on PasswordPolicy (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_A806D56C on PasswordPolicy (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_C3A17327 on PasswordPolicyRel (classNameId, classPK);
create index IX_CD25266E on PasswordPolicyRel (passwordPolicyId);

create index IX_326F75BD on PasswordTracker (userId);

create index IX_812CE07A on Phone (companyId, classNameId, classPK, primary_);
create index IX_F202B9CE on Phone (userId);
create index IX_A3D29B8B on Phone (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_4C00753E on PluginSetting (companyId, pluginId[$COLUMN_LENGTH:75$], pluginType[$COLUMN_LENGTH:75$]);

create unique index IX_14352091 on PortalPreferenceValue (portalPreferencesId, namespace[$COLUMN_LENGTH:255$], key_[$COLUMN_LENGTH:255$], index_);
create index IX_1FEBB46D on PortalPreferenceValue (portalPreferencesId, namespace[$COLUMN_LENGTH:255$], key_[$COLUMN_LENGTH:255$], smallValue[$COLUMN_LENGTH:255$]);

create index IX_D1846D13 on PortalPreferences (ownerType, ownerId);

create unique index IX_DDCB453E on Portlet (companyId, portletId[$COLUMN_LENGTH:200$]);

create index IX_E9A4B57 on PortletItem (groupId, classNameId, portletId[$COLUMN_LENGTH:200$], name[$COLUMN_LENGTH:75$]);

create unique index IX_1F5DD046 on PortletPreferenceValue (name[$COLUMN_LENGTH:255$], portletPreferencesId, index_, ctCollectionId);
create index IX_9FE86AF6 on PortletPreferenceValue (name[$COLUMN_LENGTH:255$], portletPreferencesId, smallValue[$COLUMN_LENGTH:255$]);
create index IX_F57ADA65 on PortletPreferenceValue (name[$COLUMN_LENGTH:255$], smallValue[$COLUMN_LENGTH:255$], companyId);

create index IX_793D6FEF on PortletPreferences (ownerId, ownerType, portletId[$COLUMN_LENGTH:200$], companyId);
create unique index IX_3435082 on PortletPreferences (ownerId, ownerType, portletId[$COLUMN_LENGTH:200$], plid, ctCollectionId);

create index IX_A1A8CB8B on RatingsEntry (classNameId, classPK, score);
create unique index IX_119FF2EF on RatingsEntry (classNameId, classPK, userId, ctCollectionId);
create index IX_787863DD on RatingsEntry (uuid_[$COLUMN_LENGTH:75$]);

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
create unique index IX_594AE7A5 on Region (countryId, regionCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_4DCB385 on Region (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_FDEE206 on RegionLocalization (regionId, languageId[$COLUMN_LENGTH:75$], ctCollectionId);

create unique index IX_75944912 on Release_ (servletContextName[$COLUMN_LENGTH:75$]);

create unique index IX_707E0436 on Repository (groupId, name[$COLUMN_LENGTH:200$], portletId[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_DF4536EF on Repository (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_8809B450 on RepositoryEntry (repositoryId, mappedId[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_AC7FBBF1 on RepositoryEntry (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_B67DEE02 on ResourceAction (name[$COLUMN_LENGTH:255$], actionId[$COLUMN_LENGTH:75$]);

create index IX_89AD8017 on ResourcePermission (companyId, primKey[$COLUMN_LENGTH:255$]);
create unique index IX_DBE2592 on ResourcePermission (companyId, scope, name[$COLUMN_LENGTH:255$], primKey[$COLUMN_LENGTH:255$], roleId, ctCollectionId);
create index IX_328E58AC on ResourcePermission (companyId, scope, name[$COLUMN_LENGTH:255$], roleId, primKeyId, viewActionId);
create index IX_2F1DF4B1 on ResourcePermission (companyId, scope, primKey[$COLUMN_LENGTH:255$]);
create index IX_13280D79 on ResourcePermission (name[$COLUMN_LENGTH:255$]);
create index IX_A37A0588 on ResourcePermission (roleId);
create index IX_F4555981 on ResourcePermission (scope);

create unique index IX_CC85CC2C on Role_ (companyId, ctCollectionId, classNameId, classPK);
create unique index IX_E25F681 on Role_ (companyId, ctCollectionId, name[$COLUMN_LENGTH:75$]);
create index IX_610C7F79 on Role_ (name[$COLUMN_LENGTH:75$]);
create index IX_52D1ADAF on Role_ (subtype[$COLUMN_LENGTH:75$], type_);
create index IX_362C7830 on Role_ (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_4AE7832D on ServiceComponent (buildNamespace[$COLUMN_LENGTH:75$], buildNumber);

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
create unique index IX_CD9A02CF on Team (groupId, name[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_58BB54BC on Team (uuid_[$COLUMN_LENGTH:75$]);

create index IX_A3DCE03A on Ticket (classNameId, classPK, type_, companyId);
create index IX_C85B0B31 on Ticket (key_[$COLUMN_LENGTH:75$]);

create unique index IX_C973BE4C on UserGroup (companyId, name[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_69771487 on UserGroup (companyId, parentUserGroupId);
create index IX_324F264E on UserGroup (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_D0338D45 on UserGroup (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_A353F8EB on UserGroupGroupRole (userGroupId, roleId, groupId, ctCollectionId);

create unique index IX_E7D4B319 on UserGroupRole (userId, roleId, groupId, ctCollectionId);

create index IX_2AC5356C on UserGroups_Teams (companyId);
create index IX_7F187E63 on UserGroups_Teams (userGroupId);

create unique index IX_250B93E3 on UserIdMapper (type_[$COLUMN_LENGTH:75$], externalUserId[$COLUMN_LENGTH:75$]);
create unique index IX_5CAEF20D on UserIdMapper (type_[$COLUMN_LENGTH:75$], userId);

create unique index IX_D9E1D789 on UserNotificationDelivery (userId, portletId[$COLUMN_LENGTH:200$], classNameId, notificationType, deliveryType);

create index IX_8829DB10 on UserNotificationEvent (type_[$COLUMN_LENGTH:200$]);
create index IX_D60FB085 on UserNotificationEvent (userId, deliveryType, archived, actionRequired);
create index IX_441EED81 on UserNotificationEvent (userId, deliveryType, archived, delivered, actionRequired);
create index IX_45317CDE on UserNotificationEvent (userId, deliveryType, archived, delivered, type_[$COLUMN_LENGTH:200$]);
create index IX_C5249474 on UserNotificationEvent (userId, deliveryType, delivered, type_[$COLUMN_LENGTH:200$]);
create index IX_E7D71CD5 on UserNotificationEvent (uuid_[$COLUMN_LENGTH:75$]);
create index IX_7F6985FF on UserNotificationEvent (userId, type_[$COLUMN_LENGTH:200$], timestamp, delivered);

create index IX_29BA1CF5 on UserTracker (companyId);
create index IX_2A9F7C6D on UserTracker (sessionId[$COLUMN_LENGTH:200$]);
create index IX_E4EFBA8D on UserTracker (userId);

create index IX_14D8BCC0 on UserTrackerPath (userTrackerId);

create index IX_BCFDA257 on User_ (companyId, createDate, modifiedDate);
create unique index IX_899E2902 on User_ (companyId, ctCollectionId, emailAddress[$COLUMN_LENGTH:254$]);
create unique index IX_C7C141E2 on User_ (companyId, ctCollectionId, screenName[$COLUMN_LENGTH:75$]);
create index IX_1D731F03 on User_ (companyId, facebookId);
create index IX_6728664C on User_ (companyId, googleUserId[$COLUMN_LENGTH:75$]);
create index IX_EE8ABD19 on User_ (companyId, modifiedDate);
create index IX_78F016F5 on User_ (companyId, openId[$COLUMN_LENGTH:1024$]);
create index IX_AD7F7321 on User_ (companyId, status, type_);
create unique index IX_E902F853 on User_ (ctCollectionId, contactId);
create index IX_38D55354 on User_ (emailAddress[$COLUMN_LENGTH:254$]);
create index IX_2EEFB9CE on User_ (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_A18034A4 on User_ (portraitId);
create index IX_3B6B20C5 on User_ (uuid_[$COLUMN_LENGTH:75$]);

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

create unique index IX_614F0939 on VirtualHost (hostname[$COLUMN_LENGTH:200$], ctCollectionId);
create index IX_4F1AD744 on VirtualHost (layoutSetId, companyId, defaultVirtualHost);
create index IX_A3C8DF0A on VirtualHost (layoutSetId, hostname[$COLUMN_LENGTH:200$]);

create unique index IX_97DFA146 on WebDAVProps (classNameId, classPK);

create index IX_1AA07A6D on Website (companyId, classNameId, classPK, primary_);
create index IX_F75690BB on Website (userId);
create index IX_B9A67B7E on Website (uuid_[$COLUMN_LENGTH:75$]);

create index IX_A0B53428 on WorkflowDefinitionLink (companyId, groupId, classPK, classNameId, typePK);
create index IX_5AB577C4 on WorkflowDefinitionLink (companyId, workflowDefinitionName[$COLUMN_LENGTH:75$], workflowDefinitionVersion);

create index IX_415A7007 on WorkflowInstanceLink (groupId, companyId, classNameId, classPK);