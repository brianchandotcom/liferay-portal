create index IX_93D5AD4E on Address (companyId);
create index IX_ABD7DAC0 on Address (companyId, classNameId);
create index IX_71CB1123 on Address (companyId, classNameId, classPK);
create index IX_923BD178 on Address (companyId, classNameId, classPK, mailing);
create index IX_9226DBB4 on Address (companyId, classNameId, classPK, primary_);
create index IX_5BC8B0D4 on Address (userId);
create index IX_381E55DA on Address (uuid_);
create index IX_8FCB620E on Address (uuid_, companyId);

create index IX_6EDB9600 on AnnouncementsDelivery (userId);
create unique index IX_BA4413D5 on AnnouncementsDelivery (userId, type_);

create index IX_A6EF0B81 on AnnouncementsEntry (classNameId, classPK);
create index IX_14F06A6B on AnnouncementsEntry (classNameId, classPK, alert);
create index IX_D49C2E66 on AnnouncementsEntry (userId);
create index IX_1AFBDE08 on AnnouncementsEntry (uuid_);
create index IX_F2949120 on AnnouncementsEntry (uuid_, companyId);

create index IX_9C7EB9F on AnnouncementsFlag (entryId);
create unique index IX_4539A99C on AnnouncementsFlag (userId, entryId, value);

create index IX_E639E2F6 on AssetCategory (groupId);
create index IX_C7F39FCA on AssetCategory (groupId, name, vocabularyId);
create index IX_852EA801 on AssetCategory (groupId, parentCategoryId, name, vocabularyId);
create index IX_87603842 on AssetCategory (groupId, parentCategoryId, vocabularyId);
create index IX_2008FACB on AssetCategory (groupId, vocabularyId);
create index IX_D61ABE08 on AssetCategory (name, vocabularyId);
create index IX_7BB1826B on AssetCategory (parentCategoryId);
create index IX_9DDD15EA on AssetCategory (parentCategoryId, name);
create unique index IX_BE4DF2BF on AssetCategory (parentCategoryId, name, vocabularyId);
create index IX_B185E980 on AssetCategory (parentCategoryId, vocabularyId);
create index IX_4D37BB00 on AssetCategory (uuid_);
create index IX_BBAF6928 on AssetCategory (uuid_, companyId);
create unique index IX_E8D019AA on AssetCategory (uuid_, groupId);
create index IX_287B1F89 on AssetCategory (vocabularyId);

create index IX_99DA856 on AssetCategoryProperty (categoryId);
create unique index IX_DBD111AA on AssetCategoryProperty (categoryId, key_);
create index IX_8654719F on AssetCategoryProperty (companyId);
create index IX_52340033 on AssetCategoryProperty (companyId, key_);

create index IX_A188F560 on AssetEntries_AssetCategories (categoryId);
create index IX_E119938A on AssetEntries_AssetCategories (entryId);

create index IX_2ED82CAD on AssetEntries_AssetTags (entryId);
create index IX_B2A61B55 on AssetEntries_AssetTags (tagId);

create unique index IX_1E9D371D on AssetEntry (classNameId, classPK);
create index IX_FC1F9C7B on AssetEntry (classUuid);
create index IX_7306C60 on AssetEntry (companyId);
create index IX_75D42FF9 on AssetEntry (expirationDate);
create index IX_1EBA6821 on AssetEntry (groupId, classUuid);
create index IX_FEC4A201 on AssetEntry (layoutUuid);
create index IX_2E4E3885 on AssetEntry (publishDate);
create index IX_9029E15A on AssetEntry (visible);

create index IX_128516C8 on AssetLink (entryId1);
create index IX_56E0AB21 on AssetLink (entryId1, entryId2);
create unique index IX_8F542794 on AssetLink (entryId1, entryId2, type_);
create index IX_14D5A20D on AssetLink (entryId1, type_);
create index IX_12851A89 on AssetLink (entryId2);
create index IX_91F132C on AssetLink (entryId2, type_);

create index IX_7C9E46BA on AssetTag (groupId);
create index IX_D63322F9 on AssetTag (groupId, name);

create index IX_DFF1F063 on AssetTagProperty (companyId);
create index IX_13805BF7 on AssetTagProperty (companyId, key_);
create index IX_3269E180 on AssetTagProperty (tagId);
create unique index IX_2C944354 on AssetTagProperty (tagId, key_);

create index IX_50702693 on AssetTagStats (classNameId);
create index IX_9464CA on AssetTagStats (tagId);
create unique index IX_56682CC4 on AssetTagStats (tagId, classNameId);

create index IX_B22D908C on AssetVocabulary (companyId);
create index IX_B6B8CA0E on AssetVocabulary (groupId);
create unique index IX_C0AAD74D on AssetVocabulary (groupId, name);
create index IX_55F58818 on AssetVocabulary (uuid_);
create index IX_C4E6FD10 on AssetVocabulary (uuid_, companyId);
create unique index IX_1B2B8792 on AssetVocabulary (uuid_, groupId);

create index IX_A73B688A on BackgroundTask (groupId, taskExecutorClassName);
create index IX_7E757D70 on BackgroundTask (groupId, taskExecutorClassName, status);
create index IX_2FCFE748 on BackgroundTask (taskExecutorClassName, status);

create index IX_72EF6041 on BlogsEntry (companyId);
create index IX_430D791F on BlogsEntry (companyId, displayDate);
create index IX_BB0C2905 on BlogsEntry (companyId, displayDate, status);
create index IX_EB2DCE27 on BlogsEntry (companyId, status);
create index IX_8CACE77B on BlogsEntry (companyId, userId);
create index IX_A5F57B61 on BlogsEntry (companyId, userId, status);
create index IX_2672F77F on BlogsEntry (displayDate, status);
create index IX_81A50303 on BlogsEntry (groupId);
create index IX_621E19D on BlogsEntry (groupId, displayDate);
create index IX_F0E73383 on BlogsEntry (groupId, displayDate, status);
create index IX_1EFD8EE9 on BlogsEntry (groupId, status);
create unique index IX_DB780A20 on BlogsEntry (groupId, urlTitle);
create index IX_FBDE0AA3 on BlogsEntry (groupId, userId, displayDate);
create index IX_DA04F689 on BlogsEntry (groupId, userId, displayDate, status);
create index IX_49E15A23 on BlogsEntry (groupId, userId, status);
create index IX_69157A4D on BlogsEntry (uuid_);
create index IX_5E8307BB on BlogsEntry (uuid_, companyId);
create unique index IX_1B1040FD on BlogsEntry (uuid_, groupId);

create index IX_90CDA39A on BlogsStatsUser (companyId, entryCount);
create index IX_43840EEB on BlogsStatsUser (groupId);
create index IX_28C78D5C on BlogsStatsUser (groupId, entryCount);
create unique index IX_82254C25 on BlogsStatsUser (groupId, userId);
create index IX_BB51F1D9 on BlogsStatsUser (userId);
create index IX_507BA031 on BlogsStatsUser (userId, lastPostDate);

create index IX_5200100C on BookmarksEntry (groupId, folderId);
create index IX_146382F2 on BookmarksEntry (groupId, folderId, status);
create index IX_416AD7D5 on BookmarksEntry (groupId, status);
create index IX_C78B61AC on BookmarksEntry (groupId, userId, folderId, status);
create index IX_9D9CF70F on BookmarksEntry (groupId, userId, status);
create index IX_E848278F on BookmarksEntry (resourceBlockId);
create index IX_B670BA39 on BookmarksEntry (uuid_);
create index IX_89BEDC4F on BookmarksEntry (uuid_, companyId);
create unique index IX_EAA02A91 on BookmarksEntry (uuid_, groupId);

