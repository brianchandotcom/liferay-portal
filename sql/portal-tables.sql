create table Account_ (
	mvccVersion LONG default 0 not null,
	accountId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentAccountId LONG,
	name VARCHAR(75) null,
	legalName VARCHAR(75) null,
	legalId VARCHAR(75) null,
	legalType VARCHAR(75) null,
	sicCode VARCHAR(75) null,
	tickerSymbol VARCHAR(75) null,
	industry VARCHAR(75) null,
	type_ VARCHAR(75) null,
	size_ VARCHAR(75) null
);

create table Address (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	addressId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	street1 VARCHAR(75) null,
	street2 VARCHAR(75) null,
	street3 VARCHAR(75) null,
	city VARCHAR(75) null,
	zip VARCHAR(75) null,
	regionId LONG,
	countryId LONG,
	typeId LONG,
	mailing BOOLEAN,
	primary_ BOOLEAN
);

create table AnnouncementsDelivery (
	mvccVersion LONG default 0 not null,
	deliveryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	type_ VARCHAR(75) null,
	email BOOLEAN,
	sms BOOLEAN,
	website BOOLEAN
);

create table AnnouncementsEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	entryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	title VARCHAR(75) null,
	content TEXT null,
	url STRING null,
	type_ VARCHAR(75) null,
	displayDate DATE null,
	expirationDate DATE null,
	priority INTEGER,
	alert BOOLEAN
);

create table AnnouncementsFlag (
	mvccVersion LONG default 0 not null,
	flagId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	entryId LONG,
	value INTEGER
);

create table AssetCategory (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	categoryId LONG not null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCategoryId LONG,
	treePath STRING null,
	name VARCHAR(255) null,
	title STRING null,
	description STRING null,
	vocabularyId LONG,
	lastPublishDate DATE null,
	primary key (categoryId, ctCollectionId)
);

create table AssetEntries_AssetCategories (
	companyId LONG not null,
	categoryId LONG not null,
	entryId LONG not null,
	ctCollectionId LONG default 0 not null,
	ctChangeType BOOLEAN,
	primary key (categoryId, entryId, ctCollectionId)
);

create table AssetEntries_AssetTags (
	companyId LONG not null,
	entryId LONG not null,
	tagId LONG not null,
	ctCollectionId LONG default 0 not null,
	ctChangeType BOOLEAN,
	primary key (entryId, tagId, ctCollectionId)
);

create table AssetEntry (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	entryId LONG not null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	classUuid VARCHAR(75) null,
	classTypeId LONG,
	listable BOOLEAN,
	visible BOOLEAN,
	startDate DATE null,
	endDate DATE null,
	publishDate DATE null,
	expirationDate DATE null,
	mimeType VARCHAR(75) null,
	title STRING null,
	description TEXT null,
	summary TEXT null,
	url STRING null,
	layoutUuid VARCHAR(75) null,
	height INTEGER,
	width INTEGER,
	priority DOUBLE,
	primary key (entryId, ctCollectionId)
);

create table AssetLink (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	linkId LONG not null,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	entryId1 LONG,
	entryId2 LONG,
	type_ INTEGER,
	weight INTEGER,
	primary key (linkId, ctCollectionId)
);

create table AssetTag (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	tagId LONG not null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	assetCount INTEGER,
	lastPublishDate DATE null,
	primary key (tagId, ctCollectionId)
);

create table AssetVocabulary (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	vocabularyId LONG not null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	title STRING null,
	description STRING null,
	settings_ STRING null,
	lastPublishDate DATE null,
	primary key (vocabularyId, ctCollectionId)
);

create table BrowserTracker (
	mvccVersion LONG default 0 not null,
	browserTrackerId LONG not null primary key,
	companyId LONG,
	userId LONG,
	browserKey LONG
);

create table ClassName_ (
	mvccVersion LONG default 0 not null,
	classNameId LONG not null primary key,
	value VARCHAR(200) null
);

create table Company (
	mvccVersion LONG default 0 not null,
	companyId LONG not null primary key,
	accountId LONG,
	webId VARCHAR(75) null,
	mx VARCHAR(200) null,
	homeURL STRING null,
	logoId LONG,
	system_ BOOLEAN,
	maxUsers INTEGER,
	active_ BOOLEAN
);

