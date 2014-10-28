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

*This document has been reviewed through commit `a00a5c3`.*

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
protected Summary doGetSummary(Document document, Locale locale, String snippet, PortletURL portletURL, PortletRequest portletRequest, PortletResponse portletResponse)
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

### The `aui:input` Taglib for Type `checkbox` No Longer Creates a Hidden Input
- **Date:** 2014-Jun-16
- **JIRA Ticket:** LPS-44228

#### What changed?
Whenever the aui:input taglib is used to generate an input of type checkbox,
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