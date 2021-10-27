# What are the Breaking Changes for Liferay 7.4?

This document presents a chronological list of changes that break existing functionality, APIs, or contracts with third party Liferay developers or users. We try our best to minimize these disruptions, but sometimes they are unavoidable.

Here are some of the types of changes documented in this file:

* Functionality that is removed or replaced
* API incompatibilities: Changes to public Java or JavaScript APIs
* Changes to context variables available to templates
* Changes in CSS classes available to Liferay themes and portlets
* Configuration changes: Changes in configuration files, like `portal.properties`, `system.properties`, etc.
* Execution requirements: Java version, J2EE Version, browser versions, etc.
* Deprecations or end of support: For example, warning that a certain feature or API will be dropped in an upcoming version.

*This document has been reviewed through the breaking change entry at commit `384fefe7facc`.*

Each change must have a brief descriptive title and contain the following information:

* **[Title]** Provide a brief descriptive title. Use past tense and follow the capitalization rules from <http://en.wikibooks.org/wiki/Basic_Book_Design/Capitalizing_Words_in_Titles>.
* **Date:** Specify the date you submitted the change. Format the date as *YYYY-MMM-DD* (e.g., 2014-Feb-03).
* **JIRA Ticket:** Reference the related JIRA ticket (e.g., LPS-12345) (Optional).
* **What changed?** Identify the affected component and the type of change that was made.
* **Who is affected?** Are end-users affected? Are developers affected? If the only affected people are those using a certain feature or API, say so.
* **How should I update my code?** Explain any client code changes required.
* **Why was this change made?** Explain the reason for the change. If applicable, justify why the breaking change was made instead of following a deprecation process.

Here is the template to use for each breaking change (note how it ends with a horizontal rule):

```
## Title
- **Date: YYYY-MMM-DD**
- **JIRA Ticket: [LPS-1234](https://issues.liferay.com/browse/LPS-1234)**

### What changed?

### Who is affected?

### How should I update my code?

### Why was this change made?

---------------------------------------

```

The remaining content of this document consists of the breaking changes listed in ascending chronological order.

