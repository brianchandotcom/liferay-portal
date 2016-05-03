update AssetEntry set publishDate = createDate where publishDate is null;

update UserNotificationEvent set actionRequired = FALSE where actionRequired is null;