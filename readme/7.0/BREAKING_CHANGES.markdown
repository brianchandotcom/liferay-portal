# What are the Breaking Changes for Liferay 7.0?

This document presents a chronological list of changes that break existing
functionality, APIs, or contracts with third party Liferay developers or users.
We try our best to minimize these disruptions, but sometimes they are
unavoidable.

Here are some of the types of changes documented in this file:

* Functionality that is removed or replaced
* API incompatibilities: Changes to public Java or JavaScript APIs
* Changes to context variables available to templates
* Changes in CSS classes available to Liferay themes and portlets
* Configuration changes: Changes in configuration files, like
 `portal.properties`, `system.properties`, etc.
* Execution requirements: Java version, J2EE Version, browser versions, etc.
* Deprecations or end of support: For example, warning that a certain
feature or API will be dropped in an upcoming version.
* Recommendations: For example, recommending using a newly introduced API that
replaces an old API, in spite of the old API being kept in Liferay Portal for
backwards compatibility.

*This document has been reviewed through commit `768b181`.*

## Breaking Changes Contribution Guidelines

Each change must have a brief descriptive title and contain the following
information:

* **[Title]** Provide a brief descriptive title. Use past tense and follow
the capitalization rules from
<http://en.wikibooks.org/wiki/Basic_Book_Design/Capitalizing_Words_in_Titles>.
* **Date:** Specify the date you submitted the change. Format the date as
*YYYY-MMM* (e.g., 2014-Mar) or *YYYY-MMM-DD* (e.g., 2014-Feb-25).
* **JIRA Ticket:** Reference the related JIRA ticket (e.g., LPS-123456)
(Optional).
* **What changed?** Identify the affected component and the type of change that
was made.
* **Who is affected?** Are end-users affected? Are developers affected? If the
only affected people are those using a certain feature or API, say so.
* **How should I update my code?** Explain any client code changes required.
* **Why was this change made?** Explain the reason for the change. If
applicable, justify why the breaking change was made instead of following a
deprecation process.

Here's the template to use for each breaking change (note how it ends with a
horizontal rule):

```
### [Title]
- **Date:**
- **JIRA Ticket:**

#### What changed?

#### Who is affected?

#### How should I update my code?

#### Why was this change made?

---------------------------------------
```

