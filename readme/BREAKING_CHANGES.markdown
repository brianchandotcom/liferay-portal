# What are the Breaking Changes for Liferay 7.4?

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

*This document has been reviewed through commit `4334fc6cc349`.*

## Breaking Changes Contribution Guidelines

Each change must have a brief descriptive title and contain the following
information:

* **[Title]** Provide a brief descriptive title. Use past tense and follow
  the capitalization rules from
  <http://en.wikibooks.org/wiki/Basic_Book_Design/Capitalizing_Words_in_Titles>.
* **Date:** Specify the date you submitted the change. Format the date as
  *YYYY-MMM-DD* (e.g., 2014-Feb-25).
* **JIRA Ticket:** Reference the related JIRA ticket (e.g., LPS-12345)
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
### Title
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
must follow the column rules specified in Liferay's
[Development Style](http://www.liferay.com/community/wiki/-/wiki/Main/Liferay+development+style).

The remaining content of this document consists of the breaking changes listed
in ascending chronological order.

## Breaking Changes List

### The tag liferay-ui:flash is no longer available
- **Date:** 2020-Oct-13
- **JIRA Ticket:** [LPS-121732](https://issues.liferay.com/browse/LPS-121732)

#### What changed?

The tag `liferay-ui:flash` has been deleted and is no longer available.

#### Who is affected?

This affects any development that uses the `liferay-ui:flash` tag to embed
Adobe Flash movies in a page.

#### How should I update my code?

If you still need to embed Adobe Flash content in a page, you would need to
write your own code using one of the standard mechanisms such as `SWFObject`.

#### Why was this change made?

This change was made to align with [Adobe dropping support for Flash](https://www.adobe.com/products/flashplayer/end-of-life.html)
in December 31, 2020 and browsers removing Flash support in upcoming versions.

---------------------------------------

### The /portal/flash path is no longer available
- **Date:** 2020-Oct-13
- **JIRA Ticket:** [LPS-121733](https://issues.liferay.com/browse/LPS-121733)

#### What changed?

The public path `/portal/flash` that could be used to play an Adobe Flash movie
passing the movie URL as a parameter has been removed.

Additionally, the property and accessors have been removed from `ThemeDisplay`
and are no longer accesible.

#### Who is affected?

This affects people that were using the path `/c/portal/flash` directly to show
pages with Adobe Flash content.

#### How should I update my code?

A direct code update is not possible. One possible solution would be to create
a custom page simulating to simulate the old behaviour and read the different
movie parameters from the URL and then instantiate it using the common means
for Adobe Flash reproduction.

#### Why was this change made?

This change was made to align with [Adobe dropping support for Flash](https://www.adobe.com/products/flashplayer/end-of-life.html)
in December 31, 2020 and browsers removing Flash support in upcoming versions.

---------------------------------------

### The AUI module `swfobject` is no longer available
- **Date:** 2020-Oct-13
- **JIRA Ticket:** [LPS-121736](https://issues.liferay.com/browse/LPS-121736)

#### What changed?

The AUI module `swfobject` that provided a way to load the library SWFObject
commonly used to embed Adobe Flash content has been removed.

#### Who is affected?

This affects people that were requiring the AUI `swfobject` module as a way to
make the library available globally.

#### How should I update my code?

If you still need to embed Adobe Flash content, you can inject the SWFObject
library directly in your application using any of the available mechanisms.

#### Why was this change made?

This change was made to align with [Adobe dropping support for Flash](https://www.adobe.com/products/flashplayer/end-of-life.html)
in December 31, 2020 and browsers removing Flash support in upcoming versions.

---------------------------------------

### Refactor Clamd integration to use Clamd remote service and remove portal
properties configuration for AntivirusScanner selection and hook support for
AntivirusScanner registration in favor of AntivirusScanner OSGi integration.

- **Date:** 2020-Oct-21
- **JIRA Ticket:** [LPS-122280](https://issues.liferay.com/browse/LPS-122280)

#### What changed?

The portal impl version of Clamd integration has been pulled out as an OSGi
service to use Clamd remote service.
The portal properties configuration for AntivirusScanner implementation
selection and hook support for AntivirusScanner implementation registration has
been removed in favor of the AntivirusScanner OSGi integration.

#### Who is affected?

This affects people that were using the portal impl version of Clamd integration
and people that were providing their own AntivirusScanner implementation by hook.

#### How should I update my code?

If you were using the portal impl version of Clamd integration, you need to go
to Control Panel -> System Settings -> Security -> category.antivirus to
configure the new Clamd remote service.

If you were providing your own AntivirusScanner implementation by hook, you need
to update your implementation as an OSGi service with a service ranking higher
than Clamd remote service AntivirusScanner implementation which is default to 0.

#### Why was this change made?

This change was made to better support container environment and unify the api
to do OSGi integration.

---------------------------------------

### The AssetEntries_AssetCategories table and its corresponding code have been removed from the portal
- **Date:** 2020-Oct-16
- **JIRA Ticket:** [LPS-89065](https://issues.liferay.com/browse/LPS-89065)

#### What changed?

AssetEntries_AssetCategories and its corresponding code have been removed from
the portal. In 7.2, this mapping table and the corresponding interface were
replaced by the table AssetEntryAssetCategoryRel and the service
AssetEntryAssetCategoryRelLocalService.

#### Who is affected?

This affects any content or code that relies on calling the old interfaces for
the AssetEntries_AssetCategories relationship, through the
AssetEntryLocalService and AssetCategoryLocalService.

#### How should I update my code?

Use the new methods in AssetEntryAssetCategoryRelLocalService to retrieve the
same data as before. The method signatures haven't changed; they have just been
relocated to a different service.

#### Why was this change made?

This change was made due to changes resulting from [LPS-76488](https://issues.liferay.com/browse/LPS-76488),
which let developers control the order of a list of assets for a given category.
The breaking changes regarding the service replacement were notified on
2019-Sep-11, this would be the final step to removing the table.

---------------------------------------

### The way we register display pages for entities has changed
- **Date:** 2020-Oct-27
- **JIRA Ticket:** [LPS-122275](https://issues.liferay.com/browse/LPS-122275)

#### What changed?

The way default display pages are handled has changed. From Liferay Portal 7.1
through Liferay Portal 7.3 the entities that had a default display page were
persisted in the database while those that don't have display pages associated
to them were ommited. This behaviour has been switched, so that the default
display pages are not persisted and those entities that don't have a display
page associated to them are tracked.

#### Who is affected?

Everyone with custom entities for which display pages can be created

#### How should I update my code?

If you have custom entities with display pages, we have created a base upgrade
process (`BaseUpgradeAssetDisplayPageEntries`) that receives a table, primary
key column name and a className, that will handle the swap logic.

#### Why was this change made?

This change was made to make the logic for display pages more consistent with
the overall concept of display pages.

---------------------------------------

### Previously unused and deprecated JSP tags are no longer available
- **Date:** 2020-Nov-24
- **JIRA Ticket:** [LPS-112476](https://issues.liferay.com/browse/LPS-112476)

#### What changed?

A series of deprecated and unused JSP tags have been removed and are no longer
available. This list includes:

- clay:table
- liferay-ui:alert
- liferay-ui:input-scheduler
- liferay-ui:organization-search-container-results
- liferay-ui:organization-search-form
- liferay-ui:ratings
- liferay-ui:search-speed
- liferay-ui:table-iterator
- liferay-ui:toggle-area
- liferay-ui:toggle
- liferay-ui:user-search-container-results
- liferay-ui:user-search-

#### Who is affected?

Everyone still using one of the removed tags

#### How should I update my code?

Use the new tags for those where replacements were previously avaialable. In
many cases, there's no direct replacement for these tags, so if you still need
to use them, you could make a copy of the old implementation and serve it
directly from your project.

#### Why was this change made?

This change was made to remove legacy code that was previously signaled for
removal in an attempt to clarify the default JSP component offering and focus
on providing a smaller but higher quality set of compoentns.

---------------------------------------
### The CSS class .container-fluid-1280 has been replaced with .container-fluid.container-fluid-max-xl
- **Date:** 2020-Nov-24
- **JIRA Ticket:** [LPS-123894](https://issues.liferay.com/browse/LPS-123894)

#### What changed?

The CSS class `.container-fluid-1280` has been replaced with `.container-fluid.container-fluid-max-xl` and the compatibility layer that had its style has been removed from Portal.

#### Who is affected?

All the container elements that had the CSS class `.container-fluid-1280`

#### How should I update my code?

The first recommendation is to use the updated CSS classes from Clay `.container-fluid.container-fluid-max-xl` instead of `.container-fluid-1280`. The second one is to use ClayLayout [Components](https://clayui.com/docs/components/layout.html) & [Taglibs](https://clayui.com/docs/get-started/using-clay-in-jsps.html#clay-sidebar)

#### Why was this change made?

This change was made to remove deprecated legacy code from Portal and improve the code consistency and performance

---------------------------------------