## Removed the liferay-ui:flash Tag
- **Date:** 2020-Oct-13
- **JIRA Ticket:** [LPS-121732](https://issues.liferay.com/browse/LPS-121732)

### What changed?

The tag `liferay-ui:flash` has been deleted and is no longer available.

### Who is affected?

This affects any development that uses the `liferay-ui:flash` tag.

### How should I update my code?

If you still need to embed Adobe Flash content in a page, write your own code using a standard mechanism such as `SWFObject`.

### Why was this change made?

This change aligns with [Adobe dropping support for Flash](https://www.adobe.com/products/flashplayer/end-of-life.html) in December 31, 2020 and with upcoming versions browsers removing Flash support.

---------------------------------------

## Removed the /portal/flash Path
- **Date:** 2020-Oct-13
- **JIRA Ticket:** [LPS-121733](https://issues.liferay.com/browse/LPS-121733)

### What changed?

Previously you could play an Adobe Flash movie by passing a movie URL as a parameter to the `/portal/flash` public path. The `/portal/flash path` path has been removed.

Additionally, the property and accessors have been removed from `ThemeDisplay` and are no longer available.

### Who is affected?

This affects you if you are using the `/c/portal/flash` path directly to show pages with Adobe Flash content.

### How should I update my code?

A direct code update is impossible. However, you can create a custom page that simulates the old behavior. The page could parse movie parameters from the URL and instantiate the movie using the common means for Adobe Flash reproduction.

### Why was this change made?

This change aligns with [Adobe dropping support for Flash](https://www.adobe.com/products/flashplayer/end-of-life.html) in December 31, 2020 and with upcoming versions browsers removing Flash support.

---------------------------------------

## Removed the swfobject AUI Module
- **Date:** 2020-Oct-13
- **JIRA Ticket:** [LPS-121736](https://issues.liferay.com/browse/LPS-121736)

### What changed?

The AUI module `swfobject` has been removed. It provided a way to load the SWFObject library, commonly used to embed Adobe Flash content.

### Who is affected?

This affects you if you are making the SWFObject library available globally via the AUI `swfobject` module.

### How should I update my code?

If you still need to embed Adobe Flash content, you can inject the SWFObject library directly in your application using other available mechanisms.

### Why was this change made?

This change aligns with [Adobe dropping support for Flash](https://www.adobe.com/products/flashplayer/end-of-life.html) in December 31, 2020 and with upcoming versions browsers removing Flash support.

---------------------------------------

## Removed the AssetEntries_AssetCategories Table and Corresponding Code
- **Date:** 2020-Oct-16
- **JIRA Ticket:** [LPS-89065](https://issues.liferay.com/browse/LPS-89065)

### What changed?

The `AssetEntries_AssetCategories` mapping table and its corresponding code have been removed.

### Who is affected?

This affects you if you use `AssetEntryLocalService` and `AssetCategoryLocalService` to operate on relationships between asset entries and asset categories.

### How should I update my code?

Use the new methods in `AssetEntryAssetCategoryRelLocalService`. Note, that the method signatures are the same as they were in `AssetEntryAssetCategoryRelLocalService`.

### Why was this change made?

This change removes an unnecessary table and corresponding code.

It is a followup step to the [Liferay AssetEntries_AssetCategories Is No Longer Used](https://learn.liferay.com/dxp/latest/en/liferay-internals/reference/7-2-breaking-changes.html#liferay-assetentries-assetcategories-is-no-longer-used) 7.2 breaking change where the table was replaced by the `AssetEntryAssetCategoryRel` table and the corresponding interfaces in `AssetEntryLocalService` and `AssetCategoryLocalService` were moved into `AssetEntryAssetCategoryRelLocalService`.

---------------------------------------

## Refactored AntivirusScanner Support and Clamd Integration
- **Date:** 2020-Oct-21
- **JIRA Ticket:** [LPS-122280](https://issues.liferay.com/browse/LPS-122280)

### What changed?

The portal's Clamd integration implementation has been replaced by an OSGi service that uses a Clamd remote service. The AntivirusScanner OSGi integration has replaced the AntivirusScanner implementation selection portal properties and AntivirusScanner implementation hook registration portal properties.

### Who is affected?

This affects you if you are using the portal's Clamd integration implementation or if you are providing your own AntivirusScanner implementation via a hook.

### How should I update my code?

Enable the new Clamd integration and AntivirusScanner support. See [Enabling Antivirus Scanning for Uploaded Files](https://learn.liferay.com/dxp/latest/en/system-administration/file-storage/enabling-antivirus-scanning-for-uploaded-files.html).

If you are providing your own AntivirusScanner implementation via a hook, convert it to an OSGi service that has a service ranking higher greater than zero. The Clamd remote service AntivirusScanner implementation service ranking is zero.

### Why was this change made?

This change supports container environments better and unifies the API to do OSGi integration.

---------------------------------------

## Changed Entity Display Page Registration Tracking Logic
- **Date:** 2020-Oct-27
- **JIRA Ticket:** [LPS-122275](https://issues.liferay.com/browse/LPS-122275)

### What changed?

The persisting (tracking) of entity associations with display pages has changed. In Liferay DXP/Portal versions 7.1 through 7.3, only entity associations with default display pages were persisted; entities with no display page associations and entity associations with specific, non-default display pages were not persisted. This change switches the behavior.

Here is the new behavior:

- Entities linked to a default display page are not persisted.
- Entities that do not have a display page are persisted.
- Entities that have a specific, non-default display page are persisted.

### Who is affected?

This affects you if you have custom entities for which display pages can be created.

### How should I update my code?

If you have custom entities with display pages, swap the display page logic by adding the `BaseUpgradeAssetDisplayPageEntries` upgrade process to your application. The process receives a table, primary key column name, and a class name.

### Why was this change made?

This change makes the display page logic more consistent with the overall display page concept.

---------------------------------------

## Removed Deprecated and Unused JSP Tags
- **Date:** 2020-Nov-24
- **JIRA Ticket:** [LPS-112476](https://issues.liferay.com/browse/LPS-112476)

### What changed?

A series of deprecated and unused JSP tags have been removed and are no longer available. These tags are included:

- `clay:table`
- `liferay-ui:alert`
- `liferay-ui:input-scheduler`
- `liferay-ui:organization-search-container-results`
- `liferay-ui:organization-search-form`
- `liferay-ui:ratings`
- `liferay-ui:search-speed`
- `liferay-ui:table-iterator`
- `liferay-ui:toggle-area`
- `liferay-ui:toggle`
- `liferay-ui:user-search-container-results`
- `liferay-ui:user-search-form`

### Who is affected?

This affects you if you are using any of the removed tags.

### How should I update my code?

See the 7.3 [`liferay-ui.tld`](https://github.com/liferay/liferay-portal/blob/7.3.x/util-taglib/src/META-INF/liferay-ui.tld) for removed tags that have replacements. However, many of the tags have no direct replacement. If you still need to use a tag that does not have a direct replacement, you can copy the old implementation and serve it directly from your project.

### Why was this change made?

These tags were removed in an attempt to clarify the default JSP component offering and to focus on providing a smaller but higher quality component set.

---------------------------------------

## Replaced the .container-fluid-1280 CSS Class
- **Date:** 2020-Nov-24
- **JIRA Ticket:** [LPS-123894](https://issues.liferay.com/browse/LPS-123894)

### What changed?

The `.container-fluid-1280` CSS class has been replaced with `.container-fluid.container-fluid-max-xl`. The compatibility layer that had `.container-fluid-1280`'s style has been removed too.

### Who is affected?

This affects you if your container elements have the `.container-fluid-1280` CSS class.

### How should I update my code?

Use the updated CSS classes from Clay `.container-fluid.container-fluid-max-xl` instead of `.container-fluid-1280`. Alternatively, use ClayLayout [Components](https://clayui.com/docs/components/layout.html) and [TagLibs](https://clayui.com/docs/get-started/using-clay-in-jsps.html#clay-sidebar).

### Why was this change made?

The change removes deprecated legacy code and improves code consistency and performance.

---------------------------------------

## Disabled Runtime Minification of CSS and JavaScript Resources by Default
- **Date:** 2020-Nov-27
- **JIRA Ticket:** [LPS-123550](https://issues.liferay.com/browse/LPS-123550)

### What changed?

The `minifier.enable` portal property now defaults to `false`. Instead of performing minification of CSS and JS resources at run time, we prepare pre-minified resources at build time. There should be no user-visible changes in page styles or logic.

### Who is affected?

This affects you if your implementations depend on the runtime minifier (usually the Google Closure Compiler).

### How should I update my code?

If you want to maintain the former runtime minification behavior, set the `minifier.enable` portal property to `true`.

### Why was this change made?

Moving frontend resource minification from run time to build time reduces server load and facilitates using the latest minification technologies available within the frontend ecosystem.

---------------------------------------

## Removed the SoyPortlet Class
- **Date:** 2020-Dec-09
- **JIRA Ticket:** [LPS-122955](https://issues.liferay.com/browse/LPS-122955)

### What changed?

The `SoyPortlet` class has been removed. It used to implement a portlet whose views were backed by Closure Templates (Soy).

### Who is affected?

This affects you if you are using `SoyPortlet` as a base for your portlet developments.

### How should I update my code?

We heavily recommend re-writing your Soy portlets using either a well established architecture such as `MVCPortlet` using JSPs or a particular frontend framework of your choice.

### Why was this change made?

This was done as a way to simplify our frontend technical offering and better focus on proven technologies with high demand in the market.

A further exploration and analysis of the different front-end options available can be found in [The State of Frontend Infrastructure](https://liferay.dev/blogs/-/blogs/the-state-of-frontend-infrastructure) including a rationale on why we are moving away from Soy:

> Liferay invested several years into Soy believing it was "the Holy Grail". We believed the ability to compile Closure Templates would provide us with performance comparable to JSP and accommodate reusable components from other JavaScript frameworks. While Soy came close to achieving some of our goals, we never hit the performance we wanted and more importantly, we felt like we were the only people using this technology.

---------------------------------------

## Removed Server-side Closure Templates (Soy) Support
- **Date:** 2020-Dec-14
- **JIRA Ticket:** [LPS-122956](https://issues.liferay.com/browse/LPS-122956)

### What changed?

The following modules and the classes they exported to allow Soy rendering server-side have been removed:
- `portal-template-soy-api`
- `portal-template-soy-impl`
- `portal-template-soy-context-contributor`

To simplify the migration, the following modules are deprected but available to provide only client-side initialization of previous Soy components:
- `portal-template-soy-renderer-api`
- `portal-template-soy-renderer-impl`

### Who is affected?

This affects you if you are using removed classes like `SoyContext`, `SoyHTMLData`, etc. and declaring `TemplateContextContributor` using `LANG_TYPE_SOY` as the value for the `lang.type` attribute.

This affects you if you are initializing Soy components using our Soy `ComponentRenderer`.

### How should I update my code?

There is no replacement for the removed Soy support. If the first scenario describes you, switch to a different supported template language and rewrite your templates and components.

If you are using `ComponentRenderer`, the only difference should be that your components no longer produce markup server-side. If this is important to you, a temporary workaround has been added. You can manually generate a version of the markup you want to render server-side and pass the markup as a `__placeholder__` property in your `context` parameter. Remember, `ComponentRenderer` is deprecated and will eventually be removed; so we kindly recommend rewriting your component using a different technology.

### Why was this change made?

This is done as a way to simplify our frontend technical offering and better focus on proven technologies with high demand in the market.

A further exploration and analysis of the different front-end options available can be found in [The State of Frontend Infrastructure](https://liferay.dev/blogs/-/blogs/the-state-of-frontend-infrastructure) including a rationale on why we are moving away from Soy:

> Liferay invested several years into Soy believing it was "the Holy Grail". We believed the ability to compile Closure Templates would provide us with performance comparable to JSP and accommodate reusable components from other JavaScript frameworks. While Soy came close to achieving some of our goals, we never hit the performance we wanted and more importantly, we felt like we were the only people using this technology.

---------------------------------------

## Removed com.liferay.portal.kernel.model.PortletPreferences Methods getPreferences and setPreferences
- **Date:** 2020-Dec-20
- **JIRA Ticket:** [LPS-122562](https://issues.liferay.com/browse/LPS-122562)

### What changed?

Portlet preferences are no longer stored and retrieved as XML. They are now stored in a table called `PortletPreferenceValue` that has separate columns for preference names and preference values.

### Who is affected?

This affects you if you are directly getting or setting portlet preferences via `com.liferay.portal.kernel.model.PortletPreferences` methods `getPreferences` or `setPreferences`.

### How should I update my code?

Access `javax.portlet.PortletPreferences` instances via `PortletPreferencesLocalService`. Get and set preferences using the `javax.portlet.PortletPreferences` API.

### Why was this change made?

This change simplifies upgrades, reduces storage requirements, and supports querying preferences without using the `like` operator.

---------------------------------------

## Removed CSS Compatibility Layer
- **Date:** 2021-Jan-02
- **JIRA Ticket:** [LPS-123359](https://issues.liferay.com/browse/LPS-123359)

### What changed?

The support for Boostrap 3 markup has been deleted and is no longer available.

### Who is affected?

This affects you if you are using Boostrap 3 markup or if you have not correctly migrated to Boostrap 4 markup.

### How should I update my code?

If you are using Clay markup you can update it by following the last [Clay components](https://clayui.com/docs/components/index.html) version. If your markup is based on Boostrap 3, you can update the markup with Boostrap 4 markup following the [Bootstrap migration guidelines](https://getbootstrap.com/docs/4.4/migration/).

### Why was this change made?

The configurable CSS compatibility layer simplified migrating from Liferay 7.0 to 7.1 but removing the layer resolves conflicts with new styles and improves general CSS weight.

---------------------------------------

## Removed the spi.id Property From the Log4j XML Definition File
- **Date:** 2021-Jan-19
- **JIRA Ticket:** [LPS-125998](https://issues.liferay.com/browse/LPS-125998)

### What changed?

The `spi.id` property in the Log4j XML definition file has been removed.

### Who is affected?

This affects you if you are using `@spi.id@` in its custom Log4j XML definition file.

### How should I update my code?

Remove `@spi.id@` from your Log4j XML definition file.

### Why was this change made?

SPI was removed by [LPS-110758](https://issues.liferay.com/browse/LPS-110758).

---------------------------------------

## Removed Deprecated Attributes From the frontend-taglib-clay Tags
- **Date:** 2021-Jan-26
- **JIRA Ticket:** [LPS-125256](https://issues.liferay.com/browse/LPS-125256)

### What changed?

The deprecated attributes have been removed from the `frontend-taglib-clay` TagLib.

### Who is affected?

This affects you if you use deprecated attributes in `<clay:*>` tags.

### Why was this change made?

The `frontend-taglib-clay` module is now using components from [`Clay v3`](https://github.com/liferay/clay), which does not support the removed attributes.

---------------------------------------

## Changed Handling of HTML Tag Boolean Attributes
- **Date:** 2021-Feb-18
- **JIRA Ticket:** [LPS-127832](https://issues.liferay.com/browse/LPS-127832)

### What changed?

Boolean HTML attributes will only be rendered if passed a value of `true`. The value for such attributes will be their canonical name.

Previously, a value such as `false` for a `disabled` attribute would be rendered into the DOM as `disabled="false"`; now, it the attribute is omitted. Likewise, a `true` value for a `disabled` attribute was formerly rendered into the DOM as `disabled="true"`; now it is rendered as `disabled="disabled"`.

### Who is affected?

This affects you if you are passing the following boolean attributes to tag libraries:

- `"allowfullscreen"`
- `"allowpaymentrequest"`
- `"async"`
- `"autofocus"`
- `"autoplay"`
- `"checked"`
- `"controls"`
- `"default"`
- `"disabled"`
- `"formnovalidate"`
- `"hidden"`
- `"ismap"`
- `"itemscope"`
- `"loop"`
- `"multiple"`
- `"muted"`
- `"nomodule"`
- `"novalidate"`
- `"open"`
- `"playsinline"`
- `"readonly"`
- `"required"`
- `"reversed"`
- `"selected"`
- `"truespeed"`

### How should I update my code?

Make sure to pass a `true` value to boolean attributes you want present in the DOM. Update CSS selectors that target a `true` value (e.g., `[disabled="true"]`) to target presence of the attribute (e.g., `[disabled]`) or its canonical name (e.g., `[disabled="disabled"]`).

### Why was this change made?

This change was made for better compliance with [the HTML Standard](https://html.spec.whatwg.org/#boolean-attribute), which says that "The presence of a boolean attribute on an element represents the true value, and the absence of the attribute represents the false value. If the attribute is present, its value must either be the empty string or a value that is an ASCII case-insensitive match for the attribute's canonical name."

---------------------------------------

## Removed com.liferay.portal.kernel.model.PortalPreferences Methods getPreferences and setPreferences
- **Date:** 2020-Mar-31
- **JIRA Ticket:** [LPS-124338](https://issues.liferay.com/browse/LPS-124338)

### What changed?

`PortalPreferences` preferences are now stored in the `PortalPreferenceValue` table.

### Who is affected?

This affects you if you are directly getting or setting portal preferences via `com.liferay.portal.kernel.model.PortalPreferences` methods `getPreferences` or `setPreferences`.

### How should I update my code?

Access `javax.portlet.PortalPreferences` instances via `PortalPreferencesLocalService`. Get and set preferences using the `javax.portlet.PortalPreferences` API.

### Why was this change made?

This change simplifies upgrades, reduces storage requirements, and supports querying preferences without using the `like` operator.

---------------------------------------

## item-selector-taglib No Longer fires coverImage-related Events
- **Date:** 2021-Apr-15
- **JIRA Ticket:** [LPS-130359](https://issues.liferay.com/browse/LPS-130359)

### What changed?

The `ImageSelector` JavaScript module no longer fires the `coverImageDeleted`, `coverImageSelected`, and `coverImageUploaded` events using the `Liferay.fire()` API. These events facilitated communication between the `item-selector-taglib` module and `blogs-web` module. Now `Liferay.State` synchronizes the communication using `imageSelectorCoverImageAtom`.

### Who is affected?

This affects you if you are listening for the removed events with `Liferay.on()` or similar functions.

### How should I update my code?

In practice, you should not observe interaction between these two modules, but if you must, you could subscribe to `imageSelectorCoverImageAtom` using the `Liferay.State.subscribe()` API.

### Why was this change made?

`Liferay.fire()` and `Liferay.on()` publish globally visible events on a shared channel. The `Liferay.State` API is a better fit for modules that wish to coordinate at a distance in this way, and it does so in a type-safe manner.

---------------------------------------

## Changed the OAuth 2.0 Token Instrospection Feature Identifier
- **Date:** 2021-May-04
- **JIRA Ticket:** [LPS-131573](https://issues.liferay.com/browse/LPS-131573)

### What changed?

The OAuth 2.0 Token Instrospection Feature Identifier was changed from `token_introspection` to `token.introspection`.

### Who is affected?

This affects you if you are using the Token Introspection feature identifier. Here are a couple use cases:

- Adding an OAuth 2.0 application programatically with Token Introspection feature identifier enabled.
- Checking if Token Introspection feature identifier is enabled for an OAuth 2.0 application.

### How should I update my code?

Change the token from `token_introspection` to `token.introspection`.

### Why was this change made?

This change was made to align and standarize all OAuth 2.0 constants in our code. We recommend using a dot to separate words in feature identifiers.

---------------------------------------

## Removed JournalArticle's Content Field
- **Date:** 2021-May-21
- **JIRA Ticket:** [LPS-129058](https://issues.liferay.com/browse/LPS-129058)

### What changed?

`JournalArticle` content is now stored by Dynamic Data Mapping (DDM) Field services.

### Who is affected?

This affects you if you are directly setting the `JournalArticle` content field.

### How should I update my code?

Use `JournalArticleLocalService`'s update methods instead of directly setting the content field.

### Why was this change made?

This change facilitates referencing file, page, and web content DDM fields in the database without fetching and parsing the content.

---------------------------------------

## Replaced com.liferay.portal.kernel.util.StringBundler with com.liferay.petra.string.StringBundler
- **Date:** 2021-Jun-25
- **JIRA Ticket:** [LPS-133200](https://issues.liferay.com/browse/LPS-133200)

### What changed?

The `com.liferay.petra.string.StringBundler` class has been deprecated. The `com.liferay.portal.kernel.util.StringBundler` class has replaced it.

Here are some methods that now return `com.liferay.petra.string.StringBundler` instead of `com.liferay.portal.kernel.util.StringBundler`:

- `com.liferay.frontend.taglib.dynamic.section.BaseJSPDynamicSection.java#modify`
- `com.liferay.frontend.taglib.dynamic.section.DynamicSection#modify`
- `com.liferay.portal.kernel.io.unsync.UnsyncStringWriter#getStringBundler`
- `com.liferay.portal.kernel.layoutconfiguration.util.RuntimePage#getProcessedTemplate`
- `com.liferay.portal.kernel.layoutconfiguration.util.RuntimePageUtil#getProcessedTemplate`
- `com.liferay.portal.kernel.servlet.BufferCacheServletResponse#getStringBundler`
- `com.liferay.portal.kernel.servlet.taglib.BodyContentWrapper.java#getStringBundler`
- `com.liferay.portal.kernel.theme.PortletDisplay#getContent`
- `com.liferay.portal.kernel.util.StringUtil#replaceToStringBundler`
- `com.liferay.portal.kernel.util.StringUtil#replaceWithStringBundler`
- `com.liferay.portal.layoutconfiguration.util.PortletRenderer#render`
- `com.liferay.portal.layoutconfiguration.util.PortletRenderer#renderAjax`
- `com.liferay.portal.layoutconfiguration.util.RuntimePageImpl#getProcessedTemplate`
- `com.liferay.taglib.BaseBodyTagSupport#getBodyContentAsStringBundler`
- `com.liferay.taglib.BodyContentWrapper#getStringBundler`
- `com.liferay.taglib.aui.NavBarTag#getResponsiveButtonsSB`

### Who is affected?

This affects you if you call one of these methods.

### How should I update my code?

Import `com.liferay.petra.string.StringBundler` instead of
`com.liferay.portal.kernel.util.StringBundler`.

### Why was this change made?

The `com.liferay.petra.string.StringBundler` class has been deprecated.

---------------------------------------

## UserLocalService related classes have modified public API
- **Date:** 2021-Jul-07
- **JIRA Ticket:** [LPS-134096](https://issues.liferay.com/browse/LPS-134096)

### What changed?

A number of methods which normally return `void` now return a `boolean` value instead. This list includes:

- com.liferay.portal.kernel.service.UserLocalService#addDefaultGroups
- com.liferay.portal.kernel.service.UserLocalService#addDefaultRoles
- com.liferay.portal.kernel.service.UserLocalService#addDefaultUserGroups
- com.liferay.portal.kernel.service.UserLocalServiceUtil#addDefaultGroups
- com.liferay.portal.kernel.service.UserLocalServiceUtil#addDefaultRoles
- com.liferay.portal.kernel.service.UserLocalServiceUtil#addDefaultUserGroups
- com.liferay.portal.kernel.service.UserLocalServiceWrapper#addDefaultGroups
- com.liferay.portal.kernel.service.UserLocalServiceWrapper#addDefaultRoles
- com.liferay.portal.kernel.service.UserLocalServiceWrapper#addDefaultUserGroups
- com.liferay.portal.service.impl.UserLocalServiceImpl#addDefaultGroups
- com.liferay.portal.service.impl.UserLocalServiceImpl#addDefaultRoles
- com.liferay.portal.service.impl.UserLocalServiceImpl#addDefaultUserGroups

### Who is affected?

Everyone calling one of these methods

### How should I update my code?

No immediate action is needed, but it's important to note the return type has changed.

### Why was this change made?

This change was made in order to check if default groups, roles, and/or user groups were added to the given user, or if the user already had these associations.

---------------------------------------

## Removed the frontend-css-web CSS module
- **Date:** 2021-Aug-02
- **JIRA Ticket:** [LPS-127085](https://issues.liferay.com/browse/LPS-127085)

### What changed?

The `frontend-css-web` module has been removed and its CSS files have been upgraded.

### Who is affected?

This change affects the following modules:

- `modules/apps/asset/asset-taglib/`
- `modules/apps/asset/asset-tags-navigation-web/`
- `modules/apps/captcha/captcha-taglib/`
- `modules/apps/comment/comment-web/`
- `modules/apps/commerce/commerce-product-content-web/`
- `modules/apps/document-library/document-library-web/`
- `modules/apps/dynamic-data-lists/dynamic-data-lists-web/`
- `modules/apps/dynamic-data-mapping/dynamic-data-mapping-form-web/`
- `modules/apps/dynamic-data-mapping/dynamic-data-mapping-web/`
- `modules/apps/flags/flags-taglib/`
- `modules/apps/frontend-css/frontend-css-web/`
- `modules/apps/frontend-editor/frontend-editor-ckeditor-web/`
- `modules/apps/frontend-js/frontend-js-aui-web/`
- `modules/apps/frontend-js/frontend-js-components-web/`
- `modules/apps/frontend-taglib/frontend-taglib/`
- `modules/apps/frontend-theme/frontend-theme-styled/`
- `modules/apps/item-selector/item-selector-taglib/`
- `modules/apps/knowledge-base/knowledge-base-web/`
- `modules/apps/mobile-device-rules/mobile-device-rules-web/`
- `modules/apps/polls/polls-web/`
- `modules/apps/portal-settings/portal-settings-authentication-cas-web/`
- `modules/apps/product-navigation/product-navigation-control-menu-web/`
- `modules/apps/site-navigation/site-navigation-directory-web/`
- `modules/apps/social/social-bookmarks-taglib/`
- `modules/apps/staging/staging-taglib/`
- `modules/apps/wiki/wiki-web/`
- `modules/dxp/apps/portal-search-tuning/portal-search-tuning-rankings-web/`
- `portal-kernel/`
- `portal-web/`

### How should I update my code?

There are no required code updates.

### Why was this change made?

This change removes deprecated legacy code from DXP/Portal and improves code performance and code consistency.

---------------------------------------

## Removed Some SanitizedServletResponse Static Methods, the HttpHeaders X_XSS_PROTECTION Constant, and http.header.secure.x.xss.protection Portal Property
- **Date:** 2021-Aug-05
- **JIRA Ticket:** [LPS-134188](https://issues.liferay.com/browse/LPS-134188)

### What changed?

The following methods, constant, and portal property have been removed.

Methods:

- `com.liferay.portal.kernel.servlet.SanitizedServletResponse#disableXSSAuditor`
- `com.liferay.portal.kernel.servlet.SanitizedServletResponse#disableXSSAuditor`
- `com.liferay.portal.kernel.servlet.SanitizedServletResponse#disableXSSAuditorOnNextRequest`
- `com.liferay.portal.kernel.servlet.SanitizedServletResponse#disableXSSAuditorOnNextRequest`

Constant:

- `com.liferay.portal.kernel.servlet.HttpHeaders#X_XSS_PROTECTION`

Portal Property:

- `http.header.secure.x.xss.protection`

### Who is affected?

This affects you if you call these methods, use the constant, or use the portal property.

### How should I update my code?

Remove your code that calls these methods or uses the constant. The X-Xss-Protection header has no effect on modern browsers and may give a false sense of security.

Remove the `http.header.secure.x.xss.protection` property from your portal property extension files (e.g., `portal-ext.properties`).

### Why was this change made?

The X-Xss-Protection header is no longer supported by modern browsers. These static methods, constant, and portal property relate to the X-Xss-Protection header.

---------------------------------------

## Replaced the OpenIdConnectServiceHandler Interface With the OpenIdConnectAuthenticationHandler
- **Date:** 2021-Aug-09
- **JIRA Ticket:** [LPS-124898](https://issues.liferay.com/browse/LPS-124898)

### What changed?

The `OpenIdConnectServiceHandler` interface has been removed and replaced by the `OpenIdConnectAuthenticationHandler` interface.

Old interface:

```
portal.security.sso.openid.connect.OpenIdConnectServiceHandler
```

New interface:

```
portal.security.sso.openid.connect.OpenIdConnectAuthenticationHandler
```

### Who is affected?

This affects you if you are implementing or using the `OpenIdConnectServiceHandler` interface.

### How should I update my code?

If your code invokes the `OpenIdConnectServiceHandler` interface, change it to invoke the `OpenIdConnectAuthenticationHandler` interface. This requires providing an `UnsafeConsumer` for signing in the DXP/Portal user.

If you have implemented the `OpenIdConnectServiceHandler` interface, implement the `OpenIdConnectAuthenticationHandler` interface and provide a way to refresh the user's OIDC access tokens using the provided refresh tokens. If you don't make this provision, sessions will invalidate when the initial access tokens expire.

### Why was this change made?

This change improves OIDC refresh token handling. The change was made for these reasons:

- To detach the access token refresh process from HTTP request handling. Without this detachment, there can be problems maintaining OIDC sessions with providers that only allow refresh tokens to be used once. Premature portal session invalidation can occur.

- To avoid premature portal session invalidation for OIDC providers that provide refresh tokens that expire at the same time as their corresponding access tokens.

---------------------------------------

## Renamed Language Keys

- **Date:** 2021-Sep-09
- **JIRA Ticket:** LPS-135504

### What changed?

All module language keys were moved to a module called `portal-language-lang` at `liferay-[dxp|portal]/modules/apps/portal-language/portal-language-lang`. In some cases where modules used language keys with the same name but different values, language keys were added to accommodate the different values. From an affected module's perspective, the language key has been renamed.

### Who is affected?

This affects you if you are using or overriding a language key that has been renamed. [Renamed Language Keys](https://learn.liferay.com/dxp/latest/en/installation-and-upgrades/upgrading-liferay/reference/renamed-language-keys.html) maps the old language key names to the new names.

### How should I update my code?

Rename all instances of the renamed language keys to their new names based on the mappings in [Renamed Language Keys](https://learn.liferay.com/dxp/latest/en/installation-and-upgrades/upgrading-liferay/reference/renamed-language-keys.html).

### Why was this change made?

Centralized language keys are easier to manage.

---------------------------------------

## Moved the CAS SSO Modules to the portal-security-sso-cas Project
- **Date:** 2021-Sep-15
- **JIRA Ticket:** [LPS-88905](https://issues.liferay.com/browse/LPS-88905)

### What changed?

The CAS SSO modules were moved from the `portal-security-sso` project to a new project named `portal-security-sso-cas`. The new project is deprecated but is available to download from Liferay Marketplace.

### Who is affected?

This affects anyone using CAS SSO as an authentication system.

### How should I update my code?

If you want to continue using CAS SSO as an authentication system, you must download the corresponding app from Liferay Marketplace.

### Why was this change made?

This is part of an ongoing effort to consolidate SSO support and increase focus on open standards.

---------------------------------------

## Namespaced the clay:select Tag's name Attribute for Printing
- **Date:** 2021-Sep-15
- **JIRA Ticket:** [LPS-139131](https://issues.liferay.com/browse/LPS-139131)

### What changed?

The attribute `name` of `clay:select` now includes the portlet namespace (if available) by default when printed.

### Who is affected?

This affects everyone who uses `clay:select` and its `name` attribute to handle data server-side.

### How should I update my code?

If you have been namespacing the attribute on your own by prefixing `liferayPortletResponse.getNamespace() + NAME_VALUE`
to the value, drop that prefix and use `name="NAME_VALUE"` directly.

If you want full control (or the namespaced version is insufficient), you can revert back to the old
behavior by also passing `useNamespace="<%= Boolean.FALSE %>"` to the tag.

### Why was this change made?

This change was made to better match the current `aui:select` tag behavior, facilitate a future migration to the `clay:select` tag, and simplify current `clay:select` tag usage.

---------------------------------------

## Removed the Core Registry API and Registry Implementation modules
- **Date:** 2021-Sep-28
- **JIRA Ticket:** [LPS-138126](https://issues.liferay.com/browse/LPS-138126)

### What changed?

The core Registry API (`registry-api`) and Registry Implementation (`registry-impl`) modules have been removed.

### Who is affected?

This affects anyone using the Registry API.

### How should I update my code?

Access the native OSGi API using the system bundle's `org.osgi.framework.BundleContext`. Get the context by calling the `com.liferay.portal.kernel.module.util.SystemBundleUtil.getBundleContext()` method.

### Why was this change made?

The native OSGi API makes the bridged API unnecessary.

---------------------------------------

## Removed Support for Web Content in Document Types
- ** Date:** 2021-Sep-30
- ** JIRA Ticket:** [LPS-139710](https://issues.liferay.com/browse/LPS-139710)

### What changed?

Support for Web Content fields in Document Types has been removed.

### Who is affected?

This affects anyone using a Web Content field Document Type.

### How should I update my code?

Remove all references to Web Content field Document Types.

### Why was this change made?

The Web Content field Document Type was an accidental feature included in Documents and Media. Its use case was unclear. When staging was enabled, the feature was causing circularity problems between Web Content articles linked to Documents and Media documents.

---------------------------------------

### OpenID Connect Provider Signing Algorithm Must Be Configured If Different From RS256

- **Date:** 2021-Sep-30
- **JIRA Ticket:** [LPS-138756](https://issues.liferay.com/browse/LPS-138756)

#### What changed?

The portal's OpenID Connect client now requires explicitly stating the ID Token signing algorithm agreed with the provider.

#### Who is affected?

This affects anyone integrating OpenID Connect providers that sign ID Tokens using a signing algorithm other than the **first** supported signing algorithm listed in the UI. The list is served by the provider's Discovery Endpoint, or configured offline in the UI.

#### How should I update my code?

In the UI, review the OpenID Connect Provider Connection configuration(s). Specify the agreed algorithm as the Registered ID Token Signing Algorithm.

#### Why was this change made?

This improves using all signing algorithms supported by OpenID Connect providers.

---------------------------------------