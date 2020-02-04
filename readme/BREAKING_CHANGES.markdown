# What are the Breaking Changes for Liferay 7.3?

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

*This document has been reviewed through commit ``.*

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

### Removed Portal Property user.groups.copy.layouts.to.user.personal.site
- **Date:** 2019-Dec-26
- **JIRA Ticket:** [LPS-106339](https://issues.liferay.com/browse/LPS-106339)

#### What changed?

The portal property `user.groups.copy.layouts.to.user.personal.site` and the
behavior associated with it have been removed.

#### Who is affected?

This affects any one who set the property to `true` to copy user group pages to
user personal sites.

#### How should I update my code?

There's no direct replacement for this property. If you have anything that
depends on the behavior, you can copy the old implementations of
`UserGroupLocalServiceImpl#copyUserGroupLayouts` to your own project.

#### Why was this change made?

The behavior associated with this property has been deprecated since 6.2.

---------------------------------------

### Liferay FontAwesome Is No Longer Included by Default
- **Date:** 2019-Aug-21
- **JIRA Ticket:** [LPS-100021](https://issues.liferay.com/browse/LPS-100021)

#### What changed?

Previously, Liferay FontAwesome that included icon fonts for Font Awesome,
Glyphicon and custom Liferay icons was included by default. These icon fonts are
no longer included out of the box.

#### Who is affected?

This affects any content or code that relies on such icon fonts in pages or
sites where a theme that does not include the fonts is applied.

#### How should I update my code?

Depending on how you're using icon fonts, you might need to take different
approaches:

##### For liferay-ui:icon usage

Replace `<liferay-ui:icon iconCssClass="icon-user">` with
`<liferay-ui:icon icon="user" markupView="lexicon" />`

##### For JS generated icons

Those manually generating fontawesome icon html, can use the
`Liferay.Util.getLexiconIconTpl('user')` API. For example the previous call
would return the html code for a user svg icon

##### For direct html within jsps

Developers directly using icons in jsps, can either use the `liferay-ui:icon`
tag as explained above or the `clay:icon` one to generate svg-based icons
instead.

##### For non-controlled code

People who don't have access to the content that uses icon fonts or if that
don't want to update those occurrences can reintroduce the icon fonts in their
themes.

During the 7.2 upgrade process, the theme upgrade assistant prompted developers
to keep fontawesome as part of the theme. Themes that already include the icon
fonts won't be affected and will continue to work in 7.3.

#### Why was this change made?

This change was made to avoid serving unnecessary files saving bandwidth and
increasing performance of your sites by default.

---------------------------------------

### Removed com.liferay.asset.taglib.servlet.taglib.soy.AssetTagsSelectorTag
- **Date:** 2019-Oct-15
- **JIRA Ticket:** [LPS-100144](https://issues.liferay.com/browse/LPS-100144)

#### What changed?

The Java class
`com.liferay.asset.taglib.servlet.taglib.soy.AssetTagsSelectorTag` has been
removed.

#### Who is affected?

This affects any code that directly instantiates or extends this class.

#### How should I update my code?

There's no direct replacement for the removed class. If you have code that
depends on it, you would need to copy over the old implementation to your own
project and change the dependency to rely on your local version.

#### Why was this change made?

The `asset:asset-tags-selector` and its components have been migrated to React,
making the old tag and its soy infrastructure unnecessary.

---------------------------------------

### Removed liferay.frontend.Slider
- **Date:** 2019-Oct-10
- **JIRA Ticket:** [LPS-100124](https://issues.liferay.com/browse/LPS-100124)

#### What changed?

The legacy metal+soy `liferay.frontend.Slider` component has been removed.

#### Who is affected?

This affects any code that relies on such component, which is usually done via
`soy` as `{call liferay.frontend.Slider /}`

#### How should I update my code?

There's no direct replacement for `liferay.frontend.Slider` component which was
simply being used as a temporary bridge for legacy behaviour.

If you have a component that relies on it, you can choose to co-locate a copy of
the old implementation and use it locally within your module.

#### Why was this change made?

The compatibility `liferay.frontend.Slider` component is no longer used and was
already deprecated in `7.2`.

---------------------------------------

### Removed liferay.frontend.ProgressBar
- **Date:** 2019-Aug-28
- **JIRA Ticket:** [LPS-100122](https://issues.liferay.com/browse/LPS-100122)

#### What changed?

The legacy metal+soy `liferay.frontend.ProgressBar` component has been removed.

#### Who is affected?

This affects any code that relies on such component, which is usually done via
`soy` as `{call liferay.frontend.ProgressBar /}`

#### How should I update my code?

There's no direct replacement for `liferay.frontend.ProgressBar` component
which was simply being used as a temporary bridge for legacy behaviour.

If you have a component that relies on it, you can choose to co-locate a copy of
the old implementation and use it locally within your module.

#### Why was this change made?

The compatibility `liferay.frontend.ProgessBar` component is no longer used and
was already deprecated in `7.2`.

---------------------------------------

### Removed cache bootstrap feature
- **Date:** 2020-Jan-8
- **JIRA Ticket:** [LPS-96563](https://issues.liferay.com/browse/LPS-96563)

#### What changed?

The cache bootstrap feature has been removed, which means you can not use the
following properties to enable/configure cache bootstrap:
`ehcache.bootstrap.cache.loader.enabled`,
`ehcache.bootstrap.cache.loader.properties.default`,
`ehcache.bootstrap.cache.loader.properties.${specific.cache.name}`.

#### Who is affected?

This affects who is using the properties listed above.

#### How should I update my code?

There's no direct replacement for the removed feature. If you have code that
depends on it, you would need to implement it by yourself.

#### Why was this change made?

This change was made to avoid security issues.

---------------------------------------

### Removed support for auto deploying EXT plugins
- **Date:** 2019-Dec-31
- **JIRA Ticket:** [LPS-106008](https://issues.liferay.com/browse/LPS-106008)

#### What changed?

The support for deploying EXT plugins using Auto Deployer (via
liferay-home/deploy folder) has been removed. EXT plugins copied to the deploy
folder will not be recognized any more.

#### Who is affected?

This affects who is deploying EXT plugins via the Auto Deployer.

#### How should I update my code?

There's no direct replacement for the removed feature. If you have EXT plugin,
you would need to deploy it manually or using [`ant direct-deploy`](https://github.com/liferay/liferay-plugins-ee/blob/7.0.x/ext/build-common-ext.xml#L211).

#### Why was this change made?

This feature has been deprecated since 7.1.

---------------------------------------
### Removed liferay-frontend:cards-treeview tag
- **Date:** 2020-Jan-10
- **JIRA Ticket:** [LPS-106899](https://issues.liferay.com/browse/LPS-106899)

#### What changed?

The liferay-frontend:cards-treeview tag has been removed.

#### Who is affected?

This affects anyone using the tag from a jsp or some of its components inside
a SOY (Closure Templates) template.

#### How should I update my code?

There's no direct replacement for the removed feature. If you have code that
depends on it, you would need to implement it by yourself.

#### Why was this change made?

This change was made because the UI tag was mostly internal and very specific
and local usage did not grant keeping it around

---------------------------------------

### Removed liferay-frontend:contextual-sidebar tag
- **Date:** 2020-Jan-10
- **JIRA Ticket:** [LPS-100146](https://issues.liferay.com/browse/LPS-100146)

#### What changed?

The liferay-frontend:contextual-sidebar tag has been removed.

#### Who is affected?

This affects anyone using the tag from a jsp or some of its components inside
a SOY (Closure Templates) template.

#### How should I update my code?

There's no direct replacement for the removed feature. If you have code that
depends on it, you would need to implement it by yourself.

#### Why was this change made?

This change was made because the UI tag was mostly internal and very specific
and local usage did not grant keeping it around

---------------------------------------

### Replace OSGi configuration autoUpgrade by portal property
### upgrade.database.auto.run
- **Date:** 2020-Jan-03
- **JIRA Ticket:** [LPS-102842](https://issues.liferay.com/browse/LPS-102842)

#### What changed?

The OSGi property `autoUpgrade` defined in
`com.liferay.portal.upgrade.internal.configuration.ReleaseManagerConfiguration.config`
has been replaced by the portal property `upgrade.database.auto.run`.

Unlike the old property, which only controlled the upgrade processes in
modules, the new one also affects to Core upgrade processes. The default value
is now false so upgrade processes won't run on startup or module deployment.
Remember that you can execute module upgrade processes anytime via Gogo
console.

#### Who is affected?

This only affects development environments where we don't want to run the
upgrade anytime we deploy a new process. Setting this property as true in
production environments is not supported. In these cases the use of the
upgrade tool to execute minor and major schema version changes is mandatory.

#### How should I update my code?

It does not impact your code.

#### Why was this change made?

To unify the auto-upgrade feature between the Core and modules. The default
value has also changed to avoid the execution of new upgrade processes on
startup in production environments.

---------------------------------------

### jQuery is no longer included by default
- **Date:** 2020-Feb-04
- **JIRA Ticket:** [LPS-95726](https://issues.liferay.com/browse/LPS-95726)

#### What changed?

Previously, `jQuery` was being included in every page by default and made
available through the global `window.$` and the scoped `AUI.$` variables. After
this change, `jQuery` is no longer included by default and those variables will
be `undefined`.

#### Who is affected?

This affects any developer who used `AUI.$` or `window.$` in their custom
scripts.

#### How should I update my code?

You should provide your own version `jQuery` to be used by your custom
developments following any of the possible strategies to add third party
libraries.

Additionally, as a temporary measure, you can bring back the old behaviour by
setting the `Enable jQuery` property in `System Settings > Third Party > jQuery`
to `true`.

#### Why was this change made?

This change was made to avoid bundling and serving additional library code on
every page that was mostly unused and redundant.

---------------------------------------