create table CompanyInfo (
	mvccVersion LONG default 0 not null,
	companyInfoId LONG not null primary key,
	companyId LONG,
	key_ TEXT null
);

create table Contact_ (
	mvccVersion LONG default 0 not null,
	contactId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	accountId LONG,
	parentContactId LONG,
	emailAddress VARCHAR(254) null,
	firstName VARCHAR(75) null,
	middleName VARCHAR(75) null,
	lastName VARCHAR(75) null,
	prefixId LONG,
	suffixId LONG,
	male BOOLEAN,
	birthday DATE null,
	smsSn VARCHAR(75) null,
	facebookSn VARCHAR(75) null,
	jabberSn VARCHAR(75) null,
	skypeSn VARCHAR(75) null,
	twitterSn VARCHAR(75) null,
	employeeStatusId VARCHAR(75) null,
	employeeNumber VARCHAR(75) null,
	jobTitle VARCHAR(100) null,
	jobClass VARCHAR(75) null,
	hoursOfOperation VARCHAR(75) null
);

create table Counter (
	name VARCHAR(150) not null primary key,
	currentId LONG
);

create table Country (
	mvccVersion LONG default 0 not null,
	countryId LONG not null primary key,
	name VARCHAR(75) null,
	a2 VARCHAR(75) null,
	a3 VARCHAR(75) null,
	number_ VARCHAR(75) null,
	idd_ VARCHAR(75) null,
	zipRequired BOOLEAN,
	active_ BOOLEAN
);

create table DLFileEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	fileEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	repositoryId LONG,
	folderId LONG,
	treePath STRING null,
	name VARCHAR(255) null,
	fileName VARCHAR(255) null,
	extension VARCHAR(75) null,
	mimeType VARCHAR(75) null,
	title VARCHAR(255) null,
	description STRING null,
	extraSettings TEXT null,
	fileEntryTypeId LONG,
	version VARCHAR(75) null,
	size_ LONG,
	smallImageId LONG,
	largeImageId LONG,
	custom1ImageId LONG,
	custom2ImageId LONG,
	manualCheckInRequired BOOLEAN,
	lastPublishDate DATE null
);

create table DLFileEntryMetadata (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	fileEntryMetadataId LONG not null primary key,
	companyId LONG,
	DDMStorageId LONG,
	DDMStructureId LONG,
	fileEntryId LONG,
	fileVersionId LONG
);

create table DLFileEntryType (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	fileEntryTypeId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	fileEntryTypeKey VARCHAR(75) null,
	name STRING null,
	description STRING null,
	lastPublishDate DATE null
);

create table DLFileEntryTypes_DLFolders (
	companyId LONG not null,
	fileEntryTypeId LONG not null,
	folderId LONG not null,
	primary key (fileEntryTypeId, folderId)
);