create index IX_2ABA25D7 on BookmarksFolder (companyId);
create index IX_7F703619 on BookmarksFolder (groupId);
create index IX_967799C0 on BookmarksFolder (groupId, parentFolderId);
create index IX_D16018A6 on BookmarksFolder (groupId, parentFolderId, status);
create index IX_28A49BB9 on BookmarksFolder (resourceBlockId);
create index IX_451E7AE3 on BookmarksFolder (uuid_);
create index IX_54F0ED65 on BookmarksFolder (uuid_, companyId);
create unique index IX_DC2F8927 on BookmarksFolder (uuid_, groupId);

create unique index IX_E7B95510 on BrowserTracker (userId);

create index IX_D6FD9496 on CalEvent (companyId);
create index IX_12EE4898 on CalEvent (groupId);
create index IX_4FDDD2BF on CalEvent (groupId, repeating);
create index IX_FCD7C63D on CalEvent (groupId, type_);
create index IX_FD93CBFA on CalEvent (groupId, type_, repeating);
create index IX_F6006202 on CalEvent (remindBy);
create index IX_C1AD2122 on CalEvent (uuid_);
create index IX_299639C6 on CalEvent (uuid_, companyId);
create unique index IX_5CCE79C8 on CalEvent (uuid_, groupId);

create unique index IX_B27A301F on ClassName_ (value);

create index IX_38EFE3FD on Company (logoId);
create index IX_12566EC2 on Company (mx);
create index IX_35E3E7C6 on Company (system);
create unique index IX_EC00543C on Company (webId);

create index IX_B8C28C53 on Contact_ (accountId);
create index IX_791914FA on Contact_ (classNameId, classPK);
create index IX_66D496A3 on Contact_ (companyId);

create unique index IX_717B97E1 on Country (a2);
create unique index IX_717B9BA2 on Country (a3);
create index IX_25D734CD on Country (active_);
create unique index IX_19DA007B on Country (name);

create index IX_6A6C1C85 on DDLRecord (companyId);
create index IX_87A6B599 on DDLRecord (recordSetId);
create index IX_AAC564D3 on DDLRecord (recordSetId, userId);
create index IX_8BC2F891 on DDLRecord (uuid_);
create index IX_384AB6F7 on DDLRecord (uuid_, companyId);
create unique index IX_B4328F39 on DDLRecord (uuid_, groupId);

create index IX_4FA5969F on DDLRecordSet (groupId);
create unique index IX_56DAB121 on DDLRecordSet (groupId, recordSetKey);
create index IX_561E44E9 on DDLRecordSet (uuid_);
create index IX_5938C39F on DDLRecordSet (uuid_, companyId);
create unique index IX_270BA5E1 on DDLRecordSet (uuid_, groupId);

create index IX_2F4DDFE1 on DDLRecordVersion (recordId);
create index IX_762ADC7 on DDLRecordVersion (recordId, status);
create unique index IX_C79E347 on DDLRecordVersion (recordId, version);

create index IX_E3BAF436 on DDMContent (companyId);
create index IX_50BF1038 on DDMContent (groupId);
create index IX_AE4B50C2 on DDMContent (uuid_);
create index IX_3A9C0626 on DDMContent (uuid_, companyId);
create unique index IX_EB9BDE28 on DDMContent (uuid_, groupId);

create unique index IX_702D1AD5 on DDMStorageLink (classPK);
create index IX_81776090 on DDMStorageLink (structureId);
create index IX_32A18526 on DDMStorageLink (uuid_);

create index IX_31817A62 on DDMStructure (classNameId);
create index IX_4FBAC092 on DDMStructure (companyId, classNameId);
create index IX_C8419FBE on DDMStructure (groupId);
create index IX_B6ED5E50 on DDMStructure (groupId, classNameId);
create unique index IX_C8785130 on DDMStructure (groupId, classNameId, structureKey);
create index IX_43395316 on DDMStructure (groupId, parentStructureId);
create index IX_657899A8 on DDMStructure (parentStructureId);
create index IX_20FDE04C on DDMStructure (structureKey);
create index IX_E61809C8 on DDMStructure (uuid_);
create index IX_F9FB8D60 on DDMStructure (uuid_, companyId);
create unique index IX_85C7EBE2 on DDMStructure (uuid_, groupId);

create index IX_D43E4208 on DDMStructureLink (classNameId);
create unique index IX_C803899D on DDMStructureLink (classPK);
create index IX_17692B58 on DDMStructureLink (structureId);

create index IX_B6356F93 on DDMTemplate (classNameId, classPK, type_);
create index IX_32F83D16 on DDMTemplate (classPK);
create index IX_DB24DDDD on DDMTemplate (groupId);
create index IX_BD9A4A91 on DDMTemplate (groupId, classNameId);
create index IX_824ADC72 on DDMTemplate (groupId, classNameId, classPK);
create index IX_90800923 on DDMTemplate (groupId, classNameId, classPK, type_);
create index IX_F0C3449 on DDMTemplate (groupId, classNameId, classPK, type_, mode_);
create unique index IX_E6DFAB84 on DDMTemplate (groupId, classNameId, templateKey);
create index IX_B1C33EA6 on DDMTemplate (groupId, classPK);
create index IX_33BEF579 on DDMTemplate (language);
create index IX_127A35B0 on DDMTemplate (smallImageId);
create index IX_CAE41A28 on DDMTemplate (templateKey);
create index IX_C4F283C8 on DDMTemplate (type_);
create index IX_F2A243A7 on DDMTemplate (uuid_);
create index IX_D4C2C221 on DDMTemplate (uuid_, companyId);
create unique index IX_1AA75CE3 on DDMTemplate (uuid_, groupId);

create index IX_6A83A66A on DLContent (companyId, repositoryId);
create index IX_EB531760 on DLContent (companyId, repositoryId, path_);
create unique index IX_FDD1AAA8 on DLContent (companyId, repositoryId, path_, version);

create index IX_4CB1B2B4 on DLFileEntry (companyId);
create index IX_8F6C75D0 on DLFileEntry (folderId, name);
create index IX_F4AF5636 on DLFileEntry (groupId);
create index IX_93CF8193 on DLFileEntry (groupId, folderId);
create index IX_29D0AF28 on DLFileEntry (groupId, folderId, fileEntryTypeId);
create unique index IX_5391712 on DLFileEntry (groupId, folderId, name);
create unique index IX_ED5CA615 on DLFileEntry (groupId, folderId, title);
create index IX_43261870 on DLFileEntry (groupId, userId);
create index IX_D20C434D on DLFileEntry (groupId, userId, folderId);
create index IX_D9492CF6 on DLFileEntry (mimeType);
create index IX_64F0FE40 on DLFileEntry (uuid_);
create index IX_31079DE8 on DLFileEntry (uuid_, companyId);
create unique index IX_BC2E7E6A on DLFileEntry (uuid_, groupId);

create unique index IX_7332B44F on DLFileEntryMetadata (DDMStructureId, fileVersionId);
create index IX_4F40FE5E on DLFileEntryMetadata (fileEntryId);
create index IX_A44636C9 on DLFileEntryMetadata (fileEntryId, fileVersionId);
create index IX_F8E90438 on DLFileEntryMetadata (fileEntryTypeId);
create index IX_1FE9C04 on DLFileEntryMetadata (fileVersionId);
create index IX_D49AB5D1 on DLFileEntryMetadata (uuid_);

create index IX_4501FD9C on DLFileEntryType (groupId);
create unique index IX_5B6BEF5F on DLFileEntryType (groupId, fileEntryTypeKey);
create index IX_90724726 on DLFileEntryType (uuid_);
create index IX_5B03E942 on DLFileEntryType (uuid_, companyId);
create unique index IX_1399D844 on DLFileEntryType (uuid_, groupId);

