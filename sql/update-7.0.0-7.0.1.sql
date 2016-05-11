update AssetEntry set publishDate = createDate where publishDate is null;

update UserNotificationEvent set actionRequired = FALSE where actionRequired is null;

update Layout set themeId = 'userdashboard_WAR_userdashboardtheme' where themeId = 'userhorizontal_WAR_userhorizontaltheme';
update LayoutRevision set themeId = 'userdashboard_WAR_userdashboardtheme' where themeId = 'userhorizontal_WAR_userhorizontaltheme';
update LayoutSetBranch set themeId = 'userdashboard_WAR_userdashboardtheme' where themeId = 'userhorizontal_WAR_userhorizontaltheme';
update LayoutSet set themeId = 'userdashboard_WAR_userdashboardtheme' where themeId = 'userhorizontal_WAR_userhorizontaltheme';

update Layout set themeId = 'userprofile_WAR_userprofiletheme' where themeId = 'uservertical_WAR_userverticaltheme';
update LayoutRevision set themeId = 'userprofile_WAR_userprofiletheme' where themeId = 'uservertical_WAR_userverticaltheme';
update LayoutSetBranch set themeId = 'userprofile_WAR_userprofiletheme' where themeId = 'uservertical_WAR_userverticaltheme';
update LayoutSet set themeId = 'userprofile_WAR_userprofiletheme' where themeId = 'uservertical_WAR_userverticaltheme';