create table DLFileShortcut (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	fileShortcutId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	repositoryId LONG,
	folderId LONG,
	toFileEntryId LONG,
	treePath STRING null,
	active_ BOOLEAN,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table DLFileVersion (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	fileVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	repositoryId LONG,
	folderId LONG,
	fileEntryId LONG,
	treePath STRING null,
	fileName VARCHAR(255) null,
	extension VARCHAR(75) null,
	mimeType VARCHAR(75) null,
	title VARCHAR(255) null,
	description STRING null,
	changeLog VARCHAR(75) null,
	extraSettings TEXT null,
	fileEntryTypeId LONG,
	version VARCHAR(75) null,
	size_ LONG,
	checksum VARCHAR(75) null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table DLFolder (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	folderId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	repositoryId LONG,
	mountPoint BOOLEAN,
	parentFolderId LONG,
	treePath STRING null,
	name VARCHAR(255) null,
	description STRING null,
	lastPostDate DATE null,
	defaultFileEntryTypeId LONG,
	hidden_ BOOLEAN,
	restrictionType INTEGER,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table EmailAddress (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	emailAddressId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	address VARCHAR(254) null,
	typeId LONG,
	primary_ BOOLEAN
);

create table ExpandoColumn (
	columnId LONG not null primary key,
	companyId LONG,
	tableId LONG,
	name VARCHAR(75) null,
	type_ INTEGER,
	defaultData TEXT null,
	typeSettings TEXT null
);

create table ExpandoRow (
	rowId_ LONG not null primary key,
	companyId LONG,
	modifiedDate DATE null,
	tableId LONG,
	classPK LONG
);

create table ExpandoTable (
	tableId LONG not null primary key,
	companyId LONG,
	classNameId LONG,
	name VARCHAR(75) null
);

create table ExpandoValue (
	valueId LONG not null primary key,
	companyId LONG,
	tableId LONG,
	columnId LONG,
	rowId_ LONG,
	classNameId LONG,
	classPK LONG,
	data_ TEXT null
);

create table ExportImportConfiguration (
	mvccVersion LONG default 0 not null,
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

create table Group_ (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	groupId LONG not null primary key,
	companyId LONG,
	creatorUserId LONG,
	classNameId LONG,
	classPK LONG,
	parentGroupId LONG,
	liveGroupId LONG,
	treePath STRING null,
	groupKey VARCHAR(150) null,
	name STRING null,
	description STRING null,
	type_ INTEGER,
	typeSettings TEXT null,
	manualMembership BOOLEAN,
	membershipRestriction INTEGER,
	friendlyURL VARCHAR(255) null,
	site BOOLEAN,
	remoteStagingGroupCount INTEGER,
	inheritContent BOOLEAN,
	active_ BOOLEAN
);

create table Groups_Orgs (
	companyId LONG not null,
	groupId LONG not null,
	organizationId LONG not null,
	primary key (groupId, organizationId)
);

create table Groups_Roles (
	companyId LONG not null,
	groupId LONG not null,
	roleId LONG not null,
	primary key (groupId, roleId)
);

create table Groups_UserGroups (
	companyId LONG not null,
	groupId LONG not null,
	userGroupId LONG not null,
	primary key (groupId, userGroupId)
);

create table Image (
	mvccVersion LONG default 0 not null,
	imageId LONG not null primary key,
	companyId LONG,
	modifiedDate DATE null,
	type_ VARCHAR(75) null,
	height INTEGER,
	width INTEGER,
	size_ INTEGER
);

create table Layout (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	plid LONG not null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentPlid LONG,
	privateLayout BOOLEAN,
	layoutId LONG,
	parentLayoutId LONG,
	classNameId LONG,
	classPK LONG,
	name STRING null,
	title STRING null,
	description STRING null,
	keywords STRING null,
	robots STRING null,
	type_ VARCHAR(75) null,
	typeSettings TEXT null,
	hidden_ BOOLEAN,
	system_ BOOLEAN,
	friendlyURL VARCHAR(255) null,
	iconImageId LONG,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	css TEXT null,
	priority INTEGER,
	masterLayoutPlid LONG,
	layoutPrototypeUuid VARCHAR(75) null,
	layoutPrototypeLinkEnabled BOOLEAN,
	sourcePrototypeLayoutUuid VARCHAR(75) null,
	publishDate DATE null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	primary key (plid, ctCollectionId)
);

create table LayoutBranch (
	mvccVersion LONG default 0 not null,
	layoutBranchId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	layoutSetBranchId LONG,
	plid LONG,
	name VARCHAR(75) null,
	description STRING null,
	master BOOLEAN
);

create table LayoutFriendlyURL (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	layoutFriendlyURLId LONG not null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	plid LONG,
	privateLayout BOOLEAN,
	friendlyURL VARCHAR(255) null,
	languageId VARCHAR(75) null,
	lastPublishDate DATE null,
	primary key (layoutFriendlyURLId, ctCollectionId)
);

create table LayoutPrototype (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	layoutPrototypeId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name TEXT null,
	description TEXT null,
	settings_ STRING null,
	active_ BOOLEAN
);

create table LayoutRevision (
	mvccVersion LONG default 0 not null,
	layoutRevisionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	layoutSetBranchId LONG,
	layoutBranchId LONG,
	parentLayoutRevisionId LONG,
	head BOOLEAN,
	major BOOLEAN,
	plid LONG,
	privateLayout BOOLEAN,
	name STRING null,
	title STRING null,
	description STRING null,
	keywords STRING null,
	robots STRING null,
	typeSettings TEXT null,
	iconImageId LONG,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	css TEXT null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table LayoutSet (
	mvccVersion LONG default 0 not null,
	layoutSetId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	privateLayout BOOLEAN,
	logoId LONG,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	css TEXT null,
	settings_ TEXT null,
	layoutSetPrototypeUuid VARCHAR(75) null,
	layoutSetPrototypeLinkEnabled BOOLEAN
);

create table LayoutSetBranch (
	mvccVersion LONG default 0 not null,
	layoutSetBranchId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	privateLayout BOOLEAN,
	name VARCHAR(75) null,
	description STRING null,
	master BOOLEAN,
	logoId LONG,
	themeId VARCHAR(75) null,
	colorSchemeId VARCHAR(75) null,
	css TEXT null,
	settings_ TEXT null,
	layoutSetPrototypeUuid VARCHAR(75) null,
	layoutSetPrototypeLinkEnabled BOOLEAN
);

create table LayoutSetPrototype (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	layoutSetPrototypeId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name TEXT null,
	description TEXT null,
	settings_ STRING null,
	active_ BOOLEAN
);

create table ListType (
	mvccVersion LONG default 0 not null,
	listTypeId LONG not null primary key,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null
);

create table MembershipRequest (
	mvccVersion LONG default 0 not null,
	membershipRequestId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	comments STRING null,
	replyComments STRING null,
	replyDate DATE null,
	replierUserId LONG,
	statusId LONG
);

create table Organization_ (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	organizationId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentOrganizationId LONG,
	treePath STRING null,
	name VARCHAR(100) null,
	type_ VARCHAR(75) null,
	recursable BOOLEAN,
	regionId LONG,
	countryId LONG,
	statusId LONG,
	comments STRING null,
	logoId LONG
);

create table OrgGroupRole (
	mvccVersion LONG default 0 not null,
	organizationId LONG not null,
	groupId LONG not null,
	roleId LONG not null,
	companyId LONG,
	primary key (organizationId, groupId, roleId)
);

create table OrgLabor (
	mvccVersion LONG default 0 not null,
	orgLaborId LONG not null primary key,
	companyId LONG,
	organizationId LONG,
	typeId LONG,
	sunOpen INTEGER,
	sunClose INTEGER,
	monOpen INTEGER,
	monClose INTEGER,
	tueOpen INTEGER,
	tueClose INTEGER,
	wedOpen INTEGER,
	wedClose INTEGER,
	thuOpen INTEGER,
	thuClose INTEGER,
	friOpen INTEGER,
	friClose INTEGER,
	satOpen INTEGER,
	satClose INTEGER
);

create table PasswordPolicy (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	passwordPolicyId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	defaultPolicy BOOLEAN,
	name VARCHAR(75) null,
	description STRING null,
	changeable BOOLEAN,
	changeRequired BOOLEAN,
	minAge LONG,
	checkSyntax BOOLEAN,
	allowDictionaryWords BOOLEAN,
	minAlphanumeric INTEGER,
	minLength INTEGER,
	minLowerCase INTEGER,
	minNumbers INTEGER,
	minSymbols INTEGER,
	minUpperCase INTEGER,
	regex STRING null,
	history BOOLEAN,
	historyCount INTEGER,
	expireable BOOLEAN,
	maxAge LONG,
	warningTime LONG,
	graceLimit INTEGER,
	lockout BOOLEAN,
	maxFailure INTEGER,
	lockoutDuration LONG,
	requireUnlock BOOLEAN,
	resetFailureCount LONG,
	resetTicketMaxAge LONG
);

create table PasswordPolicyRel (
	mvccVersion LONG default 0 not null,
	passwordPolicyRelId LONG not null primary key,
	companyId LONG,
	passwordPolicyId LONG,
	classNameId LONG,
	classPK LONG
);

create table PasswordTracker (
	mvccVersion LONG default 0 not null,
	passwordTrackerId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	password_ VARCHAR(75) null
);

create table Phone (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	phoneId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	number_ VARCHAR(75) null,
	extension VARCHAR(75) null,
	typeId LONG,
	primary_ BOOLEAN
);

create table PluginSetting (
	mvccVersion LONG default 0 not null,
	pluginSettingId LONG not null primary key,
	companyId LONG,
	pluginId VARCHAR(75) null,
	pluginType VARCHAR(75) null,
	roles STRING null,
	active_ BOOLEAN
);

create table PortalPreferences (
	mvccVersion LONG default 0 not null,
	portalPreferencesId LONG not null primary key,
	ownerId LONG,
	ownerType INTEGER,
	preferences TEXT null
);

create table Portlet (
	mvccVersion LONG default 0 not null,
	id_ LONG not null primary key,
	companyId LONG,
	portletId VARCHAR(200) null,
	roles STRING null,
	active_ BOOLEAN
);

create table PortletItem (
	mvccVersion LONG default 0 not null,
	portletItemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	portletId VARCHAR(200) null,
	classNameId LONG
);

create table PortletPreferences (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	portletPreferencesId LONG not null,
	companyId LONG,
	ownerId LONG,
	ownerType INTEGER,
	plid LONG,
	portletId VARCHAR(200) null,
	preferences TEXT null,
	primary key (portletPreferencesId, ctCollectionId)
);

create table RatingsEntry (
	uuid_ VARCHAR(75) null,
	entryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	score DOUBLE
);

create table RatingsStats (
	statsId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	totalEntries INTEGER,
	totalScore DOUBLE,
	averageScore DOUBLE
);

create table RecentLayoutBranch (
	mvccVersion LONG default 0 not null,
	recentLayoutBranchId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	layoutBranchId LONG,
	layoutSetBranchId LONG,
	plid LONG
);

create table RecentLayoutRevision (
	mvccVersion LONG default 0 not null,
	recentLayoutRevisionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	layoutRevisionId LONG,
	layoutSetBranchId LONG,
	plid LONG
);

create table RecentLayoutSetBranch (
	mvccVersion LONG default 0 not null,
	recentLayoutSetBranchId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	layoutSetBranchId LONG,
	layoutSetId LONG
);

create table Region (
	mvccVersion LONG default 0 not null,
	regionId LONG not null primary key,
	countryId LONG,
	regionCode VARCHAR(75) null,
	name VARCHAR(75) null,
	active_ BOOLEAN
);

create table Release_ (
	mvccVersion LONG default 0 not null,
	releaseId LONG not null primary key,
	createDate DATE null,
	modifiedDate DATE null,
	servletContextName VARCHAR(75) null,
	schemaVersion VARCHAR(75) null,
	buildNumber INTEGER,
	buildDate DATE null,
	verified BOOLEAN,
	state_ INTEGER,
	testString VARCHAR(1024) null
);

create table Repository (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	repositoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	name VARCHAR(200) null,
	description STRING null,
	portletId VARCHAR(200) null,
	typeSettings TEXT null,
	dlFolderId LONG,
	lastPublishDate DATE null
);

create table RepositoryEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	repositoryEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	repositoryId LONG,
	mappedId VARCHAR(255) null,
	manualCheckInRequired BOOLEAN,
	lastPublishDate DATE null
);

create table ResourceAction (
	mvccVersion LONG default 0 not null,
	resourceActionId LONG not null primary key,
	name VARCHAR(255) null,
	actionId VARCHAR(75) null,
	bitwiseValue LONG
);

create table ResourcePermission (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	resourcePermissionId LONG not null,
	companyId LONG,
	name VARCHAR(255) null,
	scope INTEGER,
	primKey VARCHAR(255) null,
	primKeyId LONG,
	roleId LONG,
	ownerId LONG,
	actionIds LONG,
	viewActionId BOOLEAN,
	primary key (resourcePermissionId, ctCollectionId)
);

create table Role_ (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	roleId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	name VARCHAR(75) null,
	title STRING null,
	description STRING null,
	type_ INTEGER,
	subtype VARCHAR(75) null
);

create table ServiceComponent (
	mvccVersion LONG default 0 not null,
	serviceComponentId LONG not null primary key,
	buildNamespace VARCHAR(75) null,
	buildNumber LONG,
	buildDate LONG,
	data_ TEXT null
);

create table SocialActivity (
	activityId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate LONG,
	activitySetId LONG,
	mirrorActivityId LONG,
	classNameId LONG,
	classPK LONG,
	parentClassNameId LONG,
	parentClassPK LONG,
	type_ INTEGER,
	extraData STRING null,
	receiverUserId LONG
);

create table SocialActivityAchievement (
	activityAchievementId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate LONG,
	name VARCHAR(75) null,
	firstInGroup BOOLEAN
);

create table SocialActivityCounter (
	activityCounterId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	name VARCHAR(75) null,
	ownerType INTEGER,
	currentValue INTEGER,
	totalValue INTEGER,
	graceValue INTEGER,
	startPeriod INTEGER,
	endPeriod INTEGER,
	active_ BOOLEAN
);

create table SocialActivityLimit (
	activityLimitId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	classNameId LONG,
	classPK LONG,
	activityType INTEGER,
	activityCounterName VARCHAR(75) null,
	value VARCHAR(75) null
);

create table SocialActivitySet (
	activitySetId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate LONG,
	modifiedDate LONG,
	classNameId LONG,
	classPK LONG,
	type_ INTEGER,
	extraData STRING null,
	activityCount INTEGER
);

create table SocialActivitySetting (
	activitySettingId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	classNameId LONG,
	activityType INTEGER,
	name VARCHAR(75) null,
	value VARCHAR(1024) null
);

create table SocialRelation (
	uuid_ VARCHAR(75) null,
	relationId LONG not null primary key,
	companyId LONG,
	createDate LONG,
	userId1 LONG,
	userId2 LONG,
	type_ INTEGER
);

create table SocialRequest (
	uuid_ VARCHAR(75) null,
	requestId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate LONG,
	modifiedDate LONG,
	classNameId LONG,
	classPK LONG,
	type_ INTEGER,
	extraData STRING null,
	receiverUserId LONG,
	status INTEGER
);

create table SystemEvent (
	mvccVersion LONG default 0 not null,
	systemEventId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	classUuid VARCHAR(75) null,
	referrerClassNameId LONG,
	parentSystemEventId LONG,
	systemEventSetKey LONG,
	type_ INTEGER,
	extraData TEXT null
);

create table Team (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	teamId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	groupId LONG,
	name VARCHAR(75) null,
	description STRING null,
	lastPublishDate DATE null
);

create table Ticket (
	mvccVersion LONG default 0 not null,
	ticketId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	classNameId LONG,
	classPK LONG,
	key_ VARCHAR(75) null,
	type_ INTEGER,
	extraInfo TEXT null,
	expirationDate DATE null
);

create table UserNotificationDelivery (
	mvccVersion LONG default 0 not null,
	userNotificationDeliveryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	portletId VARCHAR(200) null,
	classNameId LONG,
	notificationType INTEGER,
	deliveryType INTEGER,
	deliver BOOLEAN
);

create table User_ (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	userId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	defaultUser BOOLEAN,
	contactId LONG,
	password_ VARCHAR(75) null,
	passwordEncrypted BOOLEAN,
	passwordReset BOOLEAN,
	passwordModifiedDate DATE null,
	digest VARCHAR(255) null,
	reminderQueryQuestion VARCHAR(75) null,
	reminderQueryAnswer VARCHAR(75) null,
	graceLoginCount INTEGER,
	screenName VARCHAR(75) null,
	emailAddress VARCHAR(254) null,
	facebookId LONG,
	googleUserId VARCHAR(75) null,
	ldapServerId LONG,
	openId VARCHAR(1024) null,
	portraitId LONG,
	languageId VARCHAR(75) null,
	timeZoneId VARCHAR(75) null,
	greeting VARCHAR(255) null,
	comments STRING null,
	firstName VARCHAR(75) null,
	middleName VARCHAR(75) null,
	lastName VARCHAR(75) null,
	jobTitle VARCHAR(100) null,
	loginDate DATE null,
	loginIP VARCHAR(75) null,
	lastLoginDate DATE null,
	lastLoginIP VARCHAR(75) null,
	lastFailedLoginDate DATE null,
	failedLoginAttempts INTEGER,
	lockout BOOLEAN,
	lockoutDate DATE null,
	agreedToTermsOfUse BOOLEAN,
	emailAddressVerified BOOLEAN,
	status INTEGER
);

create table UserGroup (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	userGroupId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentUserGroupId LONG,
	name VARCHAR(255) null,
	description STRING null,
	addedByLDAPImport BOOLEAN
);

create table UserGroupGroupRole (
	mvccVersion LONG default 0 not null,
	userGroupId LONG not null,
	groupId LONG not null,
	roleId LONG not null,
	companyId LONG,
	primary key (userGroupId, groupId, roleId)
);

create table UserGroupRole (
	mvccVersion LONG default 0 not null,
	userId LONG not null,
	groupId LONG not null,
	roleId LONG not null,
	companyId LONG,
	primary key (userId, groupId, roleId)
);

create table UserGroups_Teams (
	companyId LONG not null,
	teamId LONG not null,
	userGroupId LONG not null,
	primary key (teamId, userGroupId)
);

create table UserIdMapper (
	mvccVersion LONG default 0 not null,
	userIdMapperId LONG not null primary key,
	companyId LONG,
	userId LONG,
	type_ VARCHAR(75) null,
	description VARCHAR(75) null,
	externalUserId VARCHAR(75) null
);

create table UserNotificationEvent (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	userNotificationEventId LONG not null primary key,
	companyId LONG,
	userId LONG,
	type_ VARCHAR(200) null,
	timestamp LONG,
	deliveryType INTEGER,
	deliverBy LONG,
	delivered BOOLEAN,
	payload TEXT null,
	actionRequired BOOLEAN,
	archived BOOLEAN
);

create table Users_Groups (
	companyId LONG not null,
	groupId LONG not null,
	userId LONG not null,
	primary key (groupId, userId)
);

create table Users_Orgs (
	companyId LONG not null,
	organizationId LONG not null,
	userId LONG not null,
	primary key (organizationId, userId)
);

create table Users_Roles (
	companyId LONG not null,
	roleId LONG not null,
	userId LONG not null,
	primary key (roleId, userId)
);

create table Users_Teams (
	companyId LONG not null,
	teamId LONG not null,
	userId LONG not null,
	primary key (teamId, userId)
);

create table Users_UserGroups (
	companyId LONG not null,
	userId LONG not null,
	userGroupId LONG not null,
	primary key (userId, userGroupId)
);

create table UserTracker (
	mvccVersion LONG default 0 not null,
	userTrackerId LONG not null primary key,
	companyId LONG,
	userId LONG,
	modifiedDate DATE null,
	sessionId VARCHAR(200) null,
	remoteAddr VARCHAR(75) null,
	remoteHost VARCHAR(75) null,
	userAgent VARCHAR(200) null
);

create table UserTrackerPath (
	mvccVersion LONG default 0 not null,
	userTrackerPathId LONG not null primary key,
	companyId LONG,
	userTrackerId LONG,
	path_ STRING null,
	pathDate DATE null
);

create table VirtualHost (
	mvccVersion LONG default 0 not null,
	virtualHostId LONG not null primary key,
	companyId LONG,
	layoutSetId LONG,
	hostname VARCHAR(200) null,
	defaultVirtualHost BOOLEAN,
	languageId VARCHAR(75) null
);

create table WebDAVProps (
	mvccVersion LONG default 0 not null,
	webDavPropsId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	props TEXT null
);

create table Website (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	websiteId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	url STRING null,
	typeId LONG,
	primary_ BOOLEAN,
	lastPublishDate DATE null
);

create table WorkflowDefinitionLink (
	mvccVersion LONG default 0 not null,
	workflowDefinitionLinkId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	typePK LONG,
	workflowDefinitionName VARCHAR(75) null,
	workflowDefinitionVersion INTEGER
);

create table WorkflowInstanceLink (
	mvccVersion LONG default 0 not null,
	workflowInstanceLinkId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	workflowInstanceId LONG
);