create index IX_8373EC7C on DLFileEntryTypes_DDMStructures (fileEntryTypeId);
create index IX_F147CF3F on DLFileEntryTypes_DDMStructures (structureId);

create index IX_5BB6AD6C on DLFileEntryTypes_DLFolders (fileEntryTypeId);
create index IX_6E00A2EC on DLFileEntryTypes_DLFolders (folderId);

create unique index IX_38F0315 on DLFileRank (companyId, userId, fileEntryId);
create index IX_A65A1F8B on DLFileRank (fileEntryId);
create index IX_BAFB116E on DLFileRank (groupId, userId);
create index IX_4E96195B on DLFileRank (groupId, userId, active_);
create index IX_EED06670 on DLFileRank (userId);

create index IX_B0051937 on DLFileShortcut (groupId, folderId);
create index IX_348DC3B2 on DLFileShortcut (groupId, folderId, active_);
create index IX_17EE3098 on DLFileShortcut (groupId, folderId, active_, status);
create index IX_4B7247F6 on DLFileShortcut (toFileEntryId);
create index IX_4831EBE4 on DLFileShortcut (uuid_);
create index IX_29AE81C4 on DLFileShortcut (uuid_, companyId);
create unique index IX_FDB4A946 on DLFileShortcut (uuid_, groupId);

create index IX_C68DC967 on DLFileVersion (fileEntryId);
create index IX_D47BB14D on DLFileVersion (fileEntryId, status);
create unique index IX_E2815081 on DLFileVersion (fileEntryId, version);
create index IX_DFD809D3 on DLFileVersion (groupId, folderId, status);
create index IX_9BE769ED on DLFileVersion (groupId, folderId, title, version);
create index IX_FFB3395C on DLFileVersion (mimeType);
create index IX_4BFABB9A on DLFileVersion (uuid_);
create index IX_95E9E44E on DLFileVersion (uuid_, companyId);
create unique index IX_C99B2650 on DLFileVersion (uuid_, groupId);

create index IX_A74DB14C on DLFolder (companyId);
create index IX_F2EA1ACE on DLFolder (groupId);
create index IX_F78286C5 on DLFolder (groupId, mountPoint, parentFolderId, hidden_);
create index IX_C88430AB on DLFolder (groupId, mountPoint, parentFolderId, hidden_, status);
create index IX_49C37475 on DLFolder (groupId, parentFolderId);
create index IX_CE360BF6 on DLFolder (groupId, parentFolderId, hidden_, status);
create unique index IX_902FD874 on DLFolder (groupId, parentFolderId, name);
create index IX_51556082 on DLFolder (parentFolderId, name);
create index IX_EE29C715 on DLFolder (repositoryId);
create index IX_CBC408D8 on DLFolder (uuid_);
create index IX_DA448450 on DLFolder (uuid_, companyId);
create unique index IX_3CC1DED2 on DLFolder (uuid_, groupId);

create index IX_1BB072CA on EmailAddress (companyId);
create index IX_49D2DEC4 on EmailAddress (companyId, classNameId);
create index IX_551A519F on EmailAddress (companyId, classNameId, classPK);
create index IX_2A2CB130 on EmailAddress (companyId, classNameId, classPK, primary_);
create index IX_7B43CD8 on EmailAddress (userId);
create index IX_D24F3956 on EmailAddress (uuid_);
create index IX_F74AB912 on EmailAddress (uuid_, companyId);

create index IX_A8C0CBE8 on ExpandoColumn (tableId);
create unique index IX_FEFC8DA7 on ExpandoColumn (tableId, name);

create index IX_D3F5D7AE on ExpandoRow (tableId);
create unique index IX_81EFBFF5 on ExpandoRow (tableId, classPK);

create index IX_B5AE8A85 on ExpandoTable (companyId, classNameId);
create unique index IX_37562284 on ExpandoTable (companyId, classNameId, name);

create index IX_B29FEF17 on ExpandoValue (classNameId, classPK);
create index IX_F7DD0987 on ExpandoValue (columnId);
create unique index IX_9DDD21E5 on ExpandoValue (columnId, rowId_);
create index IX_9112A7A0 on ExpandoValue (rowId_);
create index IX_F0566A77 on ExpandoValue (tableId);
create index IX_1BD3F4C on ExpandoValue (tableId, classPK);
create index IX_CA9AFB7C on ExpandoValue (tableId, columnId);
create unique index IX_D27B03E7 on ExpandoValue (tableId, columnId, classPK);
create index IX_B71E92D5 on ExpandoValue (tableId, rowId_);

create index IX_ABA5CEC2 on Group_ (companyId);
create unique index IX_D0D5E397 on Group_ (companyId, classNameId, classPK);
create unique index IX_5DE0BE11 on Group_ (companyId, classNameId, liveGroupId, name);
create index IX_ABE2D54 on Group_ (companyId, classNameId, parentGroupId);
create unique index IX_5BDDB872 on Group_ (companyId, friendlyURL);
create unique index IX_BBCA55B on Group_ (companyId, liveGroupId, name);
create unique index IX_5AA68501 on Group_ (companyId, name);
create index IX_5D75499E on Group_ (companyId, parentGroupId);
create index IX_6C499099 on Group_ (companyId, parentGroupId, site);
create index IX_63A2AABD on Group_ (companyId, site);
create index IX_16218A38 on Group_ (liveGroupId);
create index IX_7B590A7A on Group_ (type_, active_);
create index IX_F981514E on Group_ (uuid_);
create index IX_26CC761A on Group_ (uuid_, companyId);
create unique index IX_754FBB1C on Group_ (uuid_, groupId);

create index IX_75267DCA on Groups_Orgs (groupId);
create index IX_6BBB7682 on Groups_Orgs (organizationId);

create index IX_84471FD2 on Groups_Roles (groupId);
create index IX_3103EF3D on Groups_Roles (roleId);

create index IX_31FB749A on Groups_UserGroups (groupId);
create index IX_3B69160F on Groups_UserGroups (userGroupId);

create index IX_6A925A4D on Image (size_);

create index IX_DFF98523 on JournalArticle (companyId);
create index IX_323DF109 on JournalArticle (companyId, status);
create index IX_3D070845 on JournalArticle (companyId, version);
create index IX_E82F322B on JournalArticle (companyId, version, status);
create index IX_9356F865 on JournalArticle (groupId);
create index IX_68C0F69C on JournalArticle (groupId, articleId);
create index IX_4D5CD982 on JournalArticle (groupId, articleId, status);
create unique index IX_85C52EEC on JournalArticle (groupId, articleId, version);
create index IX_9CE6E0FA on JournalArticle (groupId, classNameId, classPK);
create index IX_A2534AC2 on JournalArticle (groupId, classNameId, layoutUuid);
create index IX_91E78C35 on JournalArticle (groupId, classNameId, structureId);
create index IX_F43B9FF2 on JournalArticle (groupId, classNameId, templateId);
create index IX_5CD17502 on JournalArticle (groupId, folderId);
create index IX_F35391E8 on JournalArticle (groupId, folderId, status);
create index IX_3C028C1E on JournalArticle (groupId, layoutUuid);
create index IX_301D024B on JournalArticle (groupId, status);
create index IX_2E207659 on JournalArticle (groupId, structureId);
create index IX_8DEAE14E on JournalArticle (groupId, templateId);
create index IX_22882D02 on JournalArticle (groupId, urlTitle);
create index IX_D2D249E8 on JournalArticle (groupId, urlTitle, status);
create index IX_D19C1B9F on JournalArticle (groupId, userId);
create index IX_43A0F80F on JournalArticle (groupId, userId, classNameId);
create index IX_3F1EA19E on JournalArticle (layoutUuid);
create index IX_33F49D16 on JournalArticle (resourcePrimKey);
create index IX_3E2765FC on JournalArticle (resourcePrimKey, status);
create index IX_EF9B7028 on JournalArticle (smallImageId);
create index IX_8E8710D9 on JournalArticle (structureId);
create index IX_9106F6CE on JournalArticle (templateId);
create index IX_F029602F on JournalArticle (uuid_);
create index IX_71520099 on JournalArticle (uuid_, companyId);
create unique index IX_3463D95B on JournalArticle (uuid_, groupId);

create index IX_3B51BB68 on JournalArticleImage (groupId);
create index IX_158B526F on JournalArticleImage (groupId, articleId, version);
create unique index IX_103D6207 on JournalArticleImage (groupId, articleId, version, elInstanceId, elName, languageId);
create index IX_D4121315 on JournalArticleImage (tempImage);

create index IX_F8433677 on JournalArticleResource (groupId);
create unique index IX_88DF994A on JournalArticleResource (groupId, articleId);
create index IX_DCD1FAC1 on JournalArticleResource (uuid_);
create unique index IX_84AB0309 on JournalArticleResource (uuid_, groupId);

create index IX_9207CB31 on JournalContentSearch (articleId);
create index IX_6838E427 on JournalContentSearch (groupId, articleId);
create index IX_20962903 on JournalContentSearch (groupId, privateLayout);
create index IX_7CC7D73E on JournalContentSearch (groupId, privateLayout, articleId);
create index IX_B3B318DC on JournalContentSearch (groupId, privateLayout, layoutId);
create index IX_7ACC74C9 on JournalContentSearch (groupId, privateLayout, layoutId, portletId);
create unique index IX_C3AA93B8 on JournalContentSearch (groupId, privateLayout, layoutId, portletId, articleId);
create index IX_8DAF8A35 on JournalContentSearch (portletId);

create index IX_35A2DB2F on JournalFeed (groupId);
create unique index IX_65576CBC on JournalFeed (groupId, feedId);
create index IX_50C36D79 on JournalFeed (uuid_);
create index IX_CB37A10F on JournalFeed (uuid_, companyId);
create unique index IX_39031F51 on JournalFeed (uuid_, groupId);

create index IX_E6E2725D on JournalFolder (companyId);
create index IX_742DEC1F on JournalFolder (groupId);
create index IX_E988689E on JournalFolder (groupId, name);
create index IX_190483C6 on JournalFolder (groupId, parentFolderId);
create unique index IX_65026705 on JournalFolder (groupId, parentFolderId, name);
create index IX_EFD9CAC on JournalFolder (groupId, parentFolderId, status);
create index IX_63BDFA69 on JournalFolder (uuid_);
create index IX_54F89E1F on JournalFolder (uuid_, companyId);
create unique index IX_E002061 on JournalFolder (uuid_, groupId);

create index IX_C7FBC998 on Layout (companyId);
create index IX_C099D61A on Layout (groupId);
create index IX_705F5AA3 on Layout (groupId, privateLayout);
create unique index IX_BC2C4231 on Layout (groupId, privateLayout, friendlyURL);
create unique index IX_7162C27C on Layout (groupId, privateLayout, layoutId);
create index IX_6DE88B06 on Layout (groupId, privateLayout, parentLayoutId);
create index IX_8CE8C0D9 on Layout (groupId, privateLayout, sourcePrototypeLayoutUuid);
create index IX_1A1B61D2 on Layout (groupId, privateLayout, type_);
create index IX_23922F7D on Layout (iconImageId);
create index IX_B529BFD3 on Layout (layoutPrototypeUuid);
create index IX_39A18ECC on Layout (sourcePrototypeLayoutUuid);
create index IX_D0822724 on Layout (uuid_);
create index IX_2CE4BE84 on Layout (uuid_, companyId);
create unique index IX_E118C537 on Layout (uuid_, groupId, privateLayout);

create index IX_6C226433 on LayoutBranch (layoutSetBranchId);
create index IX_2C42603E on LayoutBranch (layoutSetBranchId, plid);
create index IX_A705FF94 on LayoutBranch (layoutSetBranchId, plid, master);
create unique index IX_FD57097D on LayoutBranch (layoutSetBranchId, plid, name);

create index IX_EAB317C8 on LayoutFriendlyURL (companyId);
create index IX_742EF04A on LayoutFriendlyURL (groupId);
create index IX_CA713461 on LayoutFriendlyURL (groupId, privateLayout, friendlyURL);
create unique index IX_A6FC2B28 on LayoutFriendlyURL (groupId, privateLayout, friendlyURL, languageId);
create index IX_83AE56AB on LayoutFriendlyURL (plid);
create index IX_59051329 on LayoutFriendlyURL (plid, friendlyURL);
create unique index IX_C5762E72 on LayoutFriendlyURL (plid, languageId);
create index IX_9F80D54 on LayoutFriendlyURL (uuid_);
create index IX_F4321A54 on LayoutFriendlyURL (uuid_, companyId);
create unique index IX_326525D6 on LayoutFriendlyURL (uuid_, groupId);

create index IX_30616AAA on LayoutPrototype (companyId);
create index IX_557A639F on LayoutPrototype (companyId, active_);
create index IX_CEF72136 on LayoutPrototype (uuid_);
create index IX_63ED2532 on LayoutPrototype (uuid_, companyId);

create index IX_43E8286A on LayoutRevision (head, plid);
create index IX_314B621A on LayoutRevision (layoutSetBranchId);
create index IX_A9AC086E on LayoutRevision (layoutSetBranchId, head);
create index IX_E10AC39 on LayoutRevision (layoutSetBranchId, head, plid);
create index IX_13984800 on LayoutRevision (layoutSetBranchId, layoutBranchId, plid);
create index IX_4A84AF43 on LayoutRevision (layoutSetBranchId, parentLayoutRevisionId, plid);
create index IX_B7B914E5 on LayoutRevision (layoutSetBranchId, plid);
create index IX_70DA9ECB on LayoutRevision (layoutSetBranchId, plid, status);
create index IX_7FFAE700 on LayoutRevision (layoutSetBranchId, status);
create index IX_9329C9D6 on LayoutRevision (plid);
create index IX_8EC3D2BC on LayoutRevision (plid, status);

create index IX_A40B8BEC on LayoutSet (groupId);
create unique index IX_48550691 on LayoutSet (groupId, privateLayout);
create index IX_72BBA8B7 on LayoutSet (layoutSetPrototypeUuid);

create index IX_8FF5D6EA on LayoutSetBranch (groupId);
create index IX_C4079FD3 on LayoutSetBranch (groupId, privateLayout);
create index IX_CCF0DA29 on LayoutSetBranch (groupId, privateLayout, master);
create unique index IX_5FF18552 on LayoutSetBranch (groupId, privateLayout, name);

create index IX_55F63D98 on LayoutSetPrototype (companyId);
create index IX_9178FC71 on LayoutSetPrototype (companyId, active_);
create index IX_C5D69B24 on LayoutSetPrototype (uuid_);
create index IX_D9FFCA84 on LayoutSetPrototype (uuid_, companyId);

create index IX_2932DD37 on ListType (type_);

create unique index IX_228562AD on Lock_ (className, key_);
create index IX_E3F1286B on Lock_ (expirationDate);
create index IX_13C5CD3A on Lock_ (uuid_);
create index IX_2C418EAE on Lock_ (uuid_, companyId);

create index IX_69951A25 on MBBan (banUserId);
create index IX_5C3FF12A on MBBan (groupId);
create unique index IX_8ABC4E3B on MBBan (groupId, banUserId);
create index IX_48814BBA on MBBan (userId);
create index IX_8A13C634 on MBBan (uuid_);
create index IX_4F841574 on MBBan (uuid_, companyId);
create unique index IX_2A3B68F6 on MBBan (uuid_, groupId);

create index IX_BC735DCF on MBCategory (companyId);
create index IX_E15A5DB5 on MBCategory (companyId, status);
create index IX_BB870C11 on MBCategory (groupId);
create index IX_ED292508 on MBCategory (groupId, parentCategoryId);
create index IX_C295DBEE on MBCategory (groupId, parentCategoryId, status);
create index IX_DA84A9F7 on MBCategory (groupId, status);
create index IX_C2626EDB on MBCategory (uuid_);
create index IX_13DF4E6D on MBCategory (uuid_, companyId);
create unique index IX_F7D28C2F on MBCategory (uuid_, groupId);

create index IX_79D0120B on MBDiscussion (classNameId);
create unique index IX_33A4DE38 on MBDiscussion (classNameId, classPK);
create unique index IX_B5CA2DC on MBDiscussion (threadId);
create index IX_5477D431 on MBDiscussion (uuid_);
create index IX_7E965757 on MBDiscussion (uuid_, companyId);
create unique index IX_F7AAC799 on MBDiscussion (uuid_, groupId);

create index IX_BFEB984F on MBMailingList (active_);
create unique index IX_76CE9CDD on MBMailingList (groupId, categoryId);
create index IX_4115EC7A on MBMailingList (uuid_);
create index IX_FC61676E on MBMailingList (uuid_, companyId);
create unique index IX_E858F170 on MBMailingList (uuid_, groupId);

create index IX_51A8D44D on MBMessage (classNameId, classPK);
create index IX_F6687633 on MBMessage (classNameId, classPK, status);
create index IX_B1432D30 on MBMessage (companyId);
create index IX_1AD93C16 on MBMessage (companyId, status);
create index IX_5B153FB2 on MBMessage (groupId);
create index IX_1073AB9F on MBMessage (groupId, categoryId);
create index IX_4257DB85 on MBMessage (groupId, categoryId, status);
create index IX_B674AB58 on MBMessage (groupId, categoryId, threadId);
create index IX_CBFDBF0A on MBMessage (groupId, categoryId, threadId, answer);
create index IX_385E123E on MBMessage (groupId, categoryId, threadId, status);
create index IX_ED39AC98 on MBMessage (groupId, status);
create index IX_8EB8C5EC on MBMessage (groupId, userId);
create index IX_31A400BF on MBMessage (groupId, userId, categoryId, status);
create index IX_377858D2 on MBMessage (groupId, userId, status);
create index IX_75B95071 on MBMessage (threadId);
create index IX_9D7C3B23 on MBMessage (threadId, answer);
create index IX_A7038CD7 on MBMessage (threadId, parentMessageId);
create index IX_9DC8E57 on MBMessage (threadId, status);
create index IX_7A040C32 on MBMessage (userId);
create index IX_59F9CE5C on MBMessage (userId, classNameId);
create index IX_ABEB6D07 on MBMessage (userId, classNameId, classPK);
create index IX_4A4BB4ED on MBMessage (userId, classNameId, classPK, status);
create index IX_3321F142 on MBMessage (userId, classNameId, status);
create index IX_C57B16BC on MBMessage (uuid_);
create index IX_57CA9FEC on MBMessage (uuid_, companyId);
create unique index IX_8D12316E on MBMessage (uuid_, groupId);

create index IX_A00A898F on MBStatsUser (groupId);
create unique index IX_9168E2C9 on MBStatsUser (groupId, userId);
create index IX_D33A5445 on MBStatsUser (groupId, userId, messageCount);
create index IX_847F92B5 on MBStatsUser (userId);

create index IX_41F6DC8A on MBThread (categoryId, priority);
create index IX_95C0EA45 on MBThread (groupId);
create index IX_9A2D11B2 on MBThread (groupId, categoryId);
create index IX_50F1904A on MBThread (groupId, categoryId, lastPostDate);
create index IX_485F7E98 on MBThread (groupId, categoryId, status);
create index IX_E1E7142B on MBThread (groupId, status);
create index IX_AEDD9CB5 on MBThread (lastPostDate, priority);
create index IX_CC993ECB on MBThread (rootMessageId);
create index IX_7E264A0F on MBThread (uuid_);
create index IX_F8CA2AB9 on MBThread (uuid_, companyId);
create unique index IX_3A200B7B on MBThread (uuid_, groupId);

create index IX_8CB0A24A on MBThreadFlag (threadId);
create index IX_A28004B on MBThreadFlag (userId);
create unique index IX_33781904 on MBThreadFlag (userId, threadId);
create index IX_F36BBB83 on MBThreadFlag (uuid_);
create index IX_DCE308C5 on MBThreadFlag (uuid_, companyId);
create unique index IX_FEB0FC87 on MBThreadFlag (uuid_, groupId);

create index IX_FD90786C on MDRAction (ruleGroupInstanceId);
create index IX_77BB5E9D on MDRAction (uuid_);
create index IX_C58A516B on MDRAction (uuid_, companyId);
create unique index IX_75BE36AD on MDRAction (uuid_, groupId);

create index IX_4F4293F1 on MDRRule (ruleGroupId);
create index IX_EA63B9D7 on MDRRule (uuid_);
create index IX_7DEA8DF1 on MDRRule (uuid_, companyId);
create unique index IX_F3EFDCB3 on MDRRule (uuid_, groupId);

create index IX_5849891C on MDRRuleGroup (groupId);
create index IX_7F26B2A6 on MDRRuleGroup (uuid_);
create index IX_CC14DC2 on MDRRuleGroup (uuid_, companyId);
create unique index IX_46665CC4 on MDRRuleGroup (uuid_, groupId);

create index IX_C95A08D8 on MDRRuleGroupInstance (classNameId, classPK);
create unique index IX_808A0036 on MDRRuleGroupInstance (classNameId, classPK, ruleGroupId);
create index IX_AFF28547 on MDRRuleGroupInstance (groupId);
create index IX_22DAB85C on MDRRuleGroupInstance (groupId, classNameId, classPK);
create index IX_BF3E642B on MDRRuleGroupInstance (ruleGroupId);
create index IX_B6A6BD91 on MDRRuleGroupInstance (uuid_);
create index IX_25C9D1F7 on MDRRuleGroupInstance (uuid_, companyId);
create unique index IX_9CBC6A39 on MDRRuleGroupInstance (uuid_, groupId);

create index IX_8A1CC4B on MembershipRequest (groupId);
create index IX_C28C72EC on MembershipRequest (groupId, statusId);
create index IX_35AA8FA6 on MembershipRequest (groupId, userId, statusId);
create index IX_66D70879 on MembershipRequest (userId);

create index IX_4A527DD3 on OrgGroupRole (groupId);
create index IX_AB044D1C on OrgGroupRole (roleId);

create index IX_6AF0D434 on OrgLabor (organizationId);

create index IX_834BCEB6 on Organization_ (companyId);
create unique index IX_E301BDF5 on Organization_ (companyId, name);
create index IX_418E4522 on Organization_ (companyId, parentOrganizationId);
create index IX_396D6B42 on Organization_ (uuid_);
create index IX_A9D85BA6 on Organization_ (uuid_, companyId);

create index IX_8FEE65F5 on PasswordPolicy (companyId);
create index IX_2C1142E on PasswordPolicy (companyId, defaultPolicy);
create unique index IX_3FBFA9F4 on PasswordPolicy (companyId, name);
create index IX_51437A01 on PasswordPolicy (uuid_);
create index IX_E4D7EF87 on PasswordPolicy (uuid_, companyId);

create unique index IX_C3A17327 on PasswordPolicyRel (classNameId, classPK);
create index IX_CD25266E on PasswordPolicyRel (passwordPolicyId);

create index IX_326F75BD on PasswordTracker (userId);

create index IX_9F704A14 on Phone (companyId);
create index IX_A2E4AFBA on Phone (companyId, classNameId);
create index IX_9A53569 on Phone (companyId, classNameId, classPK);
create index IX_812CE07A on Phone (companyId, classNameId, classPK, primary_);
create index IX_F202B9CE on Phone (userId);
create index IX_EA6245A0 on Phone (uuid_);
create index IX_B271FA88 on Phone (uuid_, companyId);

create index IX_B9746445 on PluginSetting (companyId);
create unique index IX_7171B2E8 on PluginSetting (companyId, pluginId, pluginType);

create index IX_EC370F10 on PollsChoice (questionId);
create unique index IX_D76DD2CF on PollsChoice (questionId, name);
create index IX_6660B399 on PollsChoice (uuid_);
create index IX_8AE746EF on PollsChoice (uuid_, companyId);
create unique index IX_C222BD31 on PollsChoice (uuid_, groupId);

create index IX_9FF342EA on PollsQuestion (groupId);
create index IX_51F087F4 on PollsQuestion (uuid_);
create index IX_F910BBB4 on PollsQuestion (uuid_, companyId);
create unique index IX_F3C9F36 on PollsQuestion (uuid_, groupId);

create index IX_D5DF7B54 on PollsVote (choiceId);
create index IX_12112599 on PollsVote (questionId);
create unique index IX_1BBFD4D3 on PollsVote (questionId, userId);
create index IX_FD32EB70 on PollsVote (uuid_);
create index IX_7D8E92B8 on PollsVote (uuid_, companyId);
create unique index IX_A88C673A on PollsVote (uuid_, groupId);

create index IX_D1F795F1 on PortalPreferences (ownerId, ownerType);

create index IX_80CC9508 on Portlet (companyId);
create unique index IX_12B5E51D on Portlet (companyId, portletId);

create index IX_96BDD537 on PortletItem (groupId, classNameId);
create index IX_D699243F on PortletItem (groupId, name, portletId, classNameId);
create index IX_2C61314E on PortletItem (groupId, portletId);
create index IX_E922D6C0 on PortletItem (groupId, portletId, classNameId);
create index IX_8E71167F on PortletItem (groupId, portletId, classNameId, name);
create index IX_33B8CE8D on PortletItem (groupId, portletId, name);

create index IX_E4F13E6E on PortletPreferences (ownerId, ownerType, plid);
create unique index IX_C7057FF7 on PortletPreferences (ownerId, ownerType, plid, portletId);
create index IX_C9A3FCE2 on PortletPreferences (ownerId, ownerType, portletId);
create index IX_D5EDA3A1 on PortletPreferences (ownerType, plid, portletId);
create index IX_A3B2A80C on PortletPreferences (ownerType, portletId);
create index IX_F15C1C4F on PortletPreferences (plid);
create index IX_D340DB76 on PortletPreferences (plid, portletId);
create index IX_8E6DA3A1 on PortletPreferences (portletId);

create index IX_16184D57 on RatingsEntry (classNameId, classPK);
create index IX_A1A8CB8B on RatingsEntry (classNameId, classPK, score);
create unique index IX_B47E3C11 on RatingsEntry (userId, classNameId, classPK);

create unique index IX_A6E99284 on RatingsStats (classNameId, classPK);

create index IX_2D9A426F on Region (active_);
create index IX_16D87CA7 on Region (countryId);
create index IX_11FB3E42 on Region (countryId, active_);
create unique index IX_A2635F5C on Region (countryId, regionCode);

create unique index IX_8BD6BCA7 on Release_ (servletContextName);

create index IX_5253B1FA on Repository (groupId);
create unique index IX_60C8634C on Repository (groupId, name, portletId);
create index IX_74C17B04 on Repository (uuid_);
create index IX_F543EA4 on Repository (uuid_, companyId);
create unique index IX_11641E26 on Repository (uuid_, groupId);

create index IX_B7034B27 on RepositoryEntry (repositoryId);
create unique index IX_9BDCF489 on RepositoryEntry (repositoryId, mappedId);
create index IX_B9B1506 on RepositoryEntry (uuid_);
create index IX_D3B9AF62 on RepositoryEntry (uuid_, companyId);
create unique index IX_354AA664 on RepositoryEntry (uuid_, groupId);

create index IX_81F2DB09 on ResourceAction (name);
create unique index IX_EDB9986E on ResourceAction (name, actionId);

create index IX_DA30B086 on ResourceBlock (companyId, groupId, name);
create unique index IX_AEEA209C on ResourceBlock (companyId, groupId, name, permissionsHash);
create index IX_2D4CC782 on ResourceBlock (companyId, name);

create index IX_4AB3756 on ResourceBlockPermission (resourceBlockId);
create unique index IX_D63D20BB on ResourceBlockPermission (resourceBlockId, roleId);
create index IX_20A2E3D9 on ResourceBlockPermission (roleId);

create index IX_60B99860 on ResourcePermission (companyId, name, scope);
create index IX_2200AA69 on ResourcePermission (companyId, name, scope, primKey);
create unique index IX_8D83D0CE on ResourcePermission (companyId, name, scope, primKey, roleId);
create index IX_26284944 on ResourcePermission (companyId, primKey);
create index IX_A37A0588 on ResourcePermission (roleId);
create index IX_F4555981 on ResourcePermission (scope);

create unique index IX_BA497163 on ResourceTypePermission (companyId, groupId, name, roleId);
create index IX_7D81F66F on ResourceTypePermission (companyId, name, roleId);
create index IX_A82690E2 on ResourceTypePermission (roleId);

create index IX_449A10B9 on Role_ (companyId);
create unique index IX_A88E424E on Role_ (companyId, classNameId, classPK);
create unique index IX_EBC931B8 on Role_ (companyId, name);
create index IX_F436EC8E on Role_ (name);
create index IX_5EB4E2FB on Role_ (subtype);
create index IX_F92B66E6 on Role_ (type_);
create index IX_CBE204 on Role_ (type_, subtype);
create index IX_26DB26C5 on Role_ (uuid_);
create index IX_B9FF6043 on Role_ (uuid_, companyId);

create index IX_3BB93ECA on SCFrameworkVersi_SCProductVers (frameworkVersionId);
create index IX_E8D33FF9 on SCFrameworkVersi_SCProductVers (productVersionId);

create index IX_C98C0D78 on SCFrameworkVersion (companyId);
create index IX_272991FA on SCFrameworkVersion (groupId);
create index IX_6E1764F on SCFrameworkVersion (groupId, active_);

create index IX_1C841592 on SCLicense (active_);
create index IX_5327BB79 on SCLicense (active_, recommended);

create index IX_27006638 on SCLicenses_SCProductEntries (licenseId);
create index IX_D7710A66 on SCLicenses_SCProductEntries (productEntryId);

create index IX_5D25244F on SCProductEntry (companyId);
create index IX_72F87291 on SCProductEntry (groupId);
create index IX_98E6A9CB on SCProductEntry (groupId, userId);
create index IX_7311E812 on SCProductEntry (repoGroupId, repoArtifactId);

create index IX_AE8224CC on SCProductScreenshot (fullImageId);
create index IX_467956FD on SCProductScreenshot (productEntryId);
create index IX_DA913A55 on SCProductScreenshot (productEntryId, priority);
create index IX_6C572DAC on SCProductScreenshot (thumbnailId);

create index IX_7020130F on SCProductVersion (directDownloadURL);
create index IX_8377A211 on SCProductVersion (productEntryId);

create index IX_7338606F on ServiceComponent (buildNamespace);
create unique index IX_4F0315B8 on ServiceComponent (buildNamespace, buildNumber);

create index IX_DA5F4359 on Shard (classNameId, classPK);
create index IX_941BA8C3 on Shard (name);

create index IX_C28B41DC on ShoppingCart (groupId);
create unique index IX_FC46FE16 on ShoppingCart (groupId, userId);
create index IX_54101CC8 on ShoppingCart (userId);

create index IX_5F615D3E on ShoppingCategory (groupId);
create index IX_1E6464F5 on ShoppingCategory (groupId, parentCategoryId);

create unique index IX_DC60CFAE on ShoppingCoupon (code_);
create index IX_3251AF16 on ShoppingCoupon (groupId);

create unique index IX_1C717CA6 on ShoppingItem (companyId, sku);
create index IX_FEFE7D76 on ShoppingItem (groupId, categoryId);
create index IX_903DC750 on ShoppingItem (largeImageId);
create index IX_D217AB30 on ShoppingItem (mediumImageId);
create index IX_FF203304 on ShoppingItem (smallImageId);

create index IX_6D5F9B87 on ShoppingItemField (itemId);

create index IX_EA6FD516 on ShoppingItemPrice (itemId);

create index IX_1D15553E on ShoppingOrder (groupId);
create index IX_119B5630 on ShoppingOrder (groupId, userId, ppPaymentStatus);
create unique index IX_D7D6E87A on ShoppingOrder (number_);
create index IX_F474FD89 on ShoppingOrder (ppTxnId);

create index IX_B5F82C7A on ShoppingOrderItem (orderId);

create index IX_F542E9BC on SocialActivity (activitySetId);
create index IX_82E39A0C on SocialActivity (classNameId);
create index IX_A853C757 on SocialActivity (classNameId, classPK);
create index IX_D0E9029E on SocialActivity (classNameId, classPK, type_);
create index IX_64B1BC66 on SocialActivity (companyId);
create index IX_2A2468 on SocialActivity (groupId);
create index IX_FB604DC7 on SocialActivity (groupId, userId, classNameId, classPK, type_, receiverUserId);
create unique index IX_8F32DEC9 on SocialActivity (groupId, userId, createDate, classNameId, classPK, type_, receiverUserId);
create index IX_1271F25F on SocialActivity (mirrorActivityId);
create index IX_1F00C374 on SocialActivity (mirrorActivityId, classNameId, classPK);
create index IX_121CA3CB on SocialActivity (receiverUserId);
create index IX_3504B8BC on SocialActivity (userId);

create index IX_E14B1F1 on SocialActivityAchievement (groupId);
create index IX_83E16F2F on SocialActivityAchievement (groupId, firstInGroup);
create index IX_8F6408F0 on SocialActivityAchievement (groupId, name);
create index IX_C8FD892B on SocialActivityAchievement (groupId, userId);
create index IX_AABC18E9 on SocialActivityAchievement (groupId, userId, firstInGroup);
create unique index IX_D4390CAA on SocialActivityAchievement (groupId, userId, name);

create index IX_A4B9A23B on SocialActivityCounter (classNameId, classPK);
create index IX_D6666704 on SocialActivityCounter (groupId);
create unique index IX_1B7E3B67 on SocialActivityCounter (groupId, classNameId, classPK, name, ownerType, endPeriod);
create unique index IX_374B35AE on SocialActivityCounter (groupId, classNameId, classPK, name, ownerType, startPeriod);
create index IX_926CDD04 on SocialActivityCounter (groupId, classNameId, classPK, ownerType);

create index IX_B15863FA on SocialActivityLimit (classNameId, classPK);
create index IX_18D4BAE5 on SocialActivityLimit (groupId);
create unique index IX_F1C1A617 on SocialActivityLimit (groupId, userId, classNameId, classPK, activityType, activityCounterName);
create index IX_6F9EDE9F on SocialActivityLimit (userId);

create index IX_4460FA14 on SocialActivitySet (classNameId, classPK, type_);
create index IX_9E13F2DE on SocialActivitySet (groupId);
create index IX_9BE30DDF on SocialActivitySet (groupId, userId, classNameId, type_);
create index IX_F71071BD on SocialActivitySet (groupId, userId, type_);
create index IX_62AC101A on SocialActivitySet (userId, classNameId, classPK, type_);

create index IX_8BE5F230 on SocialActivitySetting (groupId);
create index IX_384788CD on SocialActivitySetting (groupId, activityType);
create index IX_9D22151E on SocialActivitySetting (groupId, classNameId);
create index IX_1E9CF33B on SocialActivitySetting (groupId, classNameId, activityType);
create index IX_D984AABA on SocialActivitySetting (groupId, classNameId, activityType, name);

create index IX_61171E99 on SocialRelation (companyId);
create index IX_95135D1C on SocialRelation (companyId, type_);
create index IX_C31A64C6 on SocialRelation (type_);
create index IX_5A40CDCC on SocialRelation (userId1);
create index IX_4B52BE89 on SocialRelation (userId1, type_);
create index IX_B5C9C690 on SocialRelation (userId1, userId2);
create unique index IX_12A92145 on SocialRelation (userId1, userId2, type_);
create index IX_5A40D18D on SocialRelation (userId2);
create index IX_3F9C2FA8 on SocialRelation (userId2, type_);
create index IX_F0CA24A5 on SocialRelation (uuid_);
create index IX_5B30F663 on SocialRelation (uuid_, companyId);

create index IX_D3425487 on SocialRequest (classNameId, classPK, type_, receiverUserId, status);
create index IX_A90FE5A0 on SocialRequest (companyId);
create index IX_32292ED1 on SocialRequest (receiverUserId);
create index IX_D9380CB7 on SocialRequest (receiverUserId, status);
create index IX_80F7A9C2 on SocialRequest (userId);
create unique index IX_36A90CA7 on SocialRequest (userId, classNameId, classPK, type_, receiverUserId);
create index IX_CC86A444 on SocialRequest (userId, classNameId, classPK, type_, status);
create index IX_AB5906A8 on SocialRequest (userId, status);
create index IX_49D5872C on SocialRequest (uuid_);
create index IX_8D42897C on SocialRequest (uuid_, companyId);
create unique index IX_4F973EFE on SocialRequest (uuid_, groupId);

create index IX_786D171A on Subscription (companyId, classNameId, classPK);
create unique index IX_2E1A92D4 on Subscription (companyId, userId, classNameId, classPK);
create index IX_54243AFD on Subscription (userId);
create index IX_E8F34171 on Subscription (userId, classNameId);

create index IX_72D73D39 on SystemEvent (groupId);
create index IX_7A2F0ECE on SystemEvent (groupId, classNameId, classPK);
create index IX_FFCBB747 on SystemEvent (groupId, classNameId, classPK, type_);

create index IX_AE6E9907 on Team (groupId);
create unique index IX_143DC786 on Team (groupId, name);

create index IX_B2468446 on Ticket (key_);

create unique index IX_B35F73D5 on TrashEntry (classNameId, classPK);
create index IX_2674F2A8 on TrashEntry (companyId);
create index IX_526A032A on TrashEntry (groupId);
create index IX_6CAAE2E8 on TrashEntry (groupId, createDate);

create index IX_630A643B on TrashVersion (classNameId, classPK);
create index IX_55D44577 on TrashVersion (entryId);

create index IX_524FEFCE on UserGroup (companyId);
create unique index IX_23EAD0D on UserGroup (companyId, name);
create index IX_69771487 on UserGroup (companyId, parentUserGroupId);
create index IX_5F1DD85A on UserGroup (uuid_);
create index IX_72394F8E on UserGroup (uuid_, companyId);

create index IX_CCBE4063 on UserGroupGroupRole (groupId);
create index IX_CAB0CCC8 on UserGroupGroupRole (groupId, roleId);
create index IX_1CDF88C on UserGroupGroupRole (roleId);
create index IX_DCDED558 on UserGroupGroupRole (userGroupId);
create index IX_73C52252 on UserGroupGroupRole (userGroupId, groupId);

create index IX_1B988D7A on UserGroupRole (groupId);
create index IX_871412DF on UserGroupRole (groupId, roleId);
create index IX_887A2C95 on UserGroupRole (roleId);
create index IX_887BE56A on UserGroupRole (userId);
create index IX_4D040680 on UserGroupRole (userId, groupId);

create index IX_31FB0B08 on UserGroups_Teams (teamId);
create index IX_7F187E63 on UserGroups_Teams (userGroupId);

create unique index IX_41A32E0D on UserIdMapper (type_, externalUserId);
create index IX_E60EA987 on UserIdMapper (userId);
create unique index IX_D1C44A6E on UserIdMapper (userId, type_);

create index IX_C648700A on UserNotificationDelivery (userId);
create unique index IX_926703D1 on UserNotificationDelivery (userId, classNameId, type_);

create index IX_3E5D78C4 on UserNotificationEvent (userId);
create index IX_3DBB361A on UserNotificationEvent (userId, archived);
create index IX_24F1BF0 on UserNotificationEvent (userId, delivered);
create index IX_ECD8CFEA on UserNotificationEvent (uuid_);
create index IX_A6BAFDFE on UserNotificationEvent (uuid_, companyId);

create index IX_29BA1CF5 on UserTracker (companyId);
create index IX_46B0AE8E on UserTracker (sessionId);
create index IX_E4EFBA8D on UserTracker (userId);

create index IX_14D8BCC0 on UserTrackerPath (userTrackerId);

create index IX_3A1E834E on User_ (companyId);
create index IX_740C4D0C on User_ (companyId, createDate);
create index IX_BCFDA257 on User_ (companyId, createDate, modifiedDate);
create index IX_6EF03E4E on User_ (companyId, defaultUser);
create unique index IX_615E9F7A on User_ (companyId, emailAddress);
create index IX_1D731F03 on User_ (companyId, facebookId);
create index IX_EE8ABD19 on User_ (companyId, modifiedDate);
create index IX_89509087 on User_ (companyId, openId);
create unique index IX_C5806019 on User_ (companyId, screenName);
create index IX_F6039434 on User_ (companyId, status);
create unique index IX_9782AD88 on User_ (companyId, userId);
create unique index IX_5ADBE171 on User_ (contactId);
create index IX_762F63C6 on User_ (emailAddress);
create index IX_A18034A4 on User_ (portraitId);
create index IX_E0422BDA on User_ (uuid_);
create index IX_405CC0E on User_ (uuid_, companyId);

create index IX_C4F9E699 on Users_Groups (groupId);
create index IX_F10B6C6B on Users_Groups (userId);

create index IX_7EF4EC0E on Users_Orgs (organizationId);
create index IX_FB646CA6 on Users_Orgs (userId);

create index IX_C19E5F31 on Users_Roles (roleId);
create index IX_C1A01806 on Users_Roles (userId);

create index IX_4D06AD51 on Users_Teams (teamId);
create index IX_A098EFBF on Users_Teams (userId);

create index IX_66FF2503 on Users_UserGroups (userGroupId);
create index IX_BE8102D6 on Users_UserGroups (userId);

create unique index IX_A083D394 on VirtualHost (companyId, layoutSetId);
create unique index IX_431A3960 on VirtualHost (hostname);

create unique index IX_97DFA146 on WebDAVProps (classNameId, classPK);

create index IX_96F07007 on Website (companyId);
create index IX_4F0F0CA7 on Website (companyId, classNameId);
create index IX_F960131C on Website (companyId, classNameId, classPK);
create index IX_1AA07A6D on Website (companyId, classNameId, classPK, primary_);
create index IX_F75690BB on Website (userId);
create index IX_76F15D13 on Website (uuid_);
create index IX_712BCD35 on Website (uuid_, companyId);

create index IX_5D6FE3F0 on WikiNode (companyId);
create index IX_B54332D6 on WikiNode (companyId, status);
create index IX_B480A672 on WikiNode (groupId);
create unique index IX_920CD8B1 on WikiNode (groupId, name);
create index IX_23325358 on WikiNode (groupId, status);
create index IX_6C112D7C on WikiNode (uuid_);
create index IX_E0E6D12C on WikiNode (uuid_, companyId);
create unique index IX_7609B2AE on WikiNode (uuid_, groupId);

create index IX_A2001730 on WikiPage (format);
create index IX_16E99B0A on WikiPage (groupId, nodeId, head);
create index IX_BA72B89A on WikiPage (groupId, nodeId, head, parentTitle, status);
create index IX_E0092FF0 on WikiPage (groupId, nodeId, head, status);
create index IX_941E429C on WikiPage (groupId, nodeId, status);
create index IX_5FF21CE6 on WikiPage (groupId, nodeId, title, head);
create index IX_CAA451D6 on WikiPage (groupId, userId, nodeId, status);
create index IX_C8A9C476 on WikiPage (nodeId);
create index IX_E7F635CA on WikiPage (nodeId, head);
create index IX_65E84AF4 on WikiPage (nodeId, head, parentTitle);
create index IX_9F7655DA on WikiPage (nodeId, head, parentTitle, status);
create index IX_432F0AB0 on WikiPage (nodeId, head, status);
create index IX_46EEF3C8 on WikiPage (nodeId, parentTitle);
create index IX_1ECC7656 on WikiPage (nodeId, redirectTitle);
create index IX_546F2D5C on WikiPage (nodeId, status);
create index IX_997EEDD2 on WikiPage (nodeId, title);
create index IX_E745EA26 on WikiPage (nodeId, title, head);
create index IX_BEA33AB8 on WikiPage (nodeId, title, status);
create unique index IX_3D4AF476 on WikiPage (nodeId, title, version);
create index IX_B771D67 on WikiPage (resourcePrimKey, nodeId);
create index IX_E1F55FB on WikiPage (resourcePrimKey, nodeId, head);
create index IX_94D1054D on WikiPage (resourcePrimKey, nodeId, status);
create unique index IX_2CD67C81 on WikiPage (resourcePrimKey, nodeId, version);
create index IX_FBBE7C96 on WikiPage (userId, nodeId, status);
create index IX_9C0E478F on WikiPage (uuid_);
create index IX_5DC4BD39 on WikiPage (uuid_, companyId);
create unique index IX_899D3DFB on WikiPage (uuid_, groupId);

create unique index IX_21277664 on WikiPageResource (nodeId, title);
create index IX_BE898221 on WikiPageResource (uuid_);

create index IX_A8B0D276 on WorkflowDefinitionLink (companyId);
create index IX_A4DB1F0F on WorkflowDefinitionLink (companyId, workflowDefinitionName, workflowDefinitionVersion);
create index IX_B6EE8C9E on WorkflowDefinitionLink (groupId, companyId, classNameId);
create index IX_1E5B9905 on WorkflowDefinitionLink (groupId, companyId, classNameId, classPK);
create index IX_705B40EE on WorkflowDefinitionLink (groupId, companyId, classNameId, classPK, typePK);

create index IX_415A7007 on WorkflowInstanceLink (groupId, companyId, classNameId, classPK);