**80 Columns Rule:** Text should not exceed 80 columns. Keeping text within 80
columns makes it easier to see the changes made between different versions of
the document. Titles, links, and tables are exempt from this rule. Code samples
must follow the column rules specified in Liferay's [Development
Style](http://www.liferay.com/community/wiki/-/wiki/Main/Liferay+development+style).

The remaining content of this document consists of the breaking changes listed
in ascending chronological order.

## Breaking Changes List

### The `liferay-ui:logo-selector` Tag Requires Parameter Changes
- **Date:** 2013-Dec-05
- **JIRA Ticket:** LPS-42645

#### What changed?
The Logo Selector tag now supports uploading an image, storing it as a temporary
file, cropping it, and canceling edits. The tag no longer requires creating a UI
to include the image. Consequently, the `editLogoURL` parameter is no longer
needed and has been removed. The tag now uses the following parameters to
support the new features:

- `currentLogoURL`: the URL to display the image being stored
- `hasUpdateLogoPermission`: `true` if the current user can update the logo
- `maxFileSize`: the size limit for the logo to be uploaded
- `tempImageFileName`: the unique identifier to store the temporary image on
upload

#### Who is affected?
Plugins or templates that are using the `liferay-ui:logo-selector` tag need
to update their usage of the tag.

#### How should I update my code?
You should remove the parameter `editLogoURL` and include (if neccessary) the
parameters `currentLogoURL`, `hasUpdateLogoPermission`, `maxFileSize`, and/or
`tempImageFileName`.

**Example**

Replace:
```
<portlet:renderURL var="editUserPortraitURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
    <portlet:param name="struts_action" value="/users_admin/edit_user_portrait" />
    <portlet:param name="redirect" value="<%= currentURL %>" />
    <portlet:param name="p_u_i_d" value="<%= String.valueOf(selUser.getUserId()) %>" />
    <portlet:param name="portrait_id" value="<%= String.valueOf(selUser.getPortraitId()) %>" />
</portlet:renderURL>

<liferay-ui:logo-selector
    defaultLogoURL="<%= UserConstants.getPortraitURL(themeDisplay.getPathImage(), selUser.isMale(), 0) %>"
    editLogoURL="<%= editUserPortraitURL %>"
    imageId="<%= selUser.getPortraitId() %>"
    logoDisplaySelector=".user-logo"
/>
```

With:
```
<liferay-ui:logo-selector
    currentLogoURL="<%= selUser.getPortraitURL(themeDisplay) %>"
    defaultLogoURL="<%= UserConstants.getPortraitURL(themeDisplay.getPathImage(), selUser.isMale(), 0) %>"
    hasUpdateLogoPermission='<%= UsersAdminUtil.hasUpdateFieldPermission(selUser, "portrait") %>'
    imageId="<%= selUser.getPortraitId() %>"
    logoDisplaySelector=".user-logo"
    maxFileSize="<%= PrefsPropsUtil.getLong(PropsKeys.USERS_IMAGE_MAX_SIZE) / 1024 %>"
    tempImageFileName="<%= String.valueOf(selUser.getUserId()) %>"
/>
```

#### Why was this change made?
This change helps keep a unified UI and consistent experience for uploading
logos in the portal. The logos can be customized from a single location and used
throughout the portal. In addition, the change adds new features such as image
cropping and support for canceling image upload.

---------------------------------------

### Merged Configured Email Signature Field into the Body of Email Messages from Message Boards and Wiki
- **Date:** 2014-Feb-28
- **JIRA Ticket:** LPS-44599

#### What changed?
The configuration for email signatures of notifications from Message Boards and
Wiki has been removed. An automatic update process is available that appends
existing signatures into respective email message bodies for Message Boards and
Wiki notifications. The upgrade process only applies to configured signatures in
the database. In case you declared signatures in portal properties (e.g.,
`portal-ext.properties`), you must make the manual changes explained below.

#### Who is affected?
Users and system administrators who have configured email signatures for Message
Boards or Wiki notifications are affected. System administrators who have
configured portal properties (e.g., `portal-ext.properties`) must make the
manual changes described below.

#### How should I update my code?
You should modify your `portal-ext.properties` file to remove the properties
`message.boards.email.message.added.signature`,
`message.boards.email.message.updated.signature`,
`wiki.email.page.added.signature`, and `wiki.email.page.updated.signature`.
Then, you should append the contents of the signatures to the bodies you had
previously configured in your `portal-ext.properties` file.

**Example**

Replace:
```
wiki.email.page.updated.body=A wiki page was updated.
wiki.email.page.updated.signature=For any doubts email the system administrator
```

With:
```
wiki.email.page.updated.body=A wiki page was updated.\n--\nFor any doubts email the system administrator
```

#### Why was this change made?
This change helps simplify the user interface. The signatures can still be set
inside the message body. There was no real benefit in keeping the signature and
body fields separate.

---------------------------------------

### Removed `get` and `format` Methods That Used `PortletConfig` Parameters
- **Date:** 2014-Mar-07
- **JIRA Ticket:** LPS-44342

#### What changed?
All the methods `get()` and `format()` which had the PortletConfig as a
parameter have been removed.

#### Who is affected?
Any invocations from Java classes or JSPs to these methods in `LanguageUtil` and
`UnicodeLanguageUtil` are affected.

#### How should I update my code?
Replace invocations to these methods with invocations to methods of the same
name that take a `ResourceBundle` parameter, instead of taking a
`PortletConfig` parameter.

**Example**

Replace:
```
LanguageUtil.get(portletConfig, locale, key);
```

With:
```
LanguageUtil.get(portletConfig.getResourceBundle(locale), key);
```

#### Why was this change made?
The removed methods didn't work properly and would never work properly, since
they didn't have all the information they required. Since we expected the
methods were rarely used, we thought it better to remove them without
deprecation than to leave them as buggy methods in the API.

---------------------------------------

### Web Content Articles Now Require a Structure and Template
- **Date:** 2014-Mar-18
- **JIRA Ticket:** LPS-45107

#### What changed?
Web content is now required to use a structure and template. A default structure
and template named *Basic Web Content* was added to the global scope, and can be
modified or deleted.

#### Who is affected?
Applications that use the Journal API to create web content without a structure
or template are affected.

#### How should I update my code?
You should always use a structure and template when creating web content. You
can still use the *Basic Web Content* from the global scope (using the
structure key `basic-web-content`), but you should keep in mind that users can
modify or delete it.

#### Why was this change made?
This change gives users the flexibility to modify the default structure and
template.

---------------------------------------

### Changed the AssetRenderer and Indexer APIs to Include the `PortletRequest` and `PortletResponse` Parameters
- **Date:** 2014-May-07
- **JIRA Ticket:** LPS-44639 and LPS-44894

#### What changed?
The `getSummary()` method in the AssetRenderer API and the `doGetSummary()`
method in the Indexer API have changed and must include a `PortletRequest`
and `PortletResponse` parameter as part of their signatures.

#### Who is affected?
These methods must be updated in all AssetRenderer and Indexer implementations.

#### How should I update my code?
Add a `PortletRequest` and `PortletResponse` parameter to the signatures of
these methods.

**Example**

Replace:
```
protected Summary doGetSummary(Document document, Locale locale, String snippet, PortletURL portletURL)
```

With:
```
protected Summary doGetSummary(Document document, Locale locale, String snippet, PortletRequest portletRequest, PortletResponse portletResponse)
```

and replace:
```
public String getSummary(Locale locale)
```

With:
```
public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse)
```

#### Why was this change made?
Some content (such as web content) needs the `PortletRequest` and
`PortletResponse` parameters in order to be rendered.

---------------------------------------

### Only One Portlet Instance's Settings is Used Per Portlet
- **Date:** 2014-Jun-06
- **JIRA Ticket:** LPS-43134

#### What changed?
Previously, some portlets allowed separate setups per portlet instance,
regardless of whether the instances were in the same page or in different pages.
For some of the portlet setup fields, however, it didn't make sense to allow
different values in different instances. The flexibility of these fields was
unnecessary and confused users. As part of this change, these fields have been
moved from portlet instance setup to Site Administration.

The upgrade process takes care of making the necessary database changes. In the
case of several portlet instances having different configurations, however, only
one configuration is preserved.

For example, if you configured three Bookmarks portlets where the mail
configuration was the same, upgrade will be the same and you won't have any
problem. But if you configured the three portlet instances differently, only one
configuration will be chosen. To find out which configuration is chosen, you can
check the log generated in the console by the upgrade process.

Since configuring instances of the same portlet type differently is highly
discouraged and notoriously problematic, we expect this change will
inconvenience only a very low minority of portal users.

#### Who is affected?
Affected users are those who have specified varying configurations for multiple
portlet instances of a portlet type, that stores configurations at the layout
level.

#### How should I update my code?
The upgrade process chooses one portlet instance's configurations and stores it
at the service level. After the upgrade, you should review the portlet's
configuration and make any necessary modifications.

#### Why was this change made?
Unifying portlet and service configuration facilitates managing them.

---------------------------------------

### DDM Structure Local Service API No Longer Has the `updateXSDFieldMetadata()` operation
- **Date:** 2014-Jun-11
- **JIRA Ticket:** LPS-47559

#### What changed?
The `updateXSDFieldMetadata()` operation was removed from the DDM Structure
Local Service API.

DDM Structure Local API users should reference a structure's internal
representation; any call to modify a DDM structure's content should be done
through the DDMForm model.

#### Who is affected?
Applications that use the DDM Structure Local Service API might be affected.

#### How should I update my code?
You should always use DDMForm to update the DDM Structure content. You can
retrieve it by calling `ddmStructure.getDDMForm()`. Perform any changes to it and
then call `DDMStructureLocalServiceUtil.updateDDMStructure(ddmStructure)`.

#### Why was this change made?
This change gives users the flexibility to modify the structure content without
concerning themselves with the DDM Structure's internal content representation
of data.

---------------------------------------

### The `aui:input` Tag for Type `checkbox` No Longer Creates a Hidden Input
- **Date:** 2014-Jun-16
- **JIRA Ticket:** LPS-44228

#### What changed?
Whenever the aui:input tag is used to generate an input of type checkbox,
only an input tag will be generated, instead of the checkbox and hidden field it
was generating before.

#### Who is affected?
Anyone trying to grab the previously generated fields is affected. The change
mostly affects JavaScript code trying to add some additional actions when
clicking on the checkboxes.

#### How should I update my code?
In your front-end JavaScript code, follow these steps:

- Remove the `Checkbox` suffix when querying for the node in any of its forms,
like `A.one(...)`, `$(...)`, etc.
- Remove any action that tries to set the value of the checkbox on the
previously generated hidden field.

#### Why was this change made?
This change makes generated forms more standard and interoperable since it falls
back to the checkboxes default behavior. It allows the form to be submitted
properly even when JavaScript is disabled.

---------------------------------------

### Using `util-taglib` No Longer Binds You to Using `portal-service`'s `javax.servlet.jsp` Implementation
- **Date:** 2014-Jun-19
- **JIRA Ticket:** LPS-47682

#### What changed?
Several APIs in `portal-service.jar` contained references to the
`javax.servlet.jsp` package. This forced `util-taglib`, which depended on many
of the package's features, to be bound to the same JSP implementation.

Due to this, the following APIs had breaking changes:

- `LanguageUtil`
- `UnicodeLanguageUtil`
- `VelocityTaglibImpl`
- `ThemeUtil`
- `RuntimePageUtil`
- `PortletDisplayTemplateUtil`
- `DDMXSDUtil`
- `PortletResourceBundles`
- `ResourceActionsUtil`
- `PortalUtil`

#### How should I update my code?
Code invoking the APIs listed above should should be updated to use an
`HttpServletRequest` parameter instead of the formerly used `PageContext`
parameter.

#### Why was this change made?
As stated previously, the use of the `javax.servlet.jsp` API in `portal-service`
prevented the use of any other JSP impl within plugins (OSGi or otherwise). This
limited what Liferay could change with respect to providing its own JSP
implementation within OSGi.

---------------------------------------

### Changes in Exceptions Thrown by User Services
- **Date:** 2014-Jul-03
- **JIRA Ticket:** LPS-47130

#### What changed?

In order to provide more information about the root cause of an exception,
several exceptions have been extended with static inner classes, one for each
cause. As a result of this effort, some exceptions have been identified that
really belong as static inner subclasses of existing exceptions.

#### Who is affected?

Client code which is handling any of the following exceptions:

- `DuplicateUserScreenNameException`
- `DuplicateUserEmailAddressException`

#### How should I update my code?

Replace the old exception with the equivalent inner class exception as follows:

- `DuplicateUserScreenNameException` &rarr;
`UserScreenNameException.MustNotBeDuplicate`
- `DuplicateUserEmailAddressException` &rarr;
`UserEmailAddressException.MustNotBeDuplicate`

#### Why was this change made?

This change provides more information to clients of the services API about the
root cause of an error. It provides a more helpful error message to the end-user
and it allows for easier recovery, when possible.

---------------------------------------

### Removed Trash Logic from `DLAppHelperLocalService` Methods
- **Date:** 2014-Jul-22
- **JIRA Ticket:** LPS-47508

#### What changed?

The `deleteFileEntry()` and `deleteFolder()` methods in
`DLAppHelperLocalService` deleted the corresponding trash entry in the database.
This logic has been removed from these methods.

#### Who is affected?

Every caller of the `deleteFileEntry()` and `deleteFolder()` methods is
affected.

#### How should I update my code?

There is no direct replacement. Trash operations are now accessible through the
`TrashCapability` implementations for each repository. The following code
demonstrates using a `TrashCapability` instance to delete a `FileEntry`:

    Repository repository = getRepository();

    TrashCapability trashCapability = repository.getCapability(
        TrashCapability.class);

    FileEntry fileEntry = repository.getFileEntry(fileEntryId);

    trashCapability.deleteFileEntry(fileEntry);

Note that the `deleteFileEntry()` and `deleteFolder()` methods in
`TrashCapability` not only remove the trash entry, but also remove the folder or
file entry itself, and any associated data, such as assets, previews, etc.

#### Why was this change made?

This change was made to allow different kinds of repositories to support trash
operations in a uniform way.

---------------------------------------

### Removed Sync Logic from `DLAppHelperLocalService` Methods
- **Date:** 2014-Sep-05
- **JIRA Ticket:** LPS-48895

#### What changed?

The `moveFileEntry()` and `moveFolder()` methods in `DLAppHelperLocalService`
fired Liferay Sync events. These methods have been removed.

#### Who is affected?

Every caller of the `moveFileEntry()` and `moveFolder()` methods is affected.

#### How should I update my code?

There is no direct replacement. Sync operations are now accessible through the
`SyncCapability` implementations for each repository. The following code
demonstrates using a `SyncCapability` instance to move a `FileEntry`:

    Repository repository = getRepository();

    SyncCapability syncCapability = repository.getCapability(
        SyncCapability.class);

    FileEntry fileEntry = repository.getFileEntry(fileEntryId);

    syncCapability.moveFileEntry(fileEntry);

#### Why was this change made?

There are repositories that don't support Liferay Sync operations.

---------------------------------------

### Removed the `.aui` Namespace from Bootstrap
- **Date:** 2014-Sep-26
- **JIRA Ticket:** LPS-50348

#### What changed?

The `.aui` namespace was removed from prefixing all of Bootstrap's CSS.

#### Who is affected?

Theme and plugin developers that targeted their CSS to rely on the namespace are
affected.

#### How should I update my code?

Theme developers can still manually add an `aui.css` file in their `_diffs`
directory, and add it back in. The `aui` CSS class can also be added to the
`$root_css_class` variable.

#### Why was this change made?

Due to changes in the Sass parser, the nesting of third-party libraries was
causing some syntax errors which broke other functionality (e.g., RTL
conversion). There was also a lot of additional complexity for a relatively
minor benefit.

---------------------------------------

### Moved `MVCPortlet`, `ActionCommand` and `ActionCommandCache` from `util-bridges.jar` to `portal-service.jar`
- **Date:** 2014-Sep-26
- **JIRA Ticket:** LPS-50156

#### What changed?

The classes from package `com.liferay.util.bridges.mvc` in `util-bridges.jar`
were moved to a new package `com.liferay.portal.kernel.portlet.bridges.mvc`
in `portal-service.jar`.

The classes affected are:

```
com.liferay.util.bridges.mvc.ActionCommand
com.liferay.util.bridges.mvc.BaseActionCommand
```

They are now:

```
com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand
com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand
```

In addition, `com.liferay.util.bridges.mvc.MVCPortlet` is deprecated, but was
made to extend `com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet`.

#### Who is affected?

This will affect any implementations of `ActionCommand`.

#### How should I update my code?

Replace imports of `com.liferay.util.bridges.mvc.ActionCommand` with
`com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand` and imports of
`com.liferay.util.bridges.mvc.BaseActionCommand` with
`com.liferay.portal.kernel.portlet.bridges.mvc.BaseActionCommand`.

#### Why was this change made?

This change was made to avoid duplication of an implementable interface in the
system. Duplication can cause `ClassCastException`s.

---------------------------------------

### Convert Process Classes Are No Longer Specified via the `convert.processes` Portal Property, but Are Contributed as OSGi Modules
- **Date:** 2014-Oct-09
- **JIRA Ticket:** LPS-50604

#### What changed?

The implementation class `com.liferay.portal.convert.ConvertProcess` was renamed
`com.liferay.portal.convert.BaseConvertProcess`. An interface named
`com.liferay.portal.convert.ConvertProcess` was created for it.

The `convert.processes` key was removed from `portal.properties`.
Consequentially, `ConvertProcess` implementations must register as OSGi
components.

#### Who is affected?

This affects any implementations of the former `ConvertProcess` class, including
`ConvertProcess` class implementations in EXT plugins. Until version 6.2, this
type of service could only be implemented with an EXT plugin, given that the
`ConvertProcess` class resided in `portal-impl`.

#### How should I update my code?

You should replace `extends com.liferay.portal.convert.ConvertProcess` with
`extends com.liferay.portal.convert.BaseConvertProcess` and annotate the class
with `@Component(service=ConvertProcess.class)`.

Then turn your EXT plugin into an OSGi bundle and deploy it to the portal. You
should see your convert process in the configuration UI.

#### Why was this change made?

This change was made as a part of the ongoing strategy to modularize Liferay
Portal by means of an OSGi container.

---------------------------------------

### Migration of the Field *Type* from the Journal Article API into a Vocabulary
- **Date:** 2014-Oct-13
- **JIRA Ticket:** LPS-50764

#### What changed?

The field *type* from the Journal Article entity has been removed. The Journal
API no longer supports this parameter. A new vocabulary called *Web Content
Types* is created when migrating from previous versions of Liferay, and the
types from the existing articles are kept as categories of this vocabulary.

#### Who is affected?

This affects any caller of the removed methods `JournalArticle.getType()` and
`JournalFeed.getType()`, and callers of `ArticleTypeException`'s methods, that
attempt to use the former `type` parameter of the `JournalArticle` or
`JournalFeed` service.

#### How should I update my code?

If your logic was not affected by the type, you can simply remove the `type`
parameter from the Journal API call. If your logic was affected by the type, you
should now use the `AssetCategoryService` to obtain the category of the journal
articles.

#### Why was this change made?

Web Content Types had to be updated in a properties file and could not be
translated easily. Categories provide a much more flexible behavior and a better
UI. In addition, all the features, such as filters, developed for categories can
be used now in asset publishers and faceted search.

---------------------------------------

### Removed the `getClassNamePortletId(String)` Method from `PortalUtil` Class
- **Date:** 2014-Nov-11
- **JIRA Ticket:** LPS-50604

#### What changed?

The `getClassNamePortletId(String)` method from the `PortalUtil` class has been
removed.

#### Who is affected?

This affects any plugin using the method.

#### How should I update my code?

If you are using the method, you should implement it yourself in a private
utility class.

#### Why was this change made?

This change was needed in order to modularize the portal. Also, the method is no
longer being used inside Liferay Portal.

---------------------------------------

### Removed the *Header Web Content* and *Footer Web Content* Preferences from the RSS Portlet
- **Date:** 2014-Nov-12
- **JIRA Ticket:** LPS-46984

#### What changed?

The *Header Web Content* and *Footer Web Content* preferences from the RSS
portlet have been removed. The portlet now supports Application Display
Templates (ADT), which provide templating capabilities that can apply web
content to the portlet's header and footer.

#### Who is affected?

This affects RSS portlets that are displayed on pages and that use these
preferences. These preferences are no longer used in the RSS portlet.

#### How should I update my code?

Even though these preferences have been removed, an ADT can be created to
produce the same result. Liferay will publish this ADT so that it can be used in
the RSS portlet.

#### Why was this change made?

The support for ADTs in the RSS portlet not only covers this use case, but also
covers many other use cases, providing a much simpler way to create custom
preferences.

---------------------------------------

### Removed the `createFlyouts` Method from `liferay/util.js` and Related Resources
- **Date:** 2014-Dec-18
- **JIRA Ticket:** LPS-52275

#### What changed?

The `Liferay.Util.createFlyouts` method has been completely removed from core
files.

#### Who is affected?

This only affects third party developers who are explicitly calling
`Liferay.Util.createFlyouts` for the creation of flyout menus. It will not
affect any menus in core files.

#### How should I update my code?

If you are using the method, you can achieve the same behavior with CSS.

#### Why was this change made?

This method was removed due to there being no working use cases in Portal, and
its overall lack of functionality.

---------------------------------------

### Removed Support for *Flat* Thread View in Discussion Comments
- **Date:** 2014-Dec-30
- **JIRA Ticket:** LPS-51876

#### What changed?

Discussion comments are now displayed using the *Combination* thread view, and
the number of levels displayed in the tree is limited.

#### Who is affected?

This affects installations that specify portal property setting
`discussion.thread.view=flat`, which was the default setting.

#### How should I update my code?

There is no need to update anything since the portal property has been removed
and the `combination` thread view is now hard-coded.

#### Why was this change made?

Flat view comments were originally implemented as an option to tree view
comments, which were having performance issues with comment pagination.

Portal now uses a new pagination implementation that performs well. It allows
comments to display in a hierarchical view, making it easier to see reply
history. Therefore, the `flat` thread view is no longer needed.

---------------------------------------

### Removed *Asset Tag Properties*
- **Date:** 2015-Jan-13
- **JIRA Ticket:** LPS-52588

#### What changed?

The *Asset Tag Properties* have been removed. The service no longer exists and
the Asset Tag Service API no longer has this parameter. The behavior associated
with tag properties in the Asset Publisher and XSL portlets has also been
removed.

#### Who is affected?

This affects any plugin that uses the Asset Tag Properties service.

#### How should I update my code?

If you are using this functionality, you can achieve the same behavior with
*Asset Category Properties*. If you are using the Asset Tag Service, remove the
`String[]` tag properties parameter from your calls to the service's methods.

#### Why was this change made?

The Asset Tag Properties were deprecated for the 6.2 version of Liferay Portal.

---------------------------------------

### Removed the `asset.publisher.asset.entry.query.processors` Property
- **Date:** 2015-Jan-22
- **JIRA Ticket:** LPS-52966

#### What changed?

The `asset.publisher.asset.entry.query.processors` property has been removed
from `portal.properties`.

#### Who is affected?

This affects any hook that uses the
`asset.publisher.asset.entry.query.processors` property.

#### How should I update my code?

If you are using this property to register Asset Entry Query Processors, your
Asset Entry Query Processor must implement the
`com.liferay.portlet.assetpublisher.util.AssetEntryQueryProcessor` interface and
must specify the `@Component(service=AssetEntryQueryProcessor.class)`
annotation.

#### Why was this change made?

This change was made as a part of the ongoing strategy to modularize Liferay
Portal.

---------------------------------------

### Replaced the `ReservedUserScreenNameException` with `UserScreenNameException.MustNotBeReserved` in `UserLocalService`
- **Date:** 2015-Jan-29
- **JIRA Ticket:** LPS-53113

#### What changed?

Previous to Liferay 7, several methods of `UserLocalService` could throw a
`ReservedUserScreenNameException` when a user set a screen name that was not
allowed. That exception has been deprecated and replaced with
`UserScreenNameException.MustNotBeReserved`.

#### Who is affected?

This affects developers who have written code that catches the
`ReservedUserScreenNameException` while calling the affected methods.

#### How should I update my code?

You should replace catching exception `ReservedUserScreenNameException` with
catching exception `UserScreenNameException.MustNotBeReserved`.

#### Why was this change made?

A new pattern has been defined for exceptions that provides higher expressivity
in their names and also more information regarding why the exception was thrown.

The new exception `UserScreenNameException.MustNotBeReserved` has all the
necessary information about why the exception was thrown and its context. In
particular, it contains the user ID, the problematic screen name, and the list
of reserved screen names.

---------------------------------------

### Replaced the `ReservedUserEmailAddressException` with `UserEmailAddressException` Inner Classes in User Services
- **Date:** 2015-Feb-03
- **JIRA Ticket:** LPS-53279

#### What changed?

Previous to Liferay 7, several methods of `UserLocalService` and `UserService`
could throw a `ReservedUserEmailAddressException` when a user set an email 
address that was not allowed. That exception has been deprecated and replaced
with `UserEmailAddressException.MustNotUseCompanyMx`,
`UserEmailAddressException.MustNotBePOP3User`, and
`UserEmailAddressException.MustNotBeReserved`.

#### Who is affected?

This affects developers who have written code that catches the
`ReservedUserEmailAddressException` while calling the affected methods.

#### How should I update my code?

Depending on the method you're calling and the context in which you're calling
it, you should replace catching exception `ReservedUserEmailAddressException`
with catching exception `UserEmailAddressException.MustNotUseCompanyMx`,
`UserEmailAddressException.MustNotBePOP3User`, or
`UserEmailAddressException.MustNotBeReserved`.

#### Why was this change made?

A new pattern has been defined for exceptions. This pattern requires using
higher expressivity in exception names and requires that each exception provide
more information regarding why it was thrown.

Each new exception provides its context and has all the necessary information
about why the exception was thrown. For example, the
`UserEmailAddressException.MustNotBeReserved` exception contains the problematic
email address and the list of reserved email addresses.

---------------------------------------

### Added Required Attribute `paginationURL` to the Tag `liferay-ui:discussion`
- **Date:** 2015-Feb-05
- **JIRA Ticket:** LPS-53313

#### What changed?

The `liferay-ui:discussion` tag now contains a new required attribute
`paginationURL`.

#### Who is affected?

This affects all developers who are using this tag in their plugins.

#### How should I update my code?

You should include the new attribute `paginationURL` in the tag. This attribute
holds a URL that returns an HTML fragment containing the next comments for
portlets such as Asset Publisher, Blogs, Document Library, etc.

If you are using the Liferay `MVCPortlet` class, you can use the following URL:

    <portlet:resourceURL var="discussionPaginationURL">
        <portlet:param name="invokeTaglibDiscussion"
            value="<%= Boolean.TRUE.toString() %>" />
    </portlet:resourceURL>

#### Why was this change made?

This change was made to support comment pagination.

---------------------------------------

### Replaced `ReservedUserIdException` with `UserIdException` Inner Classes
- **Date:** 2015-Feb-10
- **JIRA Ticket:** LPS-53487

#### What changed?

The `ReservedUserIdException` has been deprecated and replaced with
`UserIdException.MustNotBeReserved`.

#### Who is affected?

This affects developers who have written code that catches the
`ReservedUserIdException` while calling the affected methods.

#### How should I update my code?

You should replace catching exception `ReservedUserIdException` with
catching exception `UserIdException.MustNotBeReserved`.

#### Why was this change made?

A new pattern has been defined for exceptions that provides higher expressivity
in their names and also more information regarding why the exception was thrown.

The new exception `UserIdException.MustNotBeReserved` provides its context and
has all the necessary information about why the exception was thrown. In
particular, it contains the problematic user ID and the list of reserved user
IDs.

------------------------------------------------------------------------------

### Moved the `AssetPublisherUtil` Class and Removed It from the Public API
- **Date:** 2015-Feb-11
- **JIRA Ticket:** LPS-52744

#### What changed?

The class `AssetPublisherUtil` from the `portal-service` module has been moved
to the module `AssetPublisher` and it is no longer a part of the public API.

#### Who is affected?

This affects developers who have written code that uses the `AssetPublisherUtil`
class.

#### How should I update my code?

This `AssetPublisherUtil` class should no longer be used from other modules
since it contains utility methods for the Asset Publisher portlet. If needed,
you can define a dependency with the Asset Publisher module and use the new
class.

#### Why was this change made?

This change has been made as part of the modularization efforts to decouple the
different parts of the portal.

---------------------------------------

### Removed Operations That Used the `Fields` Class from the `StorageAdapter` Interface
- **Date:** 2015-Feb-11
- **JIRA Ticket:** LPS-53021

#### What changed?

All operations that used the `Fields` class have been removed from the
`StorageAdapter` interface.

#### Who is affected?

This affects developers who have written code that directly calls these 
operations. 

#### How should I update my code?

You should update your code to use the `DDMFormValues` class instead of the
`Fields` class.

#### Why was this change made?

This change has been made due to the deprecation of the `Fields` class. 

---------------------------------------

### Created a New `getType()` Method That is Implemented in `DLProcessor`
- **Date:** 2015-Feb-17
- **JIRA Ticket:** LPS-53574

#### What changed?

The `DLProcessor` interface has a new method `getType()`.

#### Who is affected?

This affects developers who have created a `DLProcessor`.

#### How should I update my code?

You should implement the new method and return the type of processor. You can
check the class `DLProcessorConstants` to see processor types.

#### Why was this change made?

Previous to Liferay 7, developers were forced to extend one of the existing
`DLProcessor` classes and developers using the extended class had to check the
instance of that class to determine its processor type.

With this change, developers no longer need to extend any particular class to
create their own `DLProcessor` and their processor's type can be clearly
specified by a constant from the class `DLProcessorConstants`.

---------------------------------------

### Changed the Usage of the `liferay-ui:restore-entry` Tag
- **Date:** 2015-Mar-01
- **JIRA Ticket:** LPS-54106

#### What changed?

The usage of the taglib tag `liferay-ui:restore-entry` serves a different
purpose now. It renders the UI to restore elements from the Recycle Bin.

#### Who is affected?

This affects developers using the tag `liferay-ui:restore-entry`.

#### How should I update my code?

You should replace your calls to the tag with code like the listing below:

    <aui:script use="liferay-restore-entry">
        new Liferay.RestoreEntry(
        {
                checkEntryURL: '<%= checkEntryURL.toString() %>',
                duplicateEntryURL: '<%= duplicateEntryURL.toString() %>',
                namespace: '<portlet:namespace />'
            }
        );
    </aui:script>

In the above code, the `checkEntryURL` should be an `ActionURL` of your portlet,
which checks whether the current entry can be restored from the Recycle Bin. The
`duplicateEntryURL` should be a `RenderURL` of your portlet, that renders the UI
to restore the entry, resolving any existing conflicts. In order to generate
that URL, you can use the tag `liferay-ui:restore-entry`, which has been
refactored for this usage.

#### Why was this change made?

This change allows the Trash portlet to be an independent module. Its actions
and views are no longer used by the tag; they are now the responsability of
each plugin.

---------------------------------------

### Added Required Parameter `resourceClassNameId` for DDM Template Search Operations
- **Date:** 2015-Mar-03
- **JIRA Ticket:** LPS-52990

#### What changed?

The DDM template `search` and `searchCount` operations have a new parameter
called `resourceClassNameId`.

#### Who is affected?

This affects developers who have direct calls to the `DDMTemplateService` or 
`DDMTemplateLocalService`.

#### How should I update my code?

You should add the `resourceClassNameId` parameter to your calls. This parameter
represents the resource that owns the permission for the DDM template. For
example, if the template is a WCM template, the `resourceClassNameId` points to
the `JournalArticle`'s `classNameId`. If the template is a DDL template, the
`resourceClassNameId` points to the `DDLRecordSet`'s `classNameId`. If the
template is an ADT template, the `resourceClassNameId` points to the
`PortletDisplayTemplate`'s `classNameId`.

#### Why was this change made?

This change was made in order to implement model resource permissions for DDM
templates, such as `VIEW`, `DELETE`, `PERMISSIONS`, and `UPDATE`.

---------------------------------------

### Replaced the Breadcrumb Portlet's Display Styles with ADTs
- **Date:** 2015-Mar-12
- **JIRA Ticket:** LPS-53577

#### What changed?

The custom display styles of the breadcrumb tag added using JSPs no longer work.
They have been replaced by Application Display Templates (ADT).

#### Who is affected?

This affects developers that use the following properties:

    breadcrumb.display.style.default=horizontal

    breadcrumb.display.style.options=horizontal,vertical

#### How should I update my code?

To style the Breadcrumb portlet, you should use ADTs instead of using custom
styles in your JSPs. ADTs can be created from the UI of the portal by navigating
to *Site Settings* &rarr; *Application Display Templates*. ADTs can also be
created programatically.

#### Why was this change made?

ADTs allow you to change an application's look and feel without changing its JSP
code.

---------------------------------------

### Changed Usage of the `liferay-ui:ddm-template-selector` Tag
- **Date:** 2015-Mar-16
- **JIRA Ticket:** LPS-53790

#### What changed?

The attribute `classNameId` of the `liferay-ui:ddm-template-selector` taglib tag
has been renamed `className`.

#### Who is affected?

This affects developers using the `liferay-ui:ddm-template-selector` tag.

#### How should I update my code?

In your `liferay-ui:ddm-template-selector` tags, rename the `classNameId`
attribute to `className`.

#### Why was this change made?

Application Display Templates were being referenced by their UUID, which was
usually not known by the developer. Referencing all DDM templates by their class
name simplifies using this tag.

---------------------------------------

### Changed the Usage of Asset Preview
- **Date:** 2015-Mar-16
- **JIRA Ticket:** LPS-53972

#### What changed?

Instead of directly including the JSP referenced by the `AssetRenderer`'s
`getPreviewPath` method to preview an asset, you now use a taglib tag.

#### Who is affected?

This affects developers who have written code that directly calls an
`AssetRenderer`'s `getPreviewPath` method to preview an asset.

#### How should I update my code?

JSP code that previews an asset by calling an `AssetRenderer`'s `getPreviewPath`
method, such as in the example code below, must be replaced:

    <liferay-util:include
        page="<%= assetRenderer.getPreviewPath(liferayPortletRequest, liferayPortletResponse) %>"
        portletId="<%= assetRendererFactory.getPortletId() %>"
        servletContext="<%= application %>"
    />

To preview an asset, you should instead use the `liferay-ui:asset-display` tag,
passing it an instance of the asset entry and an asset renderer preview
template. Here's an example of using the tag:

    <liferay-ui:asset-display
        assetEntry="<%= assetEntry %>"
        template="<%= AssetRenderer.TEMPLATE_PREVIEW %>"
    />

#### Why was this change made?

This change simplifies using asset previews.

---------------------------------------

### Replaced the Language Portlet's Display Styles with ADTs
- **Date:** 2015-Mar-30
- **JIRA Ticket:** LPS-54419

#### What changed?

The custom display styles of the language tag added using JSPs no longer work.
They have been replaced by Application Display Templates (ADT).

#### Who is affected?

This affects developers that use the following properties:

    language.display.style.default=icon

    language.display.style.options=icon,long-text

#### How should I update my code?

To style the Language portlet, you should use ADTs instead of using custom
styles in your JSPs. ADTs can be created from the UI of the portal by navigating
to *Site Settings* &rarr; *Application Display Templates*. ADTs can also be
created programatically.

#### Why was this change made?

ADTs allow you to change an application's look and feel without changing its JSP
code.

---------------------------------------

### Added Required Parameter `groupId` for Adding Tags, Categories, and Vocabularies
- **Date:** 2015-Mar-31
- **JIRA Ticket:** LPS-54570

#### What changed?

The API for adding tags, categories, and vocabularies now requires passing the
`groupId` parameter. Previously, it had to be included in the `ServiceContext`
parameter passed to the method.
 
#### Who is affected?

This affects developers who have direct calls to the following methods:

- `addTag` in `AssetTagService` or `AssetTagLocalService`
- `addCategory` in `AssetCategoryService` or `AssetCategoryLocalService`
- `addVocabulary` in `AssetVocabularyService` or `AssetVocabularyLocalService`
- `updateFolder` in `JournalFolderService` or `JournalFolderLocalService`

#### How should I update my code?

You should add the `groupId` parameter to your calls. This parameter represents
the site in which you are creating the tag, category, or vocabulary. It can be
obtained from the `themeDisplay` or `serviceContext` using
`themeDisplay.getScopeGroupId()` or `serviceContext.getScopeGroupId()`,
respectively.

#### Why was this change made?

This change was made in order improve the API. The `groupId` parameter was
always required, but it was hidden by the `ServiceContext` object.

---------------------------------------

### Removed the Tags `portlet:icon-*`
- **Date:** 2015-Mar-31
- **JIRA Ticket:** LPS-54620

#### What changed?

The following tags have been removed:

- `portlet:icon-close`
- `portlet:icon-configuration`
- `portlet:icon-edit`
- `portlet:icon-edit-defaults`
- `portlet:icon-edit-guest`
- `portlet:icon-export-import`
- `portlet:icon-help`
- `portlet:icon-maximize`
- `portlet:icon-minimize`
- `portlet:icon-portlet-css`
- `portlet:icon-print`
- `portlet:icon-refresh`
- `portlet:icon-staging`

#### Who is affected?

This affects developers who have written code that uses these tags.

#### How should I update my code?

The tag `liferay-ui:icon` can replace the call to the previous tags. All the
previous tags have been converted into Java classes that implement the methods
that the `icon` tag requires.

See the modules `portlet-configuration-icon-*` in the `modules/addons` folder.

#### Why was this change made?

These tags were used to generate the configuration icon of portlets. This
functionality will now be managed from OSGi modules instead of tags since OSGi
modules provide more flexibility and can be included in any app.

---------------------------------------

### Added new methods in `ScreenNameValidator` interface
- **Date:** 2015-Mar-17
- **JIRA Ticket:** LPS-53409

#### What changed?

The `ScreenNameValidator` interface has new methods `getDescription(Locale locale)`
and `getJSValidation()`.


#### Who is affected?

This affects developers who have implemented custom screen name validator with
the `ScreenNameValidator` interface.


#### How should I update my code?

You should implement the new methods introduced in the interface.

- `getDescription(Locale locale)`: returns a description of what the screen name 
validator validates.

- `getJSValidation()`: returns the JavaScript input validator on the client side.

#### Why was this change made?

Previous to Liferay 7, validation for user screen name characters was hard-coded 
in `UserLocalService`. A new property `users.screen.name.special.characters` has
been added to provide configurability of special characters allowed in screen
names.

In addition, developers can now specify custom input validator for the screen name
on the client side by providing a JavaScript validator in `getJSValidation()`.

---------------------------------------

### Changed default value of copy-request-parameters init parameter in MVCPortlet
- **Date:** 2015-Apr-15
- **JIRA Ticket:** LPS-54798

#### What changed?

The copy request init parameter default value is now set to true in MVCPortlet

#### Who is affected?

This affects developers that have created portlets that extend MVCPortlet

#### How should I update my code?

To change the default property, you have to set the init parameter to false in
your MVCPortlet:

"javax.portlet.init-param.copy-request-parameters=false"

#### Why was this change made?

This was done for backwards compatibility

---------------